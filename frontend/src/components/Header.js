import React from "react";
import HanaLogo from "../assets/images/hana.png";
import "../styles/Header.css";
import LoginButton from "./LoginButton";
import SigninButton from "./SigninButton";
import { BrowserRouter as Router } from "react-router-dom";
import LogOutButton from "./LogOutButton";
const Header = () => {
  const stop = async () => {
    let token = localStorage.getItem("token");
    let response = await fetch("/trading/portfolio/shutdown", {
      method: "POST",
      headers: { Authorization: "Bearer " + token },
    });
    if (response.status == 200) {
      window.location.reload();
    }
  };
  return (
    <Router>
      <div className="header-container">
        <div
          className="logo-and-text"
          onClick={() => {
            window.location.assign("/");
          }}
        >
          <img src={HanaLogo} alt="HanaLogo" className="logo" />
          <span className="logotext">Hana</span>
        </div>
        <ul className="nav-links">
          {localStorage.getItem("token") != null ? (
            <>
              <li>
                <div className="portfolio-stop-btn" on onClick={stop}>
                  포트폴리오 종료
                </div>
              </li>{" "}
              <li>
                <LogOutButton />
              </li>
            </>
          ) : (
            <>
              {" "}
              <li>
                <LoginButton />
              </li>
              <li>
                <SigninButton />
              </li>
            </>
          )}
        </ul>
      </div>
    </Router>
  );
};

export default Header;
