import React, { useEffect, useState } from "react";
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from "chart.js";
import { Doughnut } from "react-chartjs-2";
import "../styles/Doughnut.css";
import { useSearchParams } from "react-router-dom";
ChartJS.register(ArcElement, Tooltip, Legend);

const options = {
  plugins: {
    legend: {
      display: false,
    },
  },
};

export function DoughnutChart({ stocks }) {
  const keys = Object.keys(stocks);

  const data = {
    labels: ["Red", "Blue", "Yellow", "Green", "Purple"],
    datasets: [
      {
        label: "# of Votes",
        data: [stocks[keys[0]], stocks[keys[1]], stocks[keys[2]], stocks[keys[3]], stocks[keys[4]]],
        backgroundColor: [
          "#00857F",
          "#32B09D",
          "#6BD3CC",
          "#7EEB96",
          "#10D93C",
        ],
        borderColor: ["#EFF0F6"],
        borderWidth: 3,
        borderRadius: 15,
      },
    ],
  };

  const truncateString = (str) => {
    if (str.length > 7) {
      return str.slice(0, 7) + "...";
    } else {
      return str;
    }
  };

  useEffect(() => {
    console.log("도넛차트");
    console.log(stocks);
  }, []);
  return (
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        width: "100%",
        height: "200px",
        padding: "0 15px",
        boxSizing: "border-box",
      }}
    >
      <div className="legend-container">
        <div className="legend">
          <div className="legend-badge legend-first"></div>
          {stocks[keys[0]]}% {truncateString(keys[0])}
        </div>
        <div className="legend">
          <div className="legend-badge legend-second"></div>
          {stocks[keys[1]]}% {truncateString(keys[1])}
        </div>
        <div className="legend">
          <div className="legend-badge legend-third"></div>
          {stocks[keys[2]]}% {truncateString(keys[2])}
        </div>
        <div className="legend">
          <div className="legend-badge legend-fourth"></div>
          {stocks[keys[3]]}% {truncateString(keys[3])}
        </div>
        <div className="legend">
          <div className="legend-badge legend-fivth"></div>
          {stocks[keys[4]]}% {truncateString(keys[4])}
        </div>
      </div>
      <div style={{ width: "180px" }}>
        <Doughnut data={data} options={options} />
      </div>
    </div>
  );
}
