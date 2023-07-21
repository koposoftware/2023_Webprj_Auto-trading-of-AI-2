from regression import RegressionModel
from method import Kospi

if __name__ == "__main__":
    # kospi.testConnection()
    kospi = Kospi()  
    # model = RegressionModel()

    # KOSPI 종목 테이블 구축
    # KOSPI 테이블안에 데이터가 없으면 실행
    # kospi.initKospi()

    # 코스피 종목 데이터 구축
    # STOCK 테이블안에 데이터가 없으면 실행
    kospi.initStockData()

    # 머신러닝 모델 코스피 종목 952개 생성
    # 서버안에 학습 모델이 없으면 실행
    # model.trainModel()

# --------------------- 매일 주식장 시작 전 실행되어야 하는 코드 ----------------------------
# --------------------- 예를 들면 7월 11일에 실행이 되어야 하고 ----------------------------
    # 학습한 모델을 이용하여 MACHINE_PREDICT 테이블에 데이터를 추가
    # 실제 테이블에 들어가는 데이터는 종목코드, 종목이름, 예측가격이 우선적으로 들어간다
    # 나머지는 우선 NULL로 대체
    # model.insertMachinePredict()

# --------------------- 매일 주식장 마감 후 실행되어야 하는 코드 ----------------------------
# --------------------- 이 부분은 7월 10일에 실행이 되어야 한다 ----------------------------
    # 매일 데이터 추가(매일 오후 6시에 실행)
    # 다음날 아침에 하게되면 아직 주식장이 열리지 않아서 데이터가 없기 때문에 무조건 전날 실행해줘야한다.
    # kospi.updateStockData()

    # DB 연결해제
    # 무조건 실행
    kospi.closeConnection()


    # 주식장 마감 후 kospi.updateStockData() 메서드 실행 후 
    # 그 날의 종가가 STOCK 테이블에 들어오게 되면 STOCK 테이블의 CLOSE를 이용하요 오차를 구한 후
    # MACHINE_PREDICT 테이블에 NULL값에 들어있던 컬럼 값 들을 대체한다.
    # model.updateMachinePredict()

    # 모델 성능 확인 
    # model.measureAccuracy()