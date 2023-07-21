import React, { useState } from "react";
import SubmitPortButton from "./SubmitPortButton";
import "../styles/SetPort.css";
import { Link } from "react-router-dom";
const Init = () => {
    const [investmentAmount, setInvestmentAmount] = useState("");
    const [desiredReturnRate, setDesiredReturnRate] = useState("");
    const [stopLoss, setStopLoss] = useState("");

    const handleInvestmentAmountChange = (e) => {
        const value = e.target.value.replace(/[^0-9.]/g, ""); // 숫자와 소수점 이외의 값 제거
        localStorage.setItem("amt", value);
        setInvestmentAmount(value);
    };

    const handleDesiredReturnRateChange = (e) => {
        const value = e.target.value;
        setDesiredReturnRate(value);
    };

    const handleStopLossChange = (e) => {
        const value = e.target.value;
        setStopLoss(value);
    };

    return (
        <div className="setport-container">
            <div className="setport-body-container-box">
                <div
                    style={{
                        textAlign: "center",
                        marginTop: "150px",
                        marginBottom: "100px",
                    }}
                >
                    투자금액과 목표수익률을  <br />
                    입력해주세요.
                </div>
                <div className="input-container">
                    <input
                        type="number"
                        step="0.01"
                        id="investment-amount"
                        placeholder="투자금액"
                        value={investmentAmount}
                        onChange={handleInvestmentAmountChange}
                    />
                    <input
                        type="number"
                        step="0.01" // 소수점 2자리까지
                        id="desired-return-rate"
                        placeholder="목표수익률(%)"
                        value={desiredReturnRate}
                        onChange={handleDesiredReturnRateChange}
                    />
                    {/*<input*/}
                    {/*  type="number"*/}
                    {/*  step="0.01" // 소수점 2자리까지*/}
                    {/*  id="stop-loss"*/}
                    {/*  placeholder="STOP-LOSS(%)"*/}
                    {/*  value={stopLoss}*/}
                    {/*  onChange={handleStopLossChange}*/}
                    {/*/>*/}
                </div>
                <Link to="/model">
                    <SubmitPortButton buttonText="제출하기" />
                </Link>
            </div>
        </div>
    );
};

export default Init;