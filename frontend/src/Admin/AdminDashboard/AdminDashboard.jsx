import "./AdminDashboard.css";
import React, { useEffect, useState } from "react";
import { faTrash, faPencilAlt } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useDispatch, useSelector } from "react-redux";

import GraphInsuranceType from "./GraphInsuranceType/GraphInsuranceType";
import GraphVendor from "./GraphVendor/GraphVendor";
import GraphAgent from "./GraphAgent/GraphAgent";

function AdminDashboard() {
  return (
    <div className="p-0 m-0">
      <div className="col-md-12 d-flex p-0 m-0 pt-3">
        <div className="col-md-6">
          <GraphInsuranceType />
        </div>
        <div className="col-md-6">
          <GraphVendor />
        </div>
      </div>
      <hr />
      <div className="col-md-12 p-0">
        <GraphAgent />
      </div>
    </div>
  );
}

export default AdminDashboard;
