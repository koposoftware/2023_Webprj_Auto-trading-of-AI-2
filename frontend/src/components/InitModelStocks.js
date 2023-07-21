import React, { useEffect } from "react";
import "../styles/Stock.css";
const InitModelStocks = ({ data, loadStock }) => {
  const LOGO_URL =
    "https://file.alphasquare.co.kr/media/images/stock_logo/kr/" +
    data.code +
    ".png";

  const truncateString = (str) => {
    if (str.length > 6) {
      return str.slice(0, 6) + "...";
    } else {
      return str;
    }
  };

  useEffect(() => {});
  return (
    <div className="stock-container" onClick={() => loadStock(data.code)}>
      <img src={LOGO_URL} alt="logo" className="stock-logo" />
      <div className="stock-info">
        <div style={{ fontFamily: "Hana2-Heavy", width: "100%" }}>
          {truncateString(data.name)}
        </div>
        <div style={{ width: "100%", display: "flex" }}>
          <div
            style={{
              fontFamily: "Hana2-CM",
              display: "flex",
              fontSize: "14px",
            }}
          >
            {Math.round(data.currentPrice)}
            <span style={{ fontFamily: "Hana2-Light", marginLeft: "5px" }}>
              KRW
            </span>
          </div>
          <div
            style={{
              fontFamily: "Hana2-CM",
              color: "red",
              marginLeft: "15px",
              fontSize: "11px",
            }}
          >
            <span>예상가 {data.predictPrice}KRW</span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default InitModelStocks;
