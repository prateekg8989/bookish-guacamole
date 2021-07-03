import "./AgentList.css";
import React, { useEffect, useState } from "react";
import { faTrash, faPencilAlt } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useDispatch, useSelector } from "react-redux";
import { Button, Spinner } from "react-bootstrap";
import { useHistory } from "react-router-dom";
import { Grid, GridColumn } from "@progress/kendo-react-grid";
import {
  getAllAgents,
  loadAgentsFromHR,
  deleteAgent,
  fetchLicenseInformation,
} from "../../reducers/AgentReducer";
const DetailComponent = (props) => {
  const dataItem = props.dataItem;
  return (
    <div className="col-md-12 d-flex p-0 m-0">
      <div className="col-md-6 pl-0">
        <section>
          <p>
            <strong>Date of birth:- </strong>{" "}
            {dataItem.dob
              ? new Date(dataItem.dob).toDateString().slice(4)
              : null}
          </p>
          <p>
            <strong>Address:-</strong> {dataItem.address}
          </p>
        </section>
      </div>
      <div className="col-md-6 pl-0">
        <section>
          <p>
            <strong>License URN:-</strong> {dataItem.licenseUrnNo}
          </p>
          <p>
            <strong>License Expiry Date:-</strong>{" "}
            {dataItem.licenseExpiryDate
              ? new Date(dataItem.licenseExpiryDate).toDateString().slice(4)
              : null}
          </p>
          <p>
            <strong>License Issue Date:-</strong>{" "}
            {dataItem.licenseExpiryDate
              ? new Date(dataItem.licenseIssueDate).toDateString().slice(4)
              : null}
          </p>
        </section>
      </div>
    </div>
  );
};
function AgentList() {
  const history = useHistory();
  const dispatch = useDispatch();
  const { agentList, loading } = useSelector((state) => state.agent);
  const [data, setData] = useState([]);

  useEffect(() => {
    if (agentList && agentList.length > 0) {
      setData(agentList);
    }
  }, [agentList]);

  useEffect(() => {
    dispatch(getAllAgents());
  }, []);

  const handleLoadDataFromHr = () => {
    console.log("handleLoadDataFromHr clicked");
    dispatch(loadAgentsFromHR());
  };
  const updateLicenseInformation = () => {
    console.log("updateLicenseInformation clicked");
    dispatch(fetchLicenseInformation());
  };
  const deleteAgentButtonHandler = (email) => {
    console.log("delete button clicked for email:- " + email);
    dispatch(deleteAgent(email));
  };
  const editAgentButtonHandler = (email) => {
    console.log("edit button clicked for email:- " + email);
    history.push(`/edit-agent/${email}`);
  };
  const expandChange = (event) => {
    let newData = data.map((item) => {
      let expanded = item.expanded;
      if (item.email === event.dataItem.email) {
        expanded = !event.dataItem.expanded;
      }
      return { ...item, expanded };
    });
    setData(newData);
  };
  const handleAddNewAgentClick = () => {
    history.push("/admin/add-agent");
  };
  return (
    <>
      {!loading ? (
        <div className="p-3">
          <div
            className="col-md-12 p-0 m-0 mb-2 d-flex"
            style={{ justifyContent: "space-between" }}
          >
            <div>
              <h4 className="m-0">Agent List</h4>
            </div>
            <div>
              <Button
                variant="primary"
                className="mr-2"
                size="sm"
                type="button"
                onClick={handleLoadDataFromHr}
              >
                Get Agents from HR
              </Button>
              <Button
                variant="primary"
                className="mr-2"
                size="sm"
                type="button"
                onClick={updateLicenseInformation}
              >
                Update License Information
              </Button>
              <Button
                variant="primary"
                size="sm"
                type="button"
                onClick={handleAddNewAgentClick}
              >
                Add new Agent
              </Button>
            </div>
          </div>
          <Grid
            detail={DetailComponent}
            data={data}
            style={{
              height: "500px",
              width: "100%",
            }}
            reorderable={true}
            resizable={true}
            rowHeight={40}
            expandField="expanded"
            onExpandChange={expandChange}
          >
            <GridColumn
              field="name"
              title="Name"
              headerClassName="headerClassName1"
              width={175}
            />
            <GridColumn
              field="email"
              title="Email"
              headerClassName="headerClassName1"
              width={250}
            />
            <GridColumn
              field="crmId"
              title="CRM ID"
              headerClassName="headerClassName1"
            />
            <GridColumn
              field="teleCallingId"
              title="Tele calling Id"
              headerClassName="headerClassName1"
            />
            <GridColumn
              field="licenseIssueDate"
              title="License Status"
              cell={(props) => {
                let todaysDate = new Date().valueOf();
                let lExpiryDate = props.dataItem.licenseExpiryDate
                  ? props.dataItem.licenseExpiryDate
                  : null;
                return (
                  <td>
                    {lExpiryDate ? (
                      lExpiryDate < todaysDate ? (
                        <div>Ended</div>
                      ) : (
                        <div>Active</div>
                      )
                    ) : (
                      <div>N/A</div>
                    )}
                  </td>
                );
              }}
              headerClassName="headerClassName1"
            />
            <GridColumn
              title="Actions"
              cell={(props) => (
                <td>
                  {" "}
                  <FontAwesomeIcon
                    className="cursor-pointer mr-3"
                    icon={faPencilAlt}
                    onClick={() => editAgentButtonHandler(props.dataItem.email)}
                  />
                  {"   "}
                  <FontAwesomeIcon
                    className="cursor-pointer"
                    icon={faTrash}
                    onClick={() =>
                      deleteAgentButtonHandler(props.dataItem.email)
                    }
                  />
                </td>
              )}
              headerClassName="headerClassName1"
            />
          </Grid>
        </div>
      ) : (
        <div className="spinnerContainer">
          <Spinner animation="border" role="status">
            <span className="sr-only">Loading...</span>
          </Spinner>
        </div>
      )}
    </>
  );
}

export default AgentList;
