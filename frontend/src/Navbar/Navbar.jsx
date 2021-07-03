import React, { useState, useContext, useEffect } from "react";
import { AccountContext } from "../Account";
import { Dropdown } from "react-bootstrap";
import { Link } from "react-router-dom";
import { useSelector } from "react-redux";
import { useHistory } from "react-router-dom";
function Navbar() {
  const history = useHistory();
  const { loginState } = useSelector((state) => state.common);
  const [isUserLoggedIn, setIsUserLoggedIn] = useState(false);
  const { getSession, logout } = useContext(AccountContext);

  useEffect(() => {
    if (getSession) {
      getSession()
        .then((session) => {
          console.log("session ", session);
          setIsUserLoggedIn(true);
          sessionStorage.setItem("accesstoken", session.accessToken.jwtToken);
          sessionStorage.setItem("idtoken", session.idToken.jwtToken);
          sessionStorage.setItem(
            "role",
            session.idToken.payload["custom:role"]
          );
          if (session && session["idToken"]["payload"]["custom:role"] == "agent") {
            history.push("/edit-agent/" + session["idToken"]["payload"]["email"]);
          } else {
            history.push("/admin/dash");
          }
        })
        .catch((err) => setIsUserLoggedIn(false));
    }
  }, [loginState]);

  const handleLogout = () => {
    logout();
  };

  return (
    <nav
      className="navbar navbar-expand-md navbar-dark"
      style={{ backgroundColor: "#97144d" }}
    >
      <div className="container">
        <a className="navbar-brand" href="/">
          <img src={process.env.PUBLIC_URL + "/logo.png"} alt="" height="40px" />
        </a>
        <div className="collapse navbar-collapse" id="navbarToggleDemo2">
          {isUserLoggedIn ? (
            <div className="d-flex ml-auto">
              <Dropdown className="ml-auto">
                <Dropdown.Toggle variant="light" id="dropdown-basic">
                  Prateek
                </Dropdown.Toggle>

                <Dropdown.Menu>
                  {/* <Dropdown.Item href="/addresses">
                    Manage Addresses
                  </Dropdown.Item> */}
                  <Dropdown.Item onClick={handleLogout} href="/">
                    Logout
                  </Dropdown.Item>
                </Dropdown.Menu>
              </Dropdown>
            </div>
          ) : (
            <ul className="navbar-nav ml-auto">
              <li className="nav-item">
                <Link className="nav-link lightBoldFont" to="/sign-in">
                  Log In
                </Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link lightBoldFont" to="/sign-up">
                  Sign up
                </Link>
              </li>
            </ul>
          )}
        </div>
      </div>
    </nav>
  );
}

export default Navbar;
