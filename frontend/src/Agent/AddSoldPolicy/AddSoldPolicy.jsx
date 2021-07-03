// import "./AddSoldPolicy.css";
// import React, { useEffect, useState } from "react";
// import { faTrash, faPencilAlt } from "@fortawesome/free-solid-svg-icons";
// import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
// import { useDispatch, useSelector } from "react-redux";
// import { useParams } from "react-router-dom";
// import { Form, Row, Col, Button, InputGroup, Image } from "react-bootstrap";
// import { getAgentByEmail } from "../../reducers/AgentReducer";
// function AddSoldPolicy() {
//   const dispatch = useDispatch();
//   useEffect(() => {
//     // dispatch(getAgentByEmail(email));
//   }, []);
//   const [insuranceTypeId, setInsuranceTypeId] =  useState("");
//   const [insuranceTypeName, setInsuranceTypeName] =  useState("");
//   const [vendorId, setVendorId] =  useState("");
//   const [vendorName, setVendorName] =  useState("");
//   const [agentId, setAgentId] =  useState("");
//   const [policyUin, setPolicyUin] =  useState("");
//   const [policyName, setPolicyName] =  useState("");
//   const [insuredValue, setInsuredValue] =  useState("");
//   const [insuranceTypeId, setInsuranceTypeId] =  useState(0);
//   const [totalAnnualPremium, setTotalAnnualPremium] =  useState(0);
//   const [policyIssueDate, setPolicyIssueDate] = useState(null);
//   const [policyEndDate, setPolicyEndDate] = useState(null);


//   const handleEditSubmitClicked = () => {
    
//   };

//   return (
//     <div className="p-3">

//       <Form>
//         <Form.Group as={Row} controlId="exampleForm.ControlInput1">
//           <Form.Label column sm={2}>
//             Email
//           </Form.Label>
//           <Col sm={10} className="pr-5">
//             <Form.Control
//               required
//               disabled={true}
//               type="text"
//               value={email1}
//               placeholder="Enter the email"
//             />
//           </Col>
//         </Form.Group>
//         <Form.Group as={Row} controlId="exampleForm.ControlInput2">
//           <Form.Label column sm={2}>
//             Name
//           </Form.Label>
//           <Col sm={10} className="pr-5">
//             <Form.Control
//               disabled={!isEditable}
//               required
//               type="text"
//               value={name}
//               onChange={(event) => setName(event.target.value)}
//               placeholder="Enter the name"
//             />
//           </Col>
//         </Form.Group>
//         <Form.Group as={Row} controlId="exampleForm.ControlInput3">
//           <Form.Label column sm={2}>
//             DOB
//           </Form.Label>
//           <Col sm={10} className="pr-5">
//             <Form.Control
//               disabled={!isEditable}
//               required
//               value={dob == null ? null : dob.toISOString().split("T")[0]}
//               onChange={(event) => setDob(new Date(event.target.value))}
//               type="date"
//             />
//           </Col>
//         </Form.Group>
//         <Form.Group as={Row} controlId="exampleForm.ControlInput4">
//           <Form.Label column sm={2}>
//             CRM Id
//           </Form.Label>
//           <Col sm={10} className="pr-5">
//             <Form.Control
//               disabled={!isEditable}
//               value={crmId}
//               onChange={(event) => setCrmId(event.target.value)}
//               required
//               type="text"
//               placeholder="Enter the CRM-ID"
//             />
//           </Col>
//         </Form.Group>
//         <Form.Group as={Row} controlId="exampleForm.ControlInput5">
//           <Form.Label column sm={2}>
//             Telecalling ID
//           </Form.Label>
//           <Col sm={10} className="pr-5">
//             <Form.Control
//               disabled={!isEditable}
//               value={teleCallingId}
//               onChange={(event) => setTeleCallingId(event.target.value)}
//               required
//               type="text"
//               placeholder="Enter the Telecalling-ID"
//             />
//           </Col>
//         </Form.Group>
//         <Form.Group as={Row} controlId="exampleForm.ControlInput6">
//           <Form.Label column sm={2}>
//             Address
//           </Form.Label>
//           <Col sm={10} className="pr-5">
//             <Form.Control
//               disabled={!isEditable}
//               value={address}
//               onChange={(event) => setAddress(event.target.value)}
//               required
//               type="text"
//               placeholder="Enter the address"
//             />
//           </Col>
//         </Form.Group>
//         <Form.Group as={Row} controlId="exampleForm.ControlInput7">
//           <Form.Label column sm={2}>
//             License URN no.
//           </Form.Label>
//           <Col sm={10} className="pr-5">
//             <Form.Control
//               disabled={!isEditable}
//               value={licenseUrnNo}
//               onChange={(event) => setLicenseUrnNo(event.target.value)}
//               required
//               type="text"
//               placeholder="Enter the license URN no"
//             />
//           </Col>
//         </Form.Group>
//         <Form.Group as={Row} controlId="exampleForm.ControlInput8">
//           <Form.Label column sm={2}>
//             License Issue Date
//           </Form.Label>
//           <Col sm={10} className="pr-5">
//             <Form.Control
//               disabled={!isEditable}
//               value={
//                 licenseIssueDate == null
//                   ? null
//                   : licenseIssueDate.toISOString().split("T")[0]
//               }
//               onChange={(event) =>
//                 setLicenseIssueDate(new Date(event.target.value))
//               }
//               required
//               type="date"
//               placeholder="Enter the license issue date"
//             />
//           </Col>
//         </Form.Group>
//         <Form.Group as={Row} controlId="exampleForm.ControlInput9">
//           <Form.Label column sm={2}>
//             License Expiry Date
//           </Form.Label>
//           <Col sm={10} className="pr-5">
//             <Form.Control
//               disabled={!isEditable}
//               value={
//                 licenseExpiryDate == null
//                   ? null
//                   : licenseExpiryDate.toISOString().split("T")[0]
//               }
//               onChange={(event) =>
//                 setLicenseExpiryDate(new Date(event.target.value))
//               }
//               required
//               type="date"
//               placeholder="Enter the license expiry date"
//             />
//           </Col>
//         </Form.Group>

//         <div className="w-100 textEnd pr-4">
//           <Button disabled={!isEditable} variant="primary" type="button" onClick={handleEditSubmitClicked}>
//             Submit
//           </Button>
//         </div>
//       </Form>
//     </div>
//   );
// }

// export default AddSoldPolicy;
