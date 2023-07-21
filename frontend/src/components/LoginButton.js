import React, { useState } from "react";
import LogIn from "./LogIn";
import "../styles/LoginButton.css";

const LoginButton = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  return (
    <>
      <button className="login-button" onClick={openModal}>
        로그인
      </button>
      {isModalOpen && <LogIn close={closeModal} />}
    </>
  );
};

export default LoginButton;
