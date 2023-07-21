import React, { useState } from "react";
import SignIn from "./SignIn";
import "../styles/SigninButton.css";

const SigninButton = () => {
    const [isModalOpen, setIsModalOpen] = useState(false);

    const openModal = () => {
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
    };

    return (
        <>
            <button className="signin-button" onClick={openModal}>회원가입</button>
            {isModalOpen && <SignIn isOpen={isModalOpen} close={closeModal} />}
        </>
    );
};

export default SigninButton;
