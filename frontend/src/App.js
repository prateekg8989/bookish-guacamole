import './App.css';
import '@progress/kendo-theme-default/dist/all.css';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import Login from './Login/Login';
import SignUp from './Signup/Signup';
import Navbar from './Navbar/Navbar';
import EditAgent from './Agent/EditAgent/EditAgent';
// import AddSoldPolicy from './Agent/AddSoldPolicy/AddSoldPolicy';
import { useSelector } from "react-redux";
import Admin from './Admin/Admin';
import { Account } from './Account';
import { Spinner } from "react-bootstrap";

function App() {
  const { loading } = useSelector(state => state.common);
  const agentRedux = useSelector(state => state.agent);
  return (
    <Account>

      <div className="col-md-12 p-0 m-0">
        <Router>
          <Navbar />
          {
            !loading ? (<Switch>
              <Route exact path="/" component={Login} />
              <Route exact path="/sign-in" component={Login} />
              <Route exact path="/sign-up" component={SignUp} />
              <Route exact path="/edit-agent/:email" component={EditAgent} />
              {/* <Route exact path="/sold-policy" component={AddSoldPolicy} /> */}
              <Route path="/admin" component={Admin} />
            </Switch>) : (<div className="spinnerContainer">
              <Spinner animation="border" role="status">
                <span className="sr-only">Loading...</span>
              </Spinner>
            </div>)}
        </Router>
      </div>
    </Account>
  );
}

export default App;
