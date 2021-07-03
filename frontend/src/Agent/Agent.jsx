import "./Admin.css";
import { Switch, Route } from "react-router-dom";
import Login from "../Login/Login";
import AdminSidebar from "./AdminSidebar/AdminSidebar";
import AgentList from "./AgentList/AgentList";
import { useSelector } from "react-redux";
import { Spinner } from "react-bootstrap";
function Admin() {
  const { loading } = useSelector((state) => state.agent);
  return (
    <div>
      <div className="col-md-12 p-0">
        {/* <div className="d-flex">
          <div className="col-md-2 p-0">
            <AdminSidebar />
          </div>
          <hr className="verticalLine" />
          <div className="col-md-10 ml-auto">
            <Switch>
              <Route exact path="/user" component={AgentList} />
              <Route path="/user/signin" component={Login} />
            </Switch>
          </div>
        </div> */}
        <div>
          {/* {!loading ? (
            <Switch>
              <Route exact path="/user" component={AgentList} />
              <Route path="/user/signin" component={Login} />
            </Switch>
          ) : (
            <div className="spinnerContainer">
              <Spinner animation="border" role="status">
                <span className="sr-only">Loading...</span>
              </Spinner>
            </div>
          )} */}
          <Switch>
              <Route exact path="/user" component={AgentList} />
              <Route path="/user/signin" component={Login} />
            </Switch>
        </div>
      </div>
    </div>
  );
}

export default Admin;
