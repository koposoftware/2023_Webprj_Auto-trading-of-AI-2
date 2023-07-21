import yfinance as yf
from forex_python.converter import CurrencyRates
import datetime
import cx_Oracle

conn = cx_Oracle.connect(user='team4', password='Project12345', dsn='dinkdb_high')

dow_ticker = '^DJI'  # 다우존스 지수
kospi_ticker = '^KS11'  # 코스피 지수
nasdaq_ticker = '^IXIC'  # 나스닥 지수
ksdaq_ticker = '^KQ11'  # 코스닥 지수
btc_ticker = 'BTC-USD'  # 비트코인


dow_data = yf.Ticker(dow_ticker)
kospi_data = yf.Ticker(kospi_ticker)
nasdaq_data = yf.Ticker(nasdaq_ticker)
ksdaq_data = yf.Ticker(ksdaq_ticker)
btc_data = yf.Ticker(btc_ticker)

# 종가 가져오기
dow_price = dow_data.history(period='1d').Close[0]  # 다우존스 지수 종가
kospi_price = kospi_data.history(period='1d').Close[0]  # 코스피 지수 종가
nasdaq_price = nasdaq_data.history(period='1d').Close[0]  # 나스닥 지수 종가
ksdaq_price = ksdaq_data.history(period='1d').Close[0]  # 코스닥 지수 종가
btc_price = btc_data.history(period='1d').Close[0]  # 비트코인 종가

# 전일 대비 가격 변동 비율 계산
dow_change = dow_data.history(period='2d').Close.pct_change()[1]  # 다우존스 지수 변동률
kospi_change = kospi_data.history(period='2d').Close.pct_change()[1]  # 코스피 지수 변동률
nasdaq_change = nasdaq_data.history(period='2d').Close.pct_change()[1]  # 나스닥 지수 변동률
ksdaq_change = ksdaq_data.history(period='2d').Close.pct_change()[1]  # 코스닥 지수 변동률
btc_change = btc_data.history(period='3d').Close.pct_change()[1]  # 비트코인 변동률


# 환율 정보 가져오기
cr = CurrencyRates()
usd_krw_rate = cr.get_rate('USD', 'KRW')  # USD/KRW 환율

# 월별 환율 변동 비율 
previous_month = datetime.datetime.now().month - 1
usd_krw_rate_prev_month = cr.get_rate('USD', 'KRW', date_obj=datetime.datetime(datetime.datetime.now().year, previous_month, 1))
usd_krw_change = ((usd_krw_rate - usd_krw_rate_prev_month) / usd_krw_rate_prev_month) * 100

# 현재 날짜
created_date = datetime.datetime.now().date()

# 소수점 둘째 자리까지 반올림하여 값 삽입
dow_price_rounded = round(dow_price, 2)
dow_change_rounded = round(dow_change * 100, 2)
kospi_price_rounded = round(kospi_price, 2)
kospi_change_rounded = round(kospi_change * 100, 2)
nasdaq_price_rounded = round(nasdaq_price, 2)
nasdaq_change_rounded = round(nasdaq_change * 100, 2)
ksdaq_price_rounded = round(ksdaq_price, 2)
ksdaq_change_rounded = round(ksdaq_change * 100, 2)
btc_price_rounded = round(btc_price, 2)
btc_change_rounded = round(btc_change * 100, 2)
usd_krw_rate_rounded = round(usd_krw_rate, 2)
usd_change_rounded = round(usd_krw_change, 2)


# 데이터 삽입
cursor = conn.cursor()
# SQL INSERT 쿼리
sql = """
INSERT ALL
    INTO main_view (category, price, status, created_date) VALUES ('Dow', :dow_price_rounded, :dow_change_rounded, :created_date)
    INTO main_view (category, price, status, created_date) VALUES ('Kospi', :kospi_price_rounded, :kospi_change_rounded, :created_date)
    INTO main_view (category, price, status, created_date) VALUES ('Nasdaq', :nasdaq_price_rounded, :nasdaq_change_rounded, :created_date)
    INTO main_view (category, price, status, created_date) VALUES ('Kosdaq', :ksdaq_price_rounded, :ksdaq_change_rounded, :created_date)
    INTO main_view (category, price, status, created_date) VALUES ('Bitcoin', :btc_price_rounded, :btc_change_rounded, :created_date)
    INTO main_view (category, price, status, created_date) VALUES ('원화', :usd_krw_rate_rounded, :usd_change_rounded, :created_date)
SELECT 1 FROM DUAL
"""

# 쿼리 실행
cursor.execute(sql, dow_price_rounded=dow_price_rounded, dow_change_rounded=dow_change_rounded, created_date=created_date,
                     kospi_price_rounded=kospi_price_rounded, kospi_change_rounded=kospi_change_rounded,
                     nasdaq_price_rounded=nasdaq_price_rounded, nasdaq_change_rounded=nasdaq_change_rounded,
                     ksdaq_price_rounded=ksdaq_price_rounded, ksdaq_change_rounded=ksdaq_change_rounded,
                     btc_price_rounded=btc_price_rounded, btc_change_rounded=btc_change_rounded,
                     usd_krw_rate_rounded=usd_krw_rate_rounded,usd_change_rounded=usd_change_rounded)

# 변경 사항 커밋
conn.commit()

# 연결 종료
cursor.close()
conn.close()
