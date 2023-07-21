import React, {useState} from "react";
import "../styles/Agree.css";
import AgreeModal from "./AgreeModal";


const Agree = () => {
    const [modalOpen, setModalOpen] = useState(false);

    const handleModalOpen = () => {
        setModalOpen(true);
    }

    const handleModalClose = () => {
        setModalOpen(false);
    }

    return (
        <div className="agree-container">
            <div className="agree-box">
                <div className="agree-head">
                    AI 주식 자동 매매,<br/>
                    <br/>
                    누구나 할 수 있도록
                </div>
                <button className="agree-button" onClick={handleModalOpen}>
                    동의하기
                </button>

                {modalOpen &&
                    <AgreeModal closeModal={handleModalClose} />
                }
            </div>
        </div>
    );
};

export default Agree;
