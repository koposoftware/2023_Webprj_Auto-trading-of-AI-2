import React, { useState } from "react";

const LogOutButton = () => {
  return (
    <button
      style={{
        fontSize: "14px",
        fontfFamily: "Hana2-Light",
        border: "none",
        backgroundColor: "#ffffff",
        cursor: "pointer",
      }}
      onClick={() => {
        localStorage.removeItem("token");
        window.location.assign("/main");
      }}
    >
      로그아웃
    </button>
  );
};

export default LogOutButton;
