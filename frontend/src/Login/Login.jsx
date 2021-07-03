import React, { useState, useContext } from "react";
import { AccountContext } from "../Account";
import { Link } from "react-router-dom";
import { useHistory } from "react-router-dom";
import { Alert } from "react-bootstrap";
import { useDispatch } from "react-redux";
import {
  changeLoadingState,
  changeLoginState,
} from "../reducers/CommonReducer";
function Login() {
  const history = useHistory();
  const dispatch = useDispatch();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const { authenticate } = useContext(AccountContext);
  const handleEmail = (event) => setEmail(event.target.value);
  const handlePassword = (event) => setPassword(event.target.value);
  const [errorToShow, setErrorToShow] = useState("");

  const handleClick = () => {
    dispatch(changeLoadingState(true));
    authenticate(email, password)
      .then((data) => {
        console.log("Log inn !!", data);
        setErrorToShow("");
        dispatch(changeLoadingState(false));
        dispatch(changeLoginState(true));
        sessionStorage.setItem("accesstoken", data.accessToken.jwtToken);
        sessionStorage.setItem("idtoken", data.idToken.jwtToken);
        sessionStorage.setItem("role", data.idToken.payload["custom:role"]);
        if (data && data["idToken"]["payload"]["custom:role"] == "agent") {
          history.push("/edit-agent/" + data["idToken"]["payload"]["email"]);
        } else {
          history.push("/admin/dash");
        }
      })
      .catch((err) => {
        console.log("error", err);
        setErrorToShow(err["message"]);
        dispatch(changeLoadingState(false));
        dispatch(changeLoginState(false));
      });
  };

  return (
    <>
      <form>
        <div className="col-md-6 container">
          <h3 className="text-center">Log in</h3>
          <div className="form-group">
            <label>Email</label>
            <input
              type="email"
              className="form-control"
              onChange={handleEmail}
              placeholder="Enter the email address"
            />
          </div>
          <div className="form-group">
            <label>Password</label>
            <input
              type="password"
              className="form-control"
              placeholder="Enter the password"
              onChange={handlePassword}
            />
          </div>
          <button
            type="button"
            className="btn btn-primary btn-block"
            onClick={handleClick}
          >
            Login
          </button>
          {errorToShow !== "" ? (
            <Alert className="mt-2" variant="danger">
              {errorToShow}
            </Alert>
          ) : (
            <></>
          )}
          {/* <p className="mt-2 forgot-password d-flex justify-content-end">
            Already registered?
            <Link className="nav-link pt-0 pr-0" to="/">
              Login here
            </Link>
          </p> */}
        </div>
      </form>
    </>
  );
}

export default Login;
