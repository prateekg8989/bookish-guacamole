import "./GraphAgent.css";
import React, { useEffect, useState } from "react";
import { faTrash, faPencilAlt } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useDispatch, useSelector } from "react-redux";
import { Form, Row, Col, Button, InputGroup } from "react-bootstrap";
import { useHistory } from "react-router-dom";
import {
  Chart,
  ChartLegend,
  ChartSeries,
  ChartSeriesItem,
  ChartTitle,
} from "@progress/kendo-react-charts";
import "hammerjs";

import { fetchDataForAgents } from "../../../reducers/DashboardReducer";
import { fetchInsurancePolicyTypes } from "../../../reducers/InsuranceTypeVendorReducer";
function GraphAgent() {
  const history = useHistory();
  const dispatch = useDispatch();
  const { loading, dataByAgent } = useSelector((state) => state.dashboard);
  const insuranceTypeVendor = useSelector((state) => state.insuranceTypeVendor);
  const [dataByAgent1, setDataByAgent1] = useState([]);
  const [insuranceType, setInsuranceType] = useState({});
  const [vendorId, setVendorId] = useState("");
  useEffect(() => {
    dispatch(fetchInsurancePolicyTypes());
  }, []);

  useEffect(() => {
    if (!loading && dataByAgent) {
      setDataByAgent1(dataByAgent);
    }
  }, [loading, dataByAgent]);

  const handleChangeInsuranceType = (data) => {
    if (data == "null") {
      dispatch(fetchDataForAgents(null));
    } else {
      let insuranceTypeObj = JSON.parse(data);
      if (insuranceTypeObj && insuranceTypeObj.insuranceTypeId != "") {
        let obj = { insuranceTypeId: insuranceTypeObj.insuranceTypeId };
        setInsuranceType(insuranceTypeObj);
        dispatch(fetchDataForAgents(obj));
      }
    }
  };

  const handleChangeVendor = (id) => {
    if (id && id != "") {
      let obj = {
        insuranceTypeId: insuranceType.insuranceTypeId,
        vendorId: id,
      };
      setVendorId(id);
      dispatch(fetchDataForAgents(obj));
    }
  };

  return (
    <div className="col-md-12 d-flex m-0 p-0">
      <div className="col-md-6">
        <Form>
          <Form.Group as={Row} controlId="exampleForm.ControlSelect1">
            <Form.Label column sm={2}>
              Insurance Type
            </Form.Label>
            <Col sm={10} className="pr-5">
              <Form.Control
                required
                as="select"
                onChange={(event) =>
                  handleChangeInsuranceType(event.target.value)
                }
              >
                <option value={"null"}>Please select insurance type</option>
                {insuranceTypeVendor &&
                  insuranceTypeVendor.insuranceType &&
                  insuranceTypeVendor.insuranceType.map((item) => (
                    <option
                      value={JSON.stringify(item)}
                      key={item.insuranceTypeId}
                    >
                      {item.insuranceTypeName}
                    </option>
                  ))}
              </Form.Control>
            </Col>
          </Form.Group>
          <Form.Group as={Row} controlId="exampleForm.ControlSelect2">
            <Form.Label column sm={2}>
              Vendor
            </Form.Label>
            <Col sm={10} className="pr-5">
              <Form.Control
                required
                as="select"
                onChange={(event) => handleChangeVendor(event.target.value)}
              >
                <option value={null}>Please select insurance type</option>
                {insuranceType &&
                  insuranceType.listOfVendors &&
                  insuranceType.listOfVendors.map((item) => (
                    <option value={item.vendorId} key={item.vendorId}>
                      {item.vendorName}
                    </option>
                  ))}
              </Form.Control>
            </Col>
          </Form.Group>
        </Form>
      </div>
      <div className="col-md-6">
        <Chart>
          <ChartTitle text="Count of insurance sold, distributed by agents " />
          <ChartLegend position="bottom" />
          <ChartSeries>
            <ChartSeriesItem
              type="pie"
              data={dataByAgent1}
              field="totalCount"
              categoryField="agentName"
              labels={{
                visible: true,
                content: (props) => {
                  return `Count : ${props.dataItem.totalCount}`;
                },
              }}
            />
          </ChartSeries>
        </Chart>
      </div>
    </div>
  );
}

export default GraphAgent;
