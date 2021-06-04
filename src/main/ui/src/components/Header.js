import React, { useState } from "react";
import { Nav, Navbar, NavItem, Button } from "reactstrap";
import { NavLink } from "react-router-dom";

function Header(props) {
  const [sidebar, setSidebar] = useState(true);
  const showSidebar = () => setSidebar(!sidebar);

  return (
    <div className="header">
      <Navbar>
        <Nav>
          <NavItem className="menu-bars">
            <i
              class="fa fa-bars text-white"
              aria-hidden="true"
              onClick={showSidebar}
            ></i>
          </NavItem>
        </Nav>
      </Navbar>
      {/* Cutom sidebar */}
      <Navbar
        className={sidebar ? "nav-menu active bg-linear" : "nav-menu"}
        onClick={showSidebar}
      >
        <Nav className="nav-menu-items" navbar vertical>
          <NavItem className="menu-bars">
            <i
              class="fa fa-times text-white"
              aria-hidden="true"
              onClick={showSidebar}
            ></i>
          </NavItem>
          <NavItem className="nav-text ">
            <NavLink className="nav-link px-3" to="/home">
              <i class="fa fa-home fa-lg" aria-hidden="true"></i> Home
            </NavLink>
          </NavItem>
          <NavItem className="nav-text">
            <NavLink className="nav-link px-3" to="/menu">
              <i class="fa fa-cutlery fa-lg" aria-hidden="true"></i> Menu
            </NavLink>
          </NavItem>
          <NavItem className="nav-text">
            <NavLink className="nav-link px-3" to="/order">
              <i class="fa fa-shopping-cart fa-lg" aria-hidden="true"></i>{" "}
              Orders
            </NavLink>
          </NavItem>
          <NavItem className="nav-text">
            <NavLink className="nav-link px-3" to="/setting">
              <i class="fa fa-sliders fa-lg" aria-hidden="true"></i> Settings
            </NavLink>
          </NavItem>
          <NavItem className="nav-text">
            <NavLink className="nav-link px-3" to="/login">
              <i className="fa fa-sign-in fa-lg" aria-hidden="true"></i> Login
            </NavLink>
          </NavItem>
        </Nav>
      </Navbar>
      {/* End custom sidebar */}
    </div>
  );
}
export default Header;
