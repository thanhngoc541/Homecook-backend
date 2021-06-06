import React, {useState} from "react";
import {
  Collapse,
  Navbar,
  NavbarToggler,
  NavbarBrand,
  Nav,
  NavItem,
  NavLink,
  Container,
  Row,
  Col,
  Button,
  Badge
} from 'reactstrap';
function Order() {
  const [isOpen, setIsOpen] = useState(false);
  const toggle = () => setIsOpen(!isOpen);
  return (
    <div className="OrderNav">
      <Navbar color="light" light expand="md">
        <NavbarBrand href="/">Status</NavbarBrand>
        <NavbarToggler onClick={toggle} />
        <Collapse isOpen={isOpen} navbar>
          <Nav className="mr-auto" navbar>
            <NavItem>
              <NavLink href="/pending?id='?'">Pending</NavLink>
            </NavItem>
            <NavItem>
              <NavLink href="/accept">Accepted</NavLink>
            </NavItem>
            <NavItem>
              <NavLink href="/delivering">Delivering</NavLink>
            </NavItem>
            <NavItem>
              <NavLink href="/delivered">Delivered</NavLink>
            </NavItem>
            <NavItem>
              <NavLink href="/finished">Finished</NavLink>
            </NavItem>
            <NavItem>
              <NavLink href="/rejected">Rejected</NavLink>
            </NavItem>
            <NavItem>
              <NavLink href="/cancelled">Cancelled</NavLink>
            </NavItem>
          </Nav>
        </Collapse>
      </Navbar>
      <Container>
        <Row>
          <Col>
            <div className="item">
              <div className="d-flex align-items-center">
              <h6 xs="8">Burger King <Badge color="secondary"></Badge></h6>
                <p xs="4" className="icon">hi</p>
              </div>
              <div className="d-flex align-items-center">
                <p className="small">
                  <i>timestamp</i>
                </p>
              </div>
              <p className="text-dark mb-2">
                <span className="mr-2 text-black" >1</span>
                Burger
              </p>
              <p className="text-dark mb-2">
                <span className="mr-2 text-black">2</span>
                Cheeese
              </p>
              <div className="d-flex align-items-center row pt-2 mt-3">
              <Button outline color="info">info</Button>{' '}
              </div>
            </div>
          </Col>
          <Col></Col>
          <Col></Col>
          <Col></Col>
        </Row>
      </Container>
    </div>
  );
}

export default Order;
