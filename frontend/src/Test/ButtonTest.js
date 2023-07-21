import React, { useState } from "react";
import LogIn from "../components/LogIn";

const Button = () => {
    const [isModalOpen, setIsModalOpen] = useState(false);

    const openModal = () => {
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
    };

    return (
        <>
            <button onClick={openModal}>Modal Open</button>
            {isModalOpen && <LogIn isOpen={isModalOpen} close={closeModal} />}
        </>
    );
};

export default Button;
