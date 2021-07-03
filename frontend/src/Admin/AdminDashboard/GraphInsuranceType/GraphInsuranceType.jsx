import "./GraphInsuranceType.css";
import React, { useEffect, useState } from "react";
import { faTrash, faPencilAlt } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useDispatch, useSelector } from "react-redux";
import { Button } from "react-bootstrap";
import { useHistory } from "react-router-dom";
import {
  Chart,
  ChartLegend,
  ChartSeries,
  ChartSeriesItem,
  ChartTitle,
} from "@progress/kendo-react-charts";
import "hammerjs";

import { fetchDataByInsuranceType } from "../../../reducers/DashboardReducer";
function GraphInsuranceType() {
  const history = useHistory();
  const dispatch = useDispatch();
  const { loading, dataByInsuranceType } = useSelector(
    (state) => state.dashboard
  );
  const [dataByInsuranceType1, setDataByInsuranceType1] = useState([]);
  useEffect(() => {
    dispatch(fetchDataByInsuranceType());
  }, []);

  useEffect(() => {
    if (!loading && dataByInsuranceType) {
      setDataByInsuranceType1(dataByInsuranceType);
    }
  }, [loading, dataByInsuranceType]);

  return (
    <div className="col-md-12 p-0 pt-3">
      <Chart>
        <ChartTitle text="Count of insurance sold, distributed by insurance type" />
        <ChartLegend position="bottom" />
        <ChartSeries>
          <ChartSeriesItem
            type="pie"
            data={dataByInsuranceType1}
            field="totalCount"
            categoryField="insuranceTypeName"
            labels={{
              visible: true,
              content: (props) => {
                return `${props.dataItem.insuranceTypeName} : ${props.dataItem.totalCount}`;
              },
            }}
          />
        </ChartSeries>
      </Chart>
    </div>
  );
}

export default GraphInsuranceType;
