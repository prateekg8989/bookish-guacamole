import "./EditAgent.css";
import React, { useEffect, useState } from "react";
import { faTrash, faPencilAlt } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useDispatch, useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import { Form, Row, Col, Button, InputGroup, Image } from "react-bootstrap";
import { getAgentByEmail, editAgent } from "../../reducers/AgentReducer";
function EditAgent() {
  let { email } = useParams();
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(getAgentByEmail(email));
  }, [email]);
  const { agentByEmail, loading } = useSelector((state) => state.agent);
  const [userId, setUserId] = useState("");
  const [email1, setEmail1] = useState("");
  const [name, setName] = useState("");
  const [address, setAddress] = useState("");
  const [dob, setDob] = useState(null);
  const [crmId, setCrmId] = useState("");
  const [teleCallingId, setTeleCallingId] = useState("");
  const [licenseUrnNo, setLicenseUrnNo] = useState("");
  const [licenseIssueDate, setLicenseIssueDate] = useState(null);
  const [licenseExpiryDate, setLicenseExpiryDate] = useState(null);

  const [isEditable, setisEditable] = useState(false);
  useEffect(() => {
    if (!loading && agentByEmail && Object.keys(agentByEmail).length > 0) {
      setUserId(agentByEmail.userId);
      setEmail1(agentByEmail.email);
      setName(agentByEmail.name);
      setAddress(agentByEmail.address);
      console.log(
        "dob:- " + agentByEmail.dob + " === " + agentByEmail.dob
          ? new Date(agentByEmail.dob)
          : agentByEmail.dob
      );
      setDob(agentByEmail.dob ? new Date(agentByEmail.dob) : agentByEmail.dob);
      setCrmId(agentByEmail.crmId);
      setTeleCallingId(agentByEmail.teleCallingId);
      setLicenseUrnNo(agentByEmail.licenseUrnNo);
      setLicenseIssueDate(
        agentByEmail.licenseIssueDate
          ? new Date(agentByEmail.licenseIssueDate)
          : agentByEmail.licenseIssueDate
      );
      setLicenseExpiryDate(
        agentByEmail.licenseExpiryDate
          ? new Date(agentByEmail.licenseExpiryDate)
          : agentByEmail.licenseExpiryDate
      );
    }
  }, [loading]);

  const handleEditSubmitClicked = () => {
    if (agentByEmail && agentByEmail["email"] && email1) {
      let obj = {
        email: email1,
        name,
        address,
        dob: dob ? dob.valueOf() : null,
        crmId,
        teleCallingId,
        licenseUrnNo,
        licenseIssueDate: licenseIssueDate ? licenseIssueDate.valueOf() : null,
        licenseExpiryDate: licenseExpiryDate
          ? licenseExpiryDate.valueOf()
          : null,
      };
      console.log(obj);
      dispatch(editAgent(obj));
    }
  };

  return (
    <div className="p-3">
      <div className="d-flex justify-content-end pr-4 mb-3">
        <FontAwesomeIcon
          className="cursor-pointer"
          icon={faPencilAlt}
          onClick={() => setisEditable(true)}
        />
      </div>
      <Form>
        <Form.Group as={Row} controlId="exampleForm.ControlInput1">
          <Form.Label column sm={2}>
            Email
          </Form.Label>
          <Col sm={10} className="pr-5">
            <Form.Control
              required
              disabled={true}
              type="text"
              value={email1}
              placeholder="Enter the email"
            />
          </Col>
        </Form.Group>
        <Form.Group as={Row} controlId="exampleForm.ControlInput2">
          <Form.Label column sm={2}>
            Name
          </Form.Label>
          <Col sm={10} className="pr-5">
            <Form.Control
              disabled={!isEditable}
              required
              type="text"
              value={name}
              onChange={(event) => setName(event.target.value)}
              placeholder="Enter the name"
            />
          </Col>
        </Form.Group>
        <Form.Group as={Row} controlId="exampleForm.ControlInput3">
          <Form.Label column sm={2}>
            DOB
          </Form.Label>
          <Col sm={10} className="pr-5">
            <Form.Control
              disabled={!isEditable}
              required
              value={dob == null ? null : dob.toISOString().split("T")[0]}
              onChange={(event) => setDob(new Date(event.target.value))}
              type="date"
            />
          </Col>
        </Form.Group>
        <Form.Group as={Row} controlId="exampleForm.ControlInput4">
          <Form.Label column sm={2}>
            CRM Id
          </Form.Label>
          <Col sm={10} className="pr-5">
            <Form.Control
              disabled={!isEditable}
              value={crmId}
              onChange={(event) => setCrmId(event.target.value)}
              required
              type="text"
              placeholder="Enter the CRM-ID"
            />
          </Col>
        </Form.Group>
        <Form.Group as={Row} controlId="exampleForm.ControlInput5">
          <Form.Label column sm={2}>
            Telecalling ID
          </Form.Label>
          <Col sm={10} className="pr-5">
            <Form.Control
              disabled={!isEditable}
              value={teleCallingId}
              onChange={(event) => setTeleCallingId(event.target.value)}
              required
              type="text"
              placeholder="Enter the Telecalling-ID"
            />
          </Col>
        </Form.Group>
        <Form.Group as={Row} controlId="exampleForm.ControlInput6">
          <Form.Label column sm={2}>
            Address
          </Form.Label>
          <Col sm={10} className="pr-5">
            <Form.Control
              disabled={!isEditable}
              value={address}
              onChange={(event) => setAddress(event.target.value)}
              required
              type="text"
              placeholder="Enter the address"
            />
          </Col>
        </Form.Group>
        <Form.Group as={Row} controlId="exampleForm.ControlInput7">
          <Form.Label column sm={2}>
            License URN no.
          </Form.Label>
          <Col sm={10} className="pr-5">
            <Form.Control
              disabled={!isEditable}
              value={licenseUrnNo}
              onChange={(event) => setLicenseUrnNo(event.target.value)}
              required
              type="text"
              placeholder="Enter the license URN no"
            />
          </Col>
        </Form.Group>
        <Form.Group as={Row} controlId="exampleForm.ControlInput8">
          <Form.Label column sm={2}>
            License Issue Date
          </Form.Label>
          <Col sm={10} className="pr-5">
            <Form.Control
              disabled={!isEditable}
              value={
                licenseIssueDate == null
                  ? null
                  : licenseIssueDate.toISOString().split("T")[0]
              }
              onChange={(event) =>
                setLicenseIssueDate(new Date(event.target.value))
              }
              required
              type="date"
              placeholder="Enter the license issue date"
            />
          </Col>
        </Form.Group>
        <Form.Group as={Row} controlId="exampleForm.ControlInput9">
          <Form.Label column sm={2}>
            License Expiry Date
          </Form.Label>
          <Col sm={10} className="pr-5">
            <Form.Control
              disabled={!isEditable}
              value={
                licenseExpiryDate == null
                  ? null
                  : licenseExpiryDate.toISOString().split("T")[0]
              }
              onChange={(event) =>
                setLicenseExpiryDate(new Date(event.target.value))
              }
              required
              type="date"
              placeholder="Enter the license expiry date"
            />
          </Col>
        </Form.Group>

        <div className="w-100 textEnd pr-4">
          <Button disabled={!isEditable} variant="primary" type="button" onClick={handleEditSubmitClicked}>
            Submit
          </Button>
        </div>
      </Form>
    </div>
  );
}

export default EditAgent;
