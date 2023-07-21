import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import KakaoLogo from "../assets/images/kakao-logo.svg";
import GoogleLogo from "../assets/images/google-logo.svg";
import CloseBtn from "../assets/images/x-solid.svg";
import "../styles/SignIn.css";

const SignIn = ({ isOpen, close }) => {
  const [nickname, setNickname] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [rePassword, setRePassword] = useState("");

  const signup = async () => {
    if (
      password.length > 0 &&
      rePassword.length > 0 &&
      password == rePassword
    ) {
      let response = await fetch("https://api-dev.net/user/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          emailAddress: email,
          password: password,
          nickname: nickname,
        }),
      });
      if (response.status == 200) {
        response = await response.json();
        alert("회원가입 완료");
        window.location.assign("/");
      }
    } else {
      // 수정 예정
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
              fontSize: "18px",
            }}
            onClick={() => close()}
          >
            <img src={CloseBtn} alt="close button" width={20} height={20} />
          </div>
        </div>

        <div
          style={{
            width: "100%",
            fontFamily: "Hana2-Heavy",
            fontSize: "22px",
            textAlign: "center",
            color: "black",
          }}
        >
          회원가입
        </div>
        <div className="modalContents" style={{ marginTop: "0" }}>
          <input
            name="nickname"
            className="input-form"
            type="text"
            placeholder="닉네임"
            onChange={(e) => setNickname(e.target.value)}
          />

          <input
            name="email"
            className="input-form"
            type="text"
            placeholder="이메일"
            onChange={(e) => setEmail(e.target.value)}
          />

          <input
            name="password"
            className="input-form"
            type="password"
            placeholder="비밀번호"
            onChange={(e) => setPassword(e.target.value)}
          />

          <input
            name="re-password"
            className="input-form"
            type="password"
            placeholder="비밀번호 재입력"
            onChange={(e) => setRePassword(e.target.value)}
          />
          <button className="loginBtn" onClick={signup}>
            회원가입
          </button>
        </div>
      </div>
    </div>
  );
};

export default SignIn;
