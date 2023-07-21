import React, { useState } from "react";
import "../styles/Start.css";
import LogIn from "./LogIn";
import SignIn from "./SignIn";

const Start = () => {
  const [modal, setModal] = useState(false);
  const [sigup, setSignup] = useState(false);

    const clickHandler = () => {
      if (localStorage.getItem("token") != null) {
        window.location.assign("/");
      } else {
        setModal(true);
      }
    };

  return (
    <div className="start-container">
      <div className="box">
        주식 투자에 필요한 모든 것 <br />
        <br />
        AI 자동 매매의 기준, ONE-AI
        <button className="start-login-button" onClick={() => clickHandler()}>
          지금 시작하기
        </button>
        {modal && (
          <LogIn
            close={() => setModal(false)}
            toRegister={() => {
              setModal(false);
              setSignup(true);
            }}
          />
        )}
        {!modal && sigup && <SignIn />}
      </div>
    </div>
  );
};

export default Start;
