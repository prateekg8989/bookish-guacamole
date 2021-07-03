import './AdminSidebar.css';
import { useHistory } from "react-router-dom";

function AdminSidebar() {
  const history = useHistory();
  const handleClick = (url) => {
    if(url == "dash") {
      history.push("/admin/dash");
    }else if(url == "agents") {
      history.push("/admin/agents");
    }
  }
  return (
    <ul className="nav flex-column mt-3">
      {/* <li className="nav-item p-2 sidebarTitle">
        <div className="nav-link p-0 sidebarTitle">Utitilities</div>
      </li> */}
      <li className="nav-item p-2">
        <div className="m-0 p-0 sidebarLink">
          <div className="nav-link p-0 sidebarNameStyle" onClick={() => handleClick("dash")}>
            Dashboard
          </div>
        </div>
      </li>
      <li className="nav-item p-2">
        <div className="m-0 p-0 sidebarLink">
          <div className="nav-link p-0 sidebarNameStyle" onClick={() => handleClick("agents")}>Agents List</div>
        </div>
      </li>
    </ul>
  );
}

export default AdminSidebar;
