import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import KakaoLogo from "../assets/images/kakao-logo.svg";
import GoogleLogo from "../assets/images/google-logo.svg";
import CloseBtn from "../assets/images/x-solid.svg";
import { GoogleURL, KakaoURL } from "../assets/constants/ApiUrls";
import "../styles/LogIn.css";
import SignIn from "./SignIn";

const LogIn = ({ close, toRegister }) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [signup, setSignup] = useState(false);

  const login = async () => {
    if (email.length > 0 && password.length > 0) {
      let response = await fetch("/user/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ emailAddress: email, password: password }),
      });

      if (response.status == 200) {
        response = await response.json();
        localStorage.setItem("token", response.token);
        window.location.assign("/");
      } else {
        setEmail("");
        setPassword("");
        alert("잘못된 요청입니다!");
      }
    }
  };

  return (
    <div className="modal">
      <div className="loginModal">
        <div className="close">
          <div
            style={{
              width: "40px",
              height: "40px",
              cursor: "pointer",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
            }}
            onClick={() => close()}
          >
            <img src={CloseBtn} alt="close button" width={20} height={20} />
          </div>
        </div>
        <div
          style={{
            fontFamily: "Hana2-Heavy",
            fontSize: "22px",
            color: "black",
          }}
        >
          로그인
        </div>

        <div className="modalContents" style={{ marginTop: "10px" }}>
          <input
            name="email"
            className="input-form"
            type="text"
            placeholder="이메일"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />

          <input
            name="password"
            className="input-form"
            type="password"
            placeholder="비밀번호"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <button className="loginBtn" onClick={login}>
            로그인
          </button>
          <div
            style={{
              width: "350px",
              textAlign: "right",
              paddingRight: "20px",
            }}
          >
            <div onClick={() => setSignup(true)} className="link-to-register">
              회원가입
            </div>
          </div>
          <div className="social-box">
            <img
              className="googleLogo"
              src={GoogleLogo}
              alt="Google Logo"
              onClick={() => {
                window.location.assign(GoogleURL);
              }}
            />
            <img
              className="kakaoLogo"
              src={KakaoLogo}
              alt="Kakao Logo"
              onClick={() => {
                window.location.assign(KakaoURL);
              }}
            />
          </div>
        </div>
      </div>
      {signup && <SignIn close={close} />}
    </div>
  );
};

export default LogIn;
