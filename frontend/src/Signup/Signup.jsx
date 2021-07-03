import React, { useState } from "react";
import UserPool from "../UserPool";
import { Link } from "react-router-dom";
import { useDispatch } from "react-redux";
import {
  changeLoadingState
} from "../reducers/CommonReducer";
function SignUp() {
  const dispatch = useDispatch();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const handleEmail = (event) => setEmail(event.target.value);
  const handlePassword = (event) => setPassword(event.target.value);

  const handleClick = () => {
    dispatch(changeLoadingState(true));
    UserPool.signUp(email, password, [{ Name: 'email', Value: email }, { Name: 'custom:role', Value: 'agent' }], null, (err, data) => {
      if (err) {
        dispatch(changeLoadingState(false));
        console.log(err);
      }
      dispatch(changeLoadingState(false));
      console.log(data);
    });
    // user.confirmRegistration() for code verification see login component code as well
  };

  return (
    <>
      <form>
        <div className="col-md-6 container">
          <h3 className="text-center">Register</h3>
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
            Register
          </button>

          <p className="mt-2 forgot-password d-flex justify-content-end">
            Already registered?
            <Link className="nav-link pt-0 pr-0" to="/">
              Login here
            </Link>
            {/* <a href="#">Login here</a> */}
          </p>
        </div>
      </form>
    </>
  );
}

export default SignUp;
