import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";

export const fetchInsurancePolicyTypes = createAsyncThunk("insuranceTypeVendor/fetchInsurancePolicyTypes", async () => {
    try {
        const response = await axios.get(`https://gjkl2w4at2.execute-api.us-east-2.amazonaws.com/dev/policytypes`);
        if (response.status > 299) {
            console.log('fetchInsurancePolicyTypes api response:- ', response)
            let obj = {
                status: 500, data: response.data
            }
            return obj;
        } else {
            console.log('fetchInsurancePolicyTypes api response:- ', response)
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




const insuranceTypeVendorSlice = createSlice({
    name: "insuranceTypeVendor",
    initialState: {
        loading: false,
        insuranceType: [],
    },
    reducers: {
    },
    extraReducers: {
        [fetchInsurancePolicyTypes.pending]: (state, action) => {
            state.loading = true;
        },
        [fetchInsurancePolicyTypes.fulfilled]: (state, action) => {
            state.loading = false;
            if (action.payload.status == 200) {
                console.log(action.payload);
                state.insuranceType = [...action.payload.data];
            }

        },
        [fetchInsurancePolicyTypes.rejected]: (state, action) => {
            state.loading = false;
        },
    },
});

// export const { setUserDetailsFromSS, logoutTheUser } = insuranceTypeVendorSlice.actions;

export default insuranceTypeVendorSlice.reducer;
