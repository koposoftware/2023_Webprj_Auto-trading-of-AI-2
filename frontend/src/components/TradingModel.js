import React, { useEffect, useState } from "react";
import InitModelStocks from "./InitModelStocks";
import "../styles/TradingModel.css";
import LoadingBox from "./LoadingBox";

const TradingModel = () => {
  const [showElements, setShowElements] = useState(false);
  const [mlStocks, setMlStocks] = useState([]);
  const [dlStocks, setDlStocks] = useState([]);
  const [loading, setLoading] = useState(false);

  //찍어보기
  const buy = async (modelType) => {
    let token = localStorage.getItem("token");
    let a = [];
    let model;

    if (modelType === "ml") {
      a = mlStocks.map((stock) => stock.code);
      model = "MACHINE_LEARNING";
    } else if (modelType === "dl") {
      a = dlStocks.map((stock) => stock.code);
      model = "DEEP_LEARNING";
    }

    console.log(
      JSON.stringify({
        trading: model,
        stocks: a,
        target: 0.0,
        loss: 0.0,
        seed: localStorage.getItem("amt"),
      })
    );

    let response = await fetch("https://api-dev.net/trading/portfolio/init", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
      body: JSON.stringify({
        trading: model,
        stocks: a,
        target: 0.0,
        loss: 0.0,
        seed: localStorage.getItem("amt"),
      }),
    });

    if (response.status == 200) {
      window.location.assign("/");
    }
  };

  // const buy = async (modelType) => {
  //     let token = localStorage.getItem("token");
  //     let a = [];
  //     let model;
  //
  //     if (modelType === "ml") {
  //         a = mlStocks.map(stock => stock.code);
  //         model = "MACHINE_LEARNING";
  //     } else if (modelType === "dl") {
  //         a = dlStocks.map(stock => stock.code);
  //         model = "DEEP_LEARNING";
  //     }
  //
  //     let response = await fetch("/trading/portfolio/init", {
  //         method: "POST",
  //         headers: {
  //             "Content-Type": "application/json",
  //             Authorization: "Bearer " + token,
  //         },
  //         body: JSON.stringify({
  //             trading: model,
  //             stocks: a,
  //             target: 0.0,
  //             loss: 0.0,
  //             seed: localStorage.getItem("amt"),
  //         }),
  //     });
  //     if (response.status == 200) {
  //         window.location.assign("/");
  //     }
  // };

  //찍어보기
  const loadData = async () => {
    setLoading(true);
    const formattedDate = "23/07/14";
    const url1 =
      "https://api-dev.net/trading/portfolio/profit/recommend?date=" +
      formattedDate;
    const url2 =
      "https://api-dev.net/trading/portfolio/profit/recommend/deeplearning?date=" +
      formattedDate;
    let response1 = await fetch(url1, {
      method: "GET",
      headers: { "Content-Type": "application/json" },
    });
    if (response1.status == 200) {
      const data = await response1.json();
      console.log("Response from url1:", data);
      setMlStocks(data);
    }

    let response2 = await fetch(url2, {
      method: "GET",
      headers: { "Content-Type": "application/json" },
    });
    if (response2.status == 200) {
      const data = await response2.json();
      console.log("Response from url2:", data);
      setDlStocks(data);
    }
    setTimeout(() => {
      setLoading(false);
    }, 1000);
  };
  useEffect(() => {
    setShowElements(true);
    loadData();
  }, []);

  //현재 날짜로 통신
  // const loadData = async () => {
  //     setLoading(true);
  //     const currentDate = new Date();
  //     const year = currentDate.getFullYear().toString().substr(-2);
  //     const month = (currentDate.getMonth() + 1).toString().padStart(2, "0");
  //     const day = currentDate.getDate().toString().padStart(2, "0");
  //     const formattedDate = `${year}/${month}/${day}`;
  //     const url1 = "/trading/portfolio/profit/recommend?date=" + formattedDate;
  //     const url2 = "/trading/portfolio/profit/recommend/deeplearning?date=" + formattedDate;
  //
  //     let response1 = await fetch(url1, {
  //         method: "GET",
  //         headers: {"Content-Type": "application/json"},
  //     });
  //     if (response1.status == 200) {
  //         const data = await response1.json();
  //         console.log("Response from url1:", data);
  //         setMlStocks(data);
  //     }
  //
  //     let response2 = await fetch(url2, {
  //         method: "GET",
  //         headers: {"Content-Type": "application/json"},
  //     });
  //     if (response2.status == 200) {
  //         const data = await response2.json();
  //         console.log("Response from url2:", data);
  //         setDlStocks(data);
  //     }
  //     setTimeout(() => {
  //         setLoading(false);
  //     }, 1000);
  // };
  // useEffect(() => {
  //     setShowElements(true);
  //     loadData();
  // }, []);

  return (
    <div className="tradingmodel-container">
      {loading && <LoadingBox />}
      {!loading && (
        <div
          style={{
            width: "100%",
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
          }}
        >
          <div className="mc" style={{ animationDelay: "1s" }}>
            <div className="model-title">머신 러닝 모델</div>
            {mlStocks &&
              mlStocks.map((data, index) => {
                return <InitModelStocks data={data} />;
              })}
            <button className="submit-port-button" onClick={() => buy("ml")}>
              {"선택"}
            </button>
          </div>

          <div className="mc" style={{ animationDelay: "1.5s" }}>
            <div className="model-title">딥 러닝 모델</div>
            {dlStocks &&
              dlStocks.map((data, index) => {
                return <InitModelStocks data={data} />;
              })}
            <button className="submit-port-button" onClick={() => buy("dl")}>
              {"선택"}
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default TradingModel;
