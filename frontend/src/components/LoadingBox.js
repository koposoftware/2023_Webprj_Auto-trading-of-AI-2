import React from "react";
import Spinner from "./Spinner";
const LoadingBox = () => {
  return (
    <div
      style={{
        width: "100%",
        height: "800px",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        backgroundColor: "#ffffff",
        paddingTop: "20px",
        boxSizing: "border-box",
      }}
    >
      <Spinner />
    </div>
  );
};

export default LoadingBox;
