import React, { useState } from "react";
import { Nav, Navbar, NavItem, Button } from "reactstrap";
import { NavLink } from "react-router-dom";

function Header(props) {
  const [sidebar, setSidebar] = useState(false);
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
      {/* Custom sidebar */}
      <Navbar
        className={sidebar ? "nav-menu active bg-linear" : "nav-menu"}
        onClick={showSidebar}
      >
        <Nav className="nav-menu-items" navbar vertical>
          <NavItem className="menu-bars">
            <i
              class="fa fa-times text-white mx-2"
              aria-hidden="true"
              onClick={showSidebar}
            ></i>
          </NavItem>
          <NavItem className="nav-text ">
            <NavLink className="nav-link px-3" to="/home">
              <i className="fa fa-home fa-lg mx-2" aria-hidden="true"></i>
              <span>Home</span>
            </NavLink>
          </NavItem>
          <NavItem className="nav-text">
            <NavLink className="nav-link px-3" to="/menu">
              <i className="fa fa-cutlery fa-lg mx-2" aria-hidden="true"></i>
              <span>Menu</span>
            </NavLink>
          </NavItem>
          <NavItem className="nav-text">
            <NavLink className="nav-link px-3" to="/order">
              <i
                className="fa fa-shopping-cart fa-lg mx-2"
                aria-hidden="true"
              ></i>
              <span>Order</span>
            </NavLink>
          </NavItem>
          <NavItem className="nav-text">
            <NavLink className="nav-link px-3" to="/setting">
              <i className="fa fa-sliders fa-lg mx-2" aria-hidden="true"></i>
              <span>Setting</span>
            </NavLink>
          </NavItem>
          <NavItem className="nav-text">
            <NavLink className="nav-link px-3" to="/login">
              <i className="fa fa-sign-in fa-lg mx-2" aria-hidden="true"></i>
              <span>Login</span>
            </NavLink>
          </NavItem>
        </Nav>
      </Navbar>
      {/* End custom sidebar */}
    </div>
  );
}
export default Header;
