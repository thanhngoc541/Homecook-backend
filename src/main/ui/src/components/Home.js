<<<<<<< Updated upstream
import React, { useState } from "react";
=======
import React from "react";
>>>>>>> Stashed changes
import {
  Input,
  InputGroup,
  Button,
  Col,
  Navbar,
  Nav,
  NavItem,
  Form,
  FormGroup,
  Card,
  CardBody,
  CardTitle,
  CardText,
  CardImg,
  Row,
  Media,
} from "reactstrap";
import { Fade, Stagger } from "react-animation-components";
<<<<<<< Updated upstream
import { Link, NavLink } from "react-router-dom";
=======
>>>>>>> Stashed changes

import data from "../data";
function Home(props) {
<<<<<<< Updated upstream
  const [dishes, setDishes] = useState(data);

  const SearchBar = () => {
    return (
      <Navbar light expand="md" className="container-fluid shadow-sm">
=======
  const SearchBar = () => {
    return (
      <Navbar light expand="md" className="container shadow-sm">
>>>>>>> Stashed changes
        <Nav navbar className="ml-auto">
          <NavItem>
            <Form>
              <FormGroup>
                <InputGroup>
                  <Input
                    type="text"
                    name="search-bar"
                    id="search-bar"
                    placeholder="Search here"
                  />
                  <Button>Search</Button>
                </InputGroup>
              </FormGroup>
            </Form>
          </NavItem>
        </Nav>
      </Navbar>
    );
  };

  const HomeCookList = () => {
    return (
<<<<<<< Updated upstream
      <div className="container-fluid my-3">
=======
      <div className="container my-3">
>>>>>>> Stashed changes
        <h2>Featured restaurants</h2>
        <Row>
          <Col
            md={4}
            classNamRowe="media bg-white shadow-sm rounded align-items-center text-sm"
          >
            <Fade in>
              <Card className="p-0">
<<<<<<< Updated upstream
                <Link to={`/restaurant`}>
                  <CardBody className="row p-2">
                    <Col md={2} className="bg-light rounded p-3 mx-3">
                      <CardImg
                        width="100%"
                        src="assests/images/burgerking.png"
                        alt="demo"
                        className="img-fluid"
                      />
                    </Col>
                    <Col md={{ size: "auto" }} className="mx-3 py-2">
                      <CardTitle>
                        <strong>Burger King</strong>
                      </CardTitle>
                      <CardText className="small">
                        <i
                          className="fa fa-star text-warning mr-1"
                          aria-hidden="true"
                        ></i>
                        <span>0.8</span> (873)
                        <i
                          className="fa fa-usd ml-3 mr-1 text-success"
                          aria-hidden="true"
                        ></i>
                        <span>6.2</span>
                      </CardText>
                    </Col>
                  </CardBody>
                </Link>
=======
                <CardBody className="row p-2">
                  <Col md={2} className="bg-light rounded p-3 mx-3">
                    <CardImg
                      width="100%"
                      src="assests/images/burgerking.png"
                      alt="demo"
                      className="img-fluid"
                    />
                  </Col>
                  <Col md={{ size: "auto" }} className="mx-3 py-2">
                    <CardTitle>
                      <strong>Burger King</strong>
                    </CardTitle>
                    <CardText className="small">
                      <i
                        className="fa fa-star text-warning mr-1"
                        aria-hidden="true"
                      ></i>
                      <span>0.8</span> (873)
                      <i
                        className="fa fa-usd ml-3 mr-1 text-success"
                        aria-hidden="true"
                      ></i>
                      <span>6.2</span>
                    </CardText>
                  </Col>
                </CardBody>
>>>>>>> Stashed changes
              </Card>
            </Fade>
          </Col>
          <Col
            md={4}
            classNamRowe="media bg-white shadow-sm rounded align-items-center text-sm"
          >
            <Fade in>
              <Card className="p-0">
<<<<<<< Updated upstream
                <Link to={`/restaurant`}>
                  <CardBody className="row p-2">
                    <Col md={2} className="bg-light rounded p-3 mx-3">
                      <CardImg
                        width="100%"
                        src="assests/images/burgerking.png"
                        alt="demo"
                        className="img-fluid"
                      />
                    </Col>
                    <Col md={{ size: "auto" }} className="mx-3 py-2">
                      <CardTitle>
                        <strong>Burger King</strong>
                      </CardTitle>
                      <CardText className="small">
                        <i
                          className="fa fa-star text-warning mr-1"
                          aria-hidden="true"
                        ></i>
                        <span>0.8</span> (873)
                        <i
                          className="fa fa-usd ml-3 mr-1 text-success"
                          aria-hidden="true"
                        ></i>
                        <span>6.2</span>
                      </CardText>
                    </Col>
                  </CardBody>
                </Link>
=======
                <CardBody className="row p-2">
                  <Col md={2} className="bg-light rounded p-3 mx-3">
                    <CardImg
                      width="100%"
                      src="assests/images/burgerking.png"
                      alt="demo"
                      className="img-fluid"
                    />
                  </Col>
                  <Col md={{ size: "auto" }} className="mx-3 py-2">
                    <CardTitle>
                      <strong>Burger King</strong>
                    </CardTitle>
                    <CardText className="small">
                      <i
                        className="fa fa-star text-warning mr-1"
                        aria-hidden="true"
                      ></i>
                      <span>0.8</span> (873)
                      <i
                        className="fa fa-usd ml-3 mr-1 text-success"
                        aria-hidden="true"
                      ></i>
                      <span>6.2</span>
                    </CardText>
                  </Col>
                </CardBody>
>>>>>>> Stashed changes
              </Card>
            </Fade>
          </Col>
        </Row>
      </div>
    );
  };

  const MenuList = () => {
    return (
<<<<<<< Updated upstream
      <div className="container-fluid my-3">
        <h2>Featured Dishes</h2>
        <Row>
          {dishes.map((dish) => {
            const {
              DishId,
              HomeCookID,
              DishName,
              Price,
              IsAvailable,
              Description,
              ImageURL,
            } = dish;
            return (
              <Col md={4} key={DishId}>
                <Fade in>
                  <Card>
                    <CardImg
                      top
                      width="100%"
                      src={ImageURL}
                      alt={DishId}
                      className="img-fluid menu-dish rounded"
                    />
                    <CardBody>
                      <CardTitle>
                        <h3>{DishName}</h3>
                      </CardTitle>
                      <CardText>
                        DishID: {dish.DishId}
                        HomeCookID: {HomeCookID}
                        Price:{Price}
                        IsAvailable:{IsAvailable}
                        Description:{Description}
                      </CardText>
                    </CardBody>
                  </Card>
                </Fade>
              </Col>
            );
          })}
=======
      <div className="container my-3">
        <h2>Featured foods</h2>
        <Row>
          <Col md={4}>
            <Fade in>
              <Card>
                <CardImg
                  top
                  width="100%"
                  src="assests/images/food1.jpg"
                  alt="Spicy Na Thai Pizza"
                  className="img-fluid rounded"
                />
                <CardBody>
                  <CardTitle>
                    <h3>Spicy Na Thai Pizza</h3>
                  </CardTitle>
                </CardBody>
              </Card>
            </Fade>
          </Col>
          <Col md={4}>
            <Fade in>
              <Card>
                <CardImg
                  top
                  width="100%"
                  src="assests/images/food1.jpg"
                  alt="Spicy Na Thai Pizza"
                  className="img-fluid rounded"
                />
                <CardBody>
                  <CardTitle>
                    <h3>Spicy Na Thai Pizza</h3>
                  </CardTitle>
                </CardBody>
              </Card>
            </Fade>
          </Col>
>>>>>>> Stashed changes
        </Row>
      </div>
    );
  };

  return (
    <div className="bg-grey">
      <SearchBar />
      <HomeCookList />
      <MenuList />
    </div>
  );
}
export default Home;
