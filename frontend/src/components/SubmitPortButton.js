import React, { useState } from "react";
import Submit from "./SubmitPort";
import "../styles/SubmitPortButton.css";

const SubmitPortButton = ({ buttonText }) => {
  // buttonText prop 추가
  const [isModalOpen, setIsModalOpen] = useState(false);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  return (
    <>
      <button className="submit-port-button" onClick={openModal}>
        {buttonText} {/* buttonText prop을 렌더링 */}
      </button>
      {isModalOpen && <Submit close={closeModal} />}
    </>
  );
};

export default SubmitPortButton;
