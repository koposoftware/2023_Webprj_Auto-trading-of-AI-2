import React from "react";
import { Link } from "react-router-dom";
import SidebarItem from "./SidebarItem";
import "../styles/Sidebar.css";
import Calender from "../assets/images/calender.svg";
import Member from "../assets/images/member.svg";
import Home from "../assets/images/home.png";
import Money from "../assets/images/money.svg"

const Sidebar = () => {
  const menus = [
    { name: "홈", image: Home, path: "/" },
    { name: "거래내역", image: Money, path: "/tradehistory" },
  ];

  return (
      <div className="sidebar">
        {menus.map((menu, index) => (
            <SidebarItem key={index} menu={menu} />
        ))}
      </div>
  );
};

export default Sidebar;
