import React, { useEffect, useRef } from "react";
import { createChart, ColorType } from "lightweight-charts";

const LineChart = ({ data }) => {
  const chartContainerRef = useRef(null);

  const makeDataToChartData = () => {
    const result = [];
    for (let i = 0; i < data.length; i++) {
      let tmp = {};
      tmp["time"] = data[i]["date"].substring(0, 10);
      tmp["value"] = data[i]["profit"];
      result.push(tmp);
    }
    return result;
  };

  useEffect(() => {
    const chartOptions = {
      layout: {
        backgroundColor: "white",
        textColor: "black",
      },
      width: 570,
      height: 250,
    };

    const chart = createChart(chartContainerRef.current, chartOptions);
    const areaSeries = chart.addLineSeries({
      color: "#7EEB96",
    });
    areaSeries.setData(makeDataToChartData());

    chart.timeScale().fitContent();

    return () => {
      chart.remove();
    };
  });

  return <div className="chart-container" ref={chartContainerRef}></div>;
};

export default LineChart;
