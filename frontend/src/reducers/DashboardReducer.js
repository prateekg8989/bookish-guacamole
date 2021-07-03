import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";

export const fetchDataByInsuranceType = createAsyncThunk("dashboard/fetchDataByInsuranceType", async () => {
    try {
        const response = await axios.get(`https://gjkl2w4at2.execute-api.us-east-2.amazonaws.com/dev/stats/insurance-type`, {
            headers: {
                'Authorization': sessionStorage.getItem('idtoken')
            }
        });
        if (response.status > 299) {
            console.log('fetchDataByInsuranceType api response:- ', response)
            let obj = {
                status: 500, data: response.data
            }
            return obj;
        } else {
            console.log('fetchDataByInsuranceType api response:- ', response)
            let obj = {
                status: 200, data: response.data
            }
            return obj;
        }


    } catch (err) {
        console.log(err.response.data);
        let obj = {
            status: 500, ...err.response.data
        }
        return obj;
    }
});

export const fetchDataByVendor = createAsyncThunk("dashboard/fetchDataByVendor", async (insuranceTypeId) => {
    try {
        const response = await axios.get(`https://gjkl2w4at2.execute-api.us-east-2.amazonaws.com/dev/stats/vendor?insuranceTypeId=${insuranceTypeId}`, {
            headers: {
                'Authorization': sessionStorage.getItem('idtoken')
            }
        });
        if (response.status > 299) {
            console.log('fetchDataByVendor api response:- ', response)
            let obj = {
                status: 500, data: response.data
            }
            return obj;
        } else {
            console.log('fetchDataByVendor api response:- ', response)
            let obj = {
                status: 200, data: response.data
            }
            return obj;
        }


    } catch (err) {
        console.log(err.response.data);
        let obj = {
            status: 500, ...err.response.data
        }
        return obj;
    }
});


export const fetchDataForAgents = createAsyncThunk("dashboard/fetchDataForAgents", async (obj) => {
    try {
        let URL = "https://gjkl2w4at2.execute-api.us-east-2.amazonaws.com/dev/stats/agent";
        if (obj && obj['insuranceTypeId']) {
            URL = URL + "?insuranceTypeId=" + obj['insuranceTypeId'];
        }
        if (obj && obj['vendorId']) {
            URL = URL + "&vendorId=" + obj['vendorId'];
        }
        const response = await axios.get(URL, {
            headers: {
                'Authorization': sessionStorage.getItem('idtoken')
            }
        });
        if (response.status > 299) {
            console.log('fetchDataForAgents api response:- ', response)
            let obj = {
                status: 500, data: response.data
            }
            return obj;
        } else {
            console.log('fetchDataForAgents api response:- ', response)
            let obj = {
                status: 200, data: response.data
            }
            return obj;
        }


    } catch (err) {
        console.log(err.response.data);
        let obj = {
            status: 500, ...err.response.data
        }
        return obj;
    }
});



const dashboardSlice = createSlice({
    name: "dashboard",
    initialState: {
        loading: false,
        dataByInsuranceType: [],
        dataByVendor: [],
        dataByAgent: [],
    },
    reducers: {
    },
    extraReducers: {
        [fetchDataByInsuranceType.pending]: (state, action) => {
            state.loading = true;
        },
        [fetchDataByInsuranceType.fulfilled]: (state, action) => {
            state.loading = false;
            if (action.payload.status == 200) {
                console.log(action.payload);
                state.dataByInsuranceType = [...action.payload.data];
            }

        },
        [fetchDataByInsuranceType.rejected]: (state, action) => {
            state.loading = false;
        },

        [fetchDataByVendor.pending]: (state, action) => {
            state.loading = true;
        },
        [fetchDataByVendor.fulfilled]: (state, action) => {
            state.loading = false;
            if (action.payload.status == 200) {
                console.log(action.payload);
                state.dataByVendor = [...action.payload.data];
            }

        },
        [fetchDataByVendor.rejected]: (state, action) => {
            state.loading = false;
        },

        [fetchDataForAgents.pending]: (state, action) => {
            state.loading = true;
        },
        [fetchDataForAgents.fulfilled]: (state, action) => {
            state.loading = false;
            if (action.payload.status == 200) {
                console.log(action.payload);
                state.dataByAgent = [...action.payload.data];
            }

        },
        [fetchDataForAgents.rejected]: (state, action) => {
            state.loading = false;
        },
    },
});

// export const { setUserDetailsFromSS, logoutTheUser } = dashboardSlice.actions;

export default dashboardSlice.reducer;
