import os
from datetime import date
import pickle
import oracledb as cx_Oracle
import pandas as pd
from sklearn.model_selection import train_test_split, GridSearchCV
from sklearn.ensemble import RandomForestRegressor
from sklearn.metrics import r2_score

class RegressionModel:
    def __init__(self):
        self.connection = self.connect()
        self.cursor = self.connection.cursor()

    def connect(self):
        url = "49.247.25.122:1521/dink02"
        uid = "scott"
        upw = "tiger"
        connection = cx_Oracle.connect(user=uid, password=upw, dsn=url)
        return connection

    def loadStockData(self, code):
        self.cursor.execute("select * from stock where kospi_id = :1", (code,))
        result = self.cursor.fetchall()
        return result

    def trainModel(self):
        # 폴더가 존재하는지 확인하고 없다면 생성
        if not os.path.exists('RandomForestRegressor'):
            os.makedirs('RandomForestRegressor')

        self.cursor.execute("SELECT * FROM stock")
        column_names = [desc[0] for desc in self.cursor.description]  # 컬럼 이름 가져오기

        self.cursor.execute("select distinct kospi_id from stock")
        codes = [row[0] for row in self.cursor.fetchall()]

        count = 1
        for code in codes:
            try:
                result = self.loadStockData(code)
                df = pd.DataFrame(result, columns=column_names)

                # 'CREATED_DATE', 'KOSPI_ID', 'ID' 열 제거
                df = df.drop(['CREATED_DATE', 'KOSPI_ID', 'ID'], axis=1)

                # 'TRADING_DAY' 열을 에폭 타임스탬프로 변환
                df['TRADING_DAY'] = (pd.to_datetime(df['TRADING_DAY']).astype(int)/ 10**9).astype(int)

                # Feature와 Label로 분리
                X = df.drop('CLOSE', axis=1) 
                y = df['CLOSE']  

                # 학습 데이터와 테스트 데이터로 분리
                X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

                # 랜덤 포레스트 모델 생성 및 하이퍼파라미터 튜닝
                param_grid = {
                'n_estimators': [50], 
                'max_depth': [5], 
                'min_samples_split': [20, 40, 60]  
            }

                rf = RandomForestRegressor(random_state=42)
                grid_search = GridSearchCV(estimator=rf, param_grid=param_grid, cv=5, error_score='raise')
                grid_search.fit(X_train, y_train)

                # 모델 성능 평가
                predictions = grid_search.predict(X_test)
                r2 = r2_score(y_test, predictions)

                # 최적의 하이퍼파라미터로 모델 생성
                best_rf = grid_search.best_estimator_

                # 모델 저장
                with open(f'RandomForestRegressor/{code}.pkl', 'wb') as f:
                    pickle.dump(best_rf, f)
                print(count)
                count += 1
            except:
                continue

    def insertMachinePredict(self):
        self.cursor.execute("select distinct kospi_id from stock")
        codes = [row[0] for row in self.cursor.fetchall()]

        for code in codes:
            try:
                self.cursor.execute("""
                    select max(trading_day)
                    from stock 
                    where kospi_id = :1
                """, (code,))
                max_trading_day = self.cursor.fetchone()[0]

                # max_trading_day를 문자열로 변환
                str_max_trading_day = pd.to_datetime(max_trading_day).strftime('%Y-%m-%d')

                self.cursor.execute("""
                    select cap, changed_rate, high, low, open, trading_day, volume, close
                    from stock 
                    where kospi_id = :1 and trading_day = :2
                """, (code, max_trading_day))

                data = self.cursor.fetchone()
                
                if data is None:
                    continue

                self.cursor.execute("select name from kospi where id = :1", (code,))
                name = self.cursor.fetchone()[0]

                # 'TRADING_DAY'를 에폭 타임스탬프로 변환
                trading_day = int(pd.to_datetime(data[5]).timestamp())
                
                predict_df = pd.DataFrame({
                    'CAP': [data[0]],
                    'CHANGED_RATE': [data[1]],
                    'HIGH': [data[2]],
                    'LOW': [data[3]],
                    'OPEN': [data[4]],
                    'TRADING_DAY': [trading_day],
                    'VOLUME': [data[6]]
                })

            
                # 모델 불러오기
                model_path = os.path.join('RandomForestRegressor', f'{code}.pkl')
                with open(model_path, 'rb') as f:
                    loaded_model = pickle.load(f)
                tomorrow_pred = loaded_model.predict(predict_df)
                pred = round(tomorrow_pred[0] / 50) * 50
        
                
                self.cursor.execute("""
                    INSERT INTO machine_predict(id, code, created_date, name, prev_close, pred_close)
                    VALUES(machine_predict_seq.NEXTVAL, :2, TO_DATE(:3, 'YYYY-MM-DD'), :4, :5, :6)
                """, (code, str_max_trading_day, name, data[7], pred))
                
                print("완료")
            except:
                print("실패")
                continue

        self.connection.commit()
        print("저장 완료")

    # 장 마감 후 close를 넣은 후 diff 계산
    def updateMachinePredict(self):
        self.cursor.execute("select distinct code from machine_predict")
        codes = [row[0] for row in self.cursor.fetchall()]

        for code in codes:
            try:
                self.cursor.execute("""
                    select max(created_date)
                    from machine_predict 
                    where code = :1
                """, (code,))
                max_created_date = self.cursor.fetchone()[0]

                if max_created_date is None:
                    continue

                self.cursor.execute("""
                    select close
                    from stock 
                    where kospi_id = :1 and trading_day = :2
                """, (code, max_created_date))

                close_today = self.cursor.fetchone()

                if close_today is None:
                    continue

                self.cursor.execute("""
                    select pred_close
                    from machine_predict
                    where code = :1 and created_date = :2
                """, (code, max_created_date))
                row = self.cursor.fetchone()

                if row is None:
                    continue

                pred_close = row[0]
                diff = round(((pred_close - close_today[0]) / close_today[0]) * 100, 2)

                today = date.today()
                self.cursor.execute("""
                    update machine_predict
                    set close = :1, diff = :2, created_date = :3
                    where code = :4 and created_date = :5
                """, (close_today[0], diff, today, code, max_created_date))
        
            except:
                continue
        self.connection.commit()
    

