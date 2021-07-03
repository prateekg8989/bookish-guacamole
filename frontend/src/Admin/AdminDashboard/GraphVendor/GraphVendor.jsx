import "./GraphVendor.css";
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

import { fetchDataByVendor } from "../../../reducers/DashboardReducer";
import { fetchInsurancePolicyTypes } from "../../../reducers/InsuranceTypeVendorReducer";
function GraphVendor() {
  const history = useHistory();
  const dispatch = useDispatch();
  const { loading, dataByVendor } = useSelector((state) => state.dashboard);
  const insuranceTypeVendor = useSelector((state) => state.insuranceTypeVendor);
  const [dataByVendor1, setDataByVendor1] = useState([]);
  const [insuranceTypeId, setInsuranceTypeId] = useState("");
  useEffect(() => {
    dispatch(fetchInsurancePolicyTypes());
  }, []);

  useEffect(() => {
    if (!loading && dataByVendor) {
      setDataByVendor1(dataByVendor);
    }
  }, [loading, dataByVendor]);

  const handleChangeInsuranceType = (id) => {
    if (id && id != "") {
      dispatch(fetchDataByVendor(id));
    }
  };

  return (
    <div className="col-md-12 p-0 pt-3">
      <Chart>
        <ChartTitle text="Count of insurance sold, distributed by vendors" />
        <ChartLegend position="bottom" />
        <ChartSeries>
          <ChartSeriesItem
            type="pie"
            data={dataByVendor1}
            field="totalCount"
            tooltip={{
              visible: true
            }}
            categoryField="vendorName"
            labels={{
              visible: true,
              content: (props) => {
                return `${props.dataItem.vendorName} : ${props.dataItem.totalCount}`;
              },
            }}
          />
        </ChartSeries>
      </Chart>
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
              <option value={null}>Please select insurance type</option>
              {insuranceTypeVendor &&
                insuranceTypeVendor.insuranceType &&
                insuranceTypeVendor.insuranceType.map((item) => (
                  <option
                    value={item.insuranceTypeId}
                    key={item.insuranceTypeId}
                  >
                    {item.insuranceTypeName}
                  </option>
                ))}
            </Form.Control>
          </Col>
        </Form.Group>
      </Form>
    </div>
  );
}

export default GraphVendor;
