import React from "react";
import Header from "./components/Header";
import Sidebar from "./components/Siderbar";
import Showbox from "./components/Showbox";
import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Redirect from "./components/Redirect";
import LogIn from "./components/LogIn";
import KaKaoRedirect from "./components/KakaoRedirect";
import SignIn from "./components/SignIn";
import Start from "./components/Start";
import Init from "./components/Init";
import TradingModel from "./components/TradingModel";
import Agree from "./components/Agree"
import TradeHistory2 from "./components/TradeHistory2"

class App extends React.Component {
  render() {
    return (
      <div className="container">
        <div className="container-box">
          <Header />
          <BrowserRouter>
          <div className="main-container">
            <Sidebar />
            {/*<BrowserRouter>*/}
              <Routes>
                <Route path="/main" element={<Start />} />
                <Route path="/tradehistory" element={<TradeHistory2 />} />
                <Route path="/" element={<Showbox />} />
                <Route path="/init/portfolio" element={<Init />} />
                <Route path="/redirect" element={<Redirect />} />
                <Route path="/model" element={<TradingModel />} />
                <Route path="/login" element={<LogIn />} />
                <Route path="/signup" element={<SignIn />} />
                <Route path="/kakao_redirect" element={<KaKaoRedirect />} />
              </Routes>
            {/*</BrowserRouter>*/}
          </div>
          </BrowserRouter>
        </div>
      </div>
    );
  }
}

export default App;
