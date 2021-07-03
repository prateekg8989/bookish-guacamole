import "./AddAgent.css";
import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Form, Row, Col, Button, Spinner, Image } from "react-bootstrap";
import { useHistory } from "react-router-dom";
import { addNewAgent } from "../../reducers/AgentReducer";
function AddAgent() {
  const history = useHistory();
  const dispatch = useDispatch();
  const { loading } = useSelector((state) => state.agent);
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
  const [isSubmitButtonClicked, setIsSubmitButtonClicked] = useState(false);

  const handleEditSubmitClicked = () => {
    if (email1) {
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
      setIsSubmitButtonClicked(true);
      dispatch(addNewAgent(obj));
    }
  };

  // useEffect(() => {
  //   if (!loading && isSubmitButtonClicked) {
  //     history.push("");
  //   }
  // }, [loading]);

  return (
    <>
      {!loading ? (
        <div className="p-3">
          <h3>Add new Agent</h3>
          <Form>
            <Form.Group as={Row} controlId="exampleForm.ControlInput1">
              <Form.Label column sm={2}>
                Email
              </Form.Label>
              <Col sm={10} className="pr-5">
                <Form.Control
                  required
                  type="text"
                  value={email1}
                  onChange={(event) => setEmail1(event.target.value)}
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
              <Button
                variant="outline-danger"
                type="button"
                onClick={handleEditSubmitClicked}
              >
                Submit
              </Button>
            </div>
          </Form>
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

export default AddAgent;
