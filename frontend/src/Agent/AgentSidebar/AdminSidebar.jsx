import './AdminSidebar.css';


function AdminSidebar() {
  return (
    <ul className="nav flex-column">
      {/* <li className="nav-item p-2 sidebarTitle">
        <div className="nav-link p-0 sidebarTitle">Utitilities</div>
      </li> */}
      <li className="nav-item p-2">
        <div className="m-0 p-0 sidebarLink">
          <div className="nav-link p-0 sidebarNameStyle">
            View Agent List
          </div>
        </div>
      </li>
      <li className="nav-item p-2">
        <div className="m-0 p-0 sidebarLink">
          <div className="nav-link p-0 sidebarNameStyle">Add Agent</div>
        </div>
      </li>
    </ul>
  );
}

export default AdminSidebar;
