import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import IdIcon from "../assets/images/icon_id.svg";
import PwIcon from "../assets/images/icon_pw.svg";
import KakaoLogo from "../assets/images/logo_kakao.svg";
import FacebookLogo from "../assets/images/logo_facbook.svg";
import GoogleLogo from "../assets/images/logo_google.svg";
import "../styles/LogIn.css";

const LogIn = ({ isOpen, close }) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  // useEffect(() => {
  //     const handleScroll = () => {
  //         // 모달이 열려있을 때는 스크롤 막기
  //         if (isOpen) {
  //             document.body.style.overflow = "hidden";
  //         } else {
  //             // 모달이 닫혔을 때는 스크롤 활성화
  //             document.body.style.overflow = "auto";
  //         }
  //     };
  //
  //     // 이벤트 리스너 등록
  //     window.addEventListener("scroll", handleScroll);
  //
  //     // 컴포넌트 언마운트 시 이벤트 리스너 해제
  //     return () => {
  //         window.removeEventListener("scroll", handleScroll);
  //
  //         // 컴포넌트가 언마운트될 때 스크롤을 다시 활성화
  //         document.body.style.overflow = "auto";
  //     };
  // }, [isOpen]);

  const loginHandler = (e) => {
    const { name, value } = e.target;
    const updatedState = {
      email: name === "email" ? value : email,
      password: name === "password" ? value : password,
    };
    setEmail(updatedState.email);
    setPassword(updatedState.password);
  };

  const loginClickHandler = () => {
    fetch("https://api-dev.net/auth/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, password }),
    })
      .then((res) => res.json())
      .then((res) => console.log(res));
  };

  return (
    <>
      {isOpen && (
        <div className="modal">
          <div onClick={close}>
            <div className="loginModal">
              <span className="close" onClick={close}>
                &times;
              </span>
              <div className="modalContents" onClick={isOpen}>
                <input
                  name="email"
                  className="loginId"
                  type="text"
                  placeholder="아이디"
                  onChange={loginHandler}
                />

                <input
                  name="password"
                  className="loginPw"
                  type="password"
                  placeholder="비밀번호"
                  onChange={loginHandler}
                />
                <div className="loginMid">
                  <label className="autoLogin" htmlFor="hint">
                    <input type="checkbox" id="hint" /> 로그인 유지하기
                  </label>
                  <div className="autoLogin">아이디/비밀번호 찾기</div>
                </div>
                <button className="loginBtn" onClick={loginClickHandler}>
                  로그인
                </button>
                <div className="socialBox">
                  <div className="kakao">
                    <img
                      className="kakaoLogo"
                      src={KakaoLogo}
                      alt="Kakao Logo"
                    />
                    <div className="kakaoText">카카오 계정으로 신규가입</div>
                  </div>
                  <div className="facebook">
                    <img
                      className="facebookLogo"
                      src={FacebookLogo}
                      alt="Facebook Logo"
                    />
                    <div className="facebookText">
                      페이스북 계정으로 신규가입
                    </div>
                  </div>
                  <div className="google">
                    <img
                      className="googleLogo"
                      src={GoogleLogo}
                      alt="Google Logo"
                    />
                    <div className="googleText">구글 계정으로 신규가입</div>
                  </div>
                </div>
                <div className="loginEnd">
                  <div className="loginLine">
                    회원이 아니신가요? <Link to="/signup">회원가입</Link>
                  </div>
                  <div className="noUser">비회원</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default LogIn;
