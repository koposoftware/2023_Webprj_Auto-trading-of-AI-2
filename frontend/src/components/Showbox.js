import React, { useEffect, useState } from "react";
import "../styles/Showbox.css";
import LineChart from "../components/LineChart";
import CandleChart from "../components/CandleChart";
import { DoughnutChart } from "../components/DoughnutChart";
import Stock from "./Stock";
import LoadingBox from "./LoadingBox";

const Showbox = () => {
  const [summary, setSummary] = useState({});
  const [stocksList, setStockList] = useState([]);
  const [profitState, setProfitState] = useState([]);
  const [candle, setCandle] = useState([]);
  const [loading, setLoading] = useState(false);
  const [candleTrue, setCandleTrue] = useState(false);
  const [stockName, setStockName] = useState("");
  const loadSummary = async (token) => {
    let response = await fetch(
      "https://api-dev.net/trading/portfolio/summary",
      {
        method: "POST",
        headers: { Authorization: "Bearer " + token },
      }
    );
    if (response.status == 200) {
      response = await response.json();
      console.log("서머리");

      console.log(response);
      setSummary(response);
    }
  };

  const loadPortfolioStockList = async (token) => {
    let response = await fetch("https://api-dev.net/trading/portfolio/list", {
      method: "POST",
      headers: { Authorization: "Bearer " + token },
    });
    if (response.status == 200) {
      response = await response.json();
      setStockList(response);
    }
  };

  const loadPortfolioState = async (token) => {
    let response = await fetch("https://api-dev.net/trading/portfolio/state", {
      method: "POST",
      headers: { Authorization: "Bearer " + token },
    });

    if (response.status == 200) {
      response = await response.json();
      setProfitState(response);
    }
  };

  const loadStock = async (code) => {
    setCandleTrue(true);
    let response = await fetch(
      "https://api-dev.net/trading/show?code=" + code,
      { method: "GET" }
    );
    if (response.status == 200) {
      response = await response.json();
      console.log(response);
      setCandle(response);
      setCandleTrue(false);
      setStockName(response.name);

      console.log(stockName);
    }
  };

  const portfolioCheck = async (token) => {
    let response = await fetch("https://api-dev.net/trading/portfolio/check", {
      method: "POST",
      headers: { Authorization: "Bearer " + token },
    });
    if (response.status != 200) {
      window.location.assign("/init/portfolio");
    }
  };

  const loadAll = async (token) => {
    setLoading(true);
    console.log("loading");
    loadPortfolioStockList(token);
    loadPortfolioState(token);
    loadSummary(token);
    loadStock("005930");
    portfolioCheck(token);
    console.log("loaded");

    setTimeout(() => {
      setLoading(false);
    }, 3000);
    // setLoading(false);
  };
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token == null) {
      window.location.assign("/main");
    }

    loadAll(token);
    console.log();
  }, []);
  return (
    <>
      {loading && <LoadingBox />}
      {!loading && (
        <div className="showbox-container">
          <div className="showbox-inner-container">
            <div className="chart-row">
              <div className="line-chart-container">
                <div
                  style={{
                    width: "100%",
                    fontFamily: "Hana2-Heavy",
                    fontSize: "14px",
                    paddingLeft: "50px",
                    boxSizing: "border-box",
                    height: "30px",
                  }}
                >
                  수익률 차트
                </div>

                {profitState && profitState.length > 0 ? (
                  <LineChart data={profitState} />
                ) : (
                  <div
                    style={{
                      width: "570px",
                      height: "250px",
                      display: "flex",
                      alignItems: "center",
                      justifyContent: "center",
                    }}
                  >
                    데이터가 존재하지 않습니다.{" "}
                  </div>
                )}
              </div>
              <div className="pie-chart-container">
                <div className="user-account">
                  <div style={{ marginBottom: "5px" }}>{summary.nickname}</div>
                  <div>평가액 {summary.amount} 원</div>
                </div>
                {summary && summary.stocks && (
                  <DoughnutChart stocks={summary.stocks} />
                )}
              </div>
            </div>
            <div className="chart-row">
              <div className="candle-chart-container">
                <div
                  style={{
                    width: "100%",
                    fontFamily: "Hana2-Heavy",
                    fontSize: "18px",
                    paddingLeft: "50px",
                    boxSizing: "border-box",
                    height: "30px",
                  }}
                >
                  {!candleTrue ? candle.name : ""}
                </div>

                <CandleChart candle={candle.prices} loaded={candleTrue} />
              </div>
              <div className="kospi-chart-container">
                {stocksList.map((stock, index) => (
                  <Stock data={stock} loadStock={loadStock} key={index} />
                ))}
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default Showbox;
