import React from "react";
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

function Home(props) {
  const SearchBar = () => {
    return (
      <Navbar light expand="md" className="container shadow-sm">
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
      <div className="container my-3">
        <h2>Featured restaurants</h2>
        <Row>
          <Col
            md={4}
            classNamRowe="media bg-white shadow-sm rounded align-items-center text-sm"
          >
            <Fade in>
              <Card className="p-0">
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
              </Card>
            </Fade>
          </Col>
          <Col
            md={4}
            classNamRowe="media bg-white shadow-sm rounded align-items-center text-sm"
          >
            <Fade in>
              <Card className="p-0">
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
              </Card>
            </Fade>
          </Col>
        </Row>
      </div>
    );
  };

  const MenuList = () => {
    return (
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
