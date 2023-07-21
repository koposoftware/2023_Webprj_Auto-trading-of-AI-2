import React from "react";
import { Link } from "react-router-dom";
import "../styles/SidebarItem.css";

const SidebarItem = ({ menu }) => {
    return (
        <Link to={menu.path} className="sidebar-item">
            {menu.image && (
                <img src={menu.image} alt={menu.name} style={{ width: "25px", height: "25px", marginRight: "10px" }} />
            )}
            <p>{menu.name}</p>
        </Link>
    );
};

export default SidebarItem;
