import pandas as pd
import oracledb as cx_Oracle
import yfinance as yf
import FinanceDataReader as fdr
from datetime import datetime, timedelta

class Kospi:
    def __init__(self):
        self.connection = self.connect()

    # DB 연결
    # 새로 만든 오라클 DB 적어주면 된다.
    def connect(self):
        url = "49.247.25.122:1521/dink02"
        uid = "scott"
        upw = "tiger"

        connection = cx_Oracle.connect(user = uid, password = upw, dsn = url)
        return connection

    # 코스피 종목 가져오기(우선 이부분이 실행X, 기존의 데이터베이스 이용)
    def getKospi(self):
        kospi = fdr.StockListing('KOSPI')
        kospi = kospi.loc[:, ['Code', 'Name', 'Stocks']]
        kospi_data = [(code, name, stocks) for code, name, stocks in zip(kospi['Code'], kospi['Name'], kospi['Stocks'])]
        return kospi_data

    # 코스피 종목 테이블에 저장하기
    def initKospi(self):
        cursor = self.connection.cursor()

        kospi_data = self.getKospi()

        current_date = datetime.now().strftime("%Y-%m-%d")
        kospi_data_with_date = [(data[0], data[1], data[2], current_date) for data in kospi_data]

        sql = "insert into kospi (id, name, stock_supply, created_date) values (:1, :2, :3, TO_DATE(:4, 'YYYY-MM-DD'))"
        cursor.executemany(sql, kospi_data_with_date)

        self.connection.commit()
        cursor.close()


    # 코스피 데이터 가져오기
    def getData(self):
        cursor = self.connection.cursor()
        cursor.execute("SELECT id FROM kospi")
        codes = [row[0] for row in cursor.fetchall()]
        cursor.close()
        return codes

    # 가장 오래된 날짜 찾기
    def getOldestDate(self, code):
        kospi_data = fdr.DataReader(code)
        oldest_date = kospi_data.index[0].date()
        return oldest_date

    def initStockData(self):
        start_date = datetime(2010, 1, 4)  
        end_date = datetime.now() - timedelta(days=1)
        codes = self.getData()
        cursor = self.connection.cursor()
        num = 0
        for code in codes:
    
            try:
                stock_data = yf.download(code+".KS", start=start_date, end=end_date)
                stock_data['Prev_Open'] = stock_data['Open'].shift(1)

                for index, row in stock_data.iterrows():
                    date = index.strftime("%Y-%m-%d")
                    openPrice = row['Open']
                    high = row['High']
                    low = row['Low']
                    close = row['Close']
                    volume = row['Volume']
                    prev_open = row['Prev_Open']

                    cursor.execute("select stock_supply from kospi where id = :1", (code,))
                    stock_no = cursor.fetchone()[0]
                    cap = close * stock_no

                    created_date = datetime.now().strftime("%Y-%m-%d")  

                    if pd.isna(prev_open):
                        changed_rate = 0
                    else:
                        changed_rate = ((close - prev_open) / prev_open) * 100

                    sql = "insert into stock (kospi_id, trading_day, open, high, low, close, volume, cap, created_date, changed_rate) values (:1, TO_DATE(:2, 'YYYY-MM-DD'), :3, :4, :5, :6, :7, :8, TO_DATE(:9, 'YYYY-MM-DD'), :10)"
                    cursor.execute(sql, (code, date, openPrice, high, low, close, volume, cap, created_date, changed_rate))
            except:
                oldest_date = self.getOldestDate(code)
                stock_data = yf.download(code+".KS", start=oldest_date, end=end_date)
                stock_data['Prev_Open'] = stock_data['Open'].shift(1)

                for index, row in stock_data.iterrows():
                    date = index.strftime("%Y-%m-%d")
                    openPrice = row['Open']
                    high = row['High']
                    low = row['Low']
                    close = row['Close']
                    volume = row['Volume']
                    prev_open = row['Prev_Open']

                    cursor.execute("select stock_supply from kospi where id = :1", (code,))
                    stock_no = cursor.fetchone()[0]
                    cap = close * stock_no

                    created_date = datetime.now().strftime("%Y-%m-%d") 

                    if pd.isna(prev_open):
                        changed_rate = 0
                    else:
                        changed_rate = ((close - prev_open) / prev_open) * 100

                    sql = "insert into stock (kospi_id, trading_day, open, high, low, close, volume, cap, created_date, changed_rate) values (:1, TO_DATE(:2, 'YYYY-MM-DD'), :3, :4, :5, :6, :7, :8, TO_DATE(:9, 'YYYY-MM-DD'), :10)"
                    cursor.execute(sql, (code, date, openPrice, high, low, close, volume, cap, created_date, changed_rate))
            finally:
                num += 1
        self.connection.commit()
        cursor.close()



    def updateStockData(self):
        start_date = datetime.now().strftime('%Y-%m-%d')
        end_date = (datetime.now() + timedelta(days=1)).strftime('%Y-%m-%d')

        codes = self.getData()
        cursor = self.connection.cursor()

        for code in codes:
            try:

                stock_data = yf.download(code+".KS", start=start_date, end=end_date)
                stock_data['Prev_Open'] = stock_data['Open'].shift(1)

                for index, row in stock_data.iterrows():
                    date = index.strftime("%Y-%m-%d")
                    openPrice = row['Open']
                    high = row['High']
                    low = row['Low']
                    close = row['Close']
                    volume = row['Volume']
                    prev_open = row['Prev_Open']
                    
                    cursor.execute("select stock_supply from kospi where id = :1", (code,))
                    stock_no = cursor.fetchone()[0]
                    cap = close * stock_no

                    created_date = datetime.now().strftime("%Y-%m-%d") 
                    
                    if pd.isna(prev_open):
                        changed_rate = 0
                    else:
                        changed_rate = ((close - prev_open) / prev_open) * 100

                    # sql = "insert into stock (kospi_id, trading_day, open, high, low, close, volume, cap, created_date, changed_rate) values (:1, TO_DATE(:2, 'YYYY-MM-DD'), :3, :4, :5, :6, :7, :8, TO_DATE(:9, 'YYYY-MM-DD'), :10)"
                    # cursor.execute(sql, (code, date, openPrice, high, low, close, volume, cap, created_date, changed_rate))
                    print(code, date, close)
            except:
                continue


        self.connection.commit()
        cursor.close()


    # DB 연결 종료
    def closeConnection(self):
        self.connection.close()