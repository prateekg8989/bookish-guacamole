import { configureStore } from "@reduxjs/toolkit";
import commonReducer from './reducers/CommonReducer';
import agentReducer from './reducers/AgentReducer';
import dashboardReducer from './reducers/DashboardReducer';
import insuranceTypeVendorReducer from './reducers/InsuranceTypeVendorReducer';
export default configureStore({
  reducer: {
    common: commonReducer,
    agent: agentReducer,
    dashboard: dashboardReducer,
    insuranceTypeVendor: insuranceTypeVendorReducer
  },
});
