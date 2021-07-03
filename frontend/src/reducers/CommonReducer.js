// import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { createSlice } from "@reduxjs/toolkit";
// import axios from "axios";

const commonSlice = createSlice({
    name: "common",
    initialState: {
        loading: false,
        loginState: false
    },
    reducers: {
        changeLoadingState(state, action) {
            state.loading = action.payload
        },
        changeLoginState(state, action) {
            state.loginState = action.payload
        }

    },
    extraReducers: {
    },
});

export const { changeLoadingState, changeLoginState } = commonSlice.actions;

export default commonSlice.reducer;
