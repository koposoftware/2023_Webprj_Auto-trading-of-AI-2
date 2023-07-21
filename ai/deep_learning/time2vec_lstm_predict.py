import pandas as pd
import cx_Oracle
import numpy as np
import matplotlib.pyplot as plt
from tensorflow import keras
from tensorflow.keras import layers, Model
from sklearn.preprocessing import MinMaxScaler

class T2V(layers.Layer):
    def __init__(self, output_dim=None, **kwargs):
        self.output_dim = output_dim
        super(T2V, self).__init__(**kwargs)

    def build(self, input_shape):
        self.W = self.add_weight(
            name='W',
            shape=(input_shape[-1], self.output_dim),
            initializer='uniform',
            trainable=True
        )

        self.P = self.add_weight(
            name='P',
            shape=(input_shape[1], self.output_dim),
            initializer='uniform',
            trainable=True
        )

        self.w = self.add_weight(
            name='w',
            shape=(input_shape[1], 1),
            initializer='uniform',
            trainable=True
        )

        self.p = self.add_weight(
            name='p',
            shape=(input_shape[1], 1),
            initializer='uniform',
            trainable=True
        )

        super(T2V, self).build(input_shape)

    def call(self, x):
        original = self.w * x + self.p
        sin_trans = keras.backend.sin(keras.backend.dot(x, self.W) + self.P)

        return keras.backend.concatenate([sin_trans, original], -1)

def T2V_NN(param, dim):
    inp = layers.Input(shape=(dim,1))
    x = T2V(param['t2v_dim'])(inp)
    x = layers.LSTM(param['unit'], activation=param['activation'])(x)
    x = layers.Dense(1)(x)

    m = Model(inp, x)
    m.compile(loss='mse', optimizer='adam')

    return m

class StockPrediction:
    def __init__(self, model_path, seq_len):
        self.seq_len = seq_len
        base_param = {'unit': 32,'t2v_dim': 16, 'lr': 0.01, 'activation': 'relu', 'epochs': 20, 'batch_size': 1024}
        self.model = T2V_NN(base_param, self.seq_len)
        self.model.load_weights(model_path)
        self.scaler = MinMaxScaler()
        self.actual_list = []
        self.prediction_list = []
        self.rate_list = []

    def connect(self):
        url = "49.247.25.122:1521/dink02"
        uid = "scott"
        upw = "tiger"
        connection = cx_Oracle.connect(user=uid, password=upw, dsn=url)
        return connection

    def loadStockData(self, connection):
        cursor = connection.cursor()
        cursor.execute("SELECT KOSPI.ID as KOSPI_ID, KOSPI.NAME,stock.TRADING_DAY,stock.CLOSE FROM KOSPI LEFT JOIN (select * from stock) stock ON stock.kospi_id = KOSPI.id ORDER BY KOSPI.ID ASC, stock.TRADING_DAY ASC")
        result = cursor.fetchall()
        column_names = [desc[0] for desc in cursor.description]
        df = pd.DataFrame(result, columns=column_names)
        cursor.close()
        return df

    def stockUpperSeqlen(self, df):
        grouped = df.groupby('KOSPI_ID')
        group_sizes = grouped.size()
        valid_idx = group_sizes[group_sizes >= self.seq_len].index
        stockupperseqlen = df[df['KOSPI_ID'].isin(valid_idx)]
        return stockupperseqlen

    def normalize(self, df):
        df = df.dropna()
        df = df.reset_index()
        close_price = df.CLOSE.values.reshape(-1, 1)
        df['scaled_Close'] = self.scaler.fit_transform(close_price)
        return df

    def gen_sequence(self, id_df, seq_length, seq_cols):
        data_matrix = id_df[seq_cols].values
        num_elements = data_matrix.shape[0]

        for start in range(0, num_elements - seq_length + 1):
            yield data_matrix[start : start + seq_length, :]

    def reshapeX(self, df):
        X = []
        for sequence in self.gen_sequence(df, self.seq_len, ['scaled_Close']):
            X.append(sequence)
        X = np.asarray(X)

        return X

    def dayPrediction(self, X):
        lastday = X[-1:]
        y_hat = self.model.predict(lastday)

        y_hat_inverse = self.scaler.inverse_transform(y_hat)

        return y_hat_inverse[0]
    
    def predictStock(self, stock_df):
        stock_df = self.normalize(stock_df)
        X = self.reshapeX(stock_df)
        prediction = self.dayPrediction(X)
        self.prediction_list.append(prediction[0])


    def insertPredictions(self, data_list):
        conn = self.connect()
        cursor = conn.cursor()
        sql = "INSERT INTO deeplearning_predict (id, code, created_date, name,prev_close, pred_close,pred_rate) VALUES (deeplearning_predict_seq.NEXTVAL, :1, SYSDATE, :2, :3, :4,:5)"
        

        for data in data_list:
            cursor.execute(sql, data)

        conn.commit()
        cursor.close()
        conn.close()



model_path = "checkpoint_best.hdf5"
seq_len = 20
stock_prediction = StockPrediction(model_path, seq_len)
connection = stock_prediction.connect()
stock = stock_prediction.loadStockData(connection)
connection.close()

stockupperseqlen = stock_prediction.stockUpperSeqlen(stock)
for kospi_id in stockupperseqlen['KOSPI_ID'].unique():
    stock_df = stockupperseqlen[stockupperseqlen['KOSPI_ID'] == kospi_id]
    stock_prediction.predictStock(stock_df)


prevClose = stockupperseqlen.loc[stockupperseqlen.groupby('KOSPI_ID')['TRADING_DAY'].idxmax(), 'CLOSE']
prevClose_list = [round(value) for value in prevClose]

prediction_list = [round(value) for value in stock_prediction.prediction_list]

for i in range(len(prevClose_list)-1):
    stock_prediction.rate_list.append(round(((prevClose_list[i]-prediction_list[i])/prevClose_list[i])*100,2))

zipped_data = zip(stockupperseqlen['KOSPI_ID'].unique(), stockupperseqlen['NAME'].unique(), prevClose_list,prediction_list,stock_prediction.rate_list)
data_list = [list(item) for item in zipped_data]

stock_prediction.insertPredictions(data_list)

