import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";
export const getAllAgents = createAsyncThunk("agents/getAllAgents", async () => {
    try {
        const response = await axios.get(`https://gjkl2w4at2.execute-api.us-east-2.amazonaws.com/dev/agents`, {
            headers: {
                'Authorization': sessionStorage.getItem('idtoken')
            }
        });
        console.log('get all agent api response:- ', response)
        let obj = {
            status: 200, list: response.data
        }
        return obj;
    } catch (err) {
        console.log(err.response.data);
        let obj = {
            status: 500, ...err.response.data
        }
        return obj;
    }
});

export const getAgentByEmail = createAsyncThunk("agents/getAgentByEmail", async (email) => {
    try {
        const response = await axios.get(`https://gjkl2w4at2.execute-api.us-east-2.amazonaws.com/dev/agent?email=${email}`, {
            headers: {
                'Authorization': sessionStorage.getItem('idtoken')
            }
        });
        console.log('getAgentByEmail api response:- ', response)
        let obj = {
            status: 200, data: response.data
        }
        return obj;
    } catch (err) {
        console.log(err.response.data);
        let obj = {
            status: 500, ...err.response.data
        }
        return obj;
    }
});

export const editAgent = createAsyncThunk("agents/editAgent", async (data) => {
    try {
        const response = await axios.put(`https://gjkl2w4at2.execute-api.us-east-2.amazonaws.com/dev/agent`, data, { headers: { 'Content-Type': 'application/json', 'Authorization': sessionStorage.getItem('idtoken') } });
        console.log('editAgent api response:- ', response)
        let obj = {
            status: 200, data: response.data
        }
        return obj;
    } catch (err) {
        console.log(err.response.data);
        let obj = {
            status: 500, ...err.response.data
        }
        return obj;
    }
});

export const addNewAgent = createAsyncThunk("agents/addNewAgent", async (data) => {
    try {
        const response = await axios.post(`https://gjkl2w4at2.execute-api.us-east-2.amazonaws.com/dev/agent`, data, { headers: { 'Content-Type': 'application/json', 'Authorization': sessionStorage.getItem('idtoken') } });
        console.log('addNewAgent api response:- ', response)
        let obj = {
            status: 200, data: response.data
        }
        return obj;
    } catch (err) {
        console.log(err.response.data);
        let obj = {
            status: 500, ...err.response.data
        }
        return obj;
    }
});



export const loadAgentsFromHR = createAsyncThunk("agents/loadAgentsFromHR", async () => {
    try {
        const response = await axios.get(`https://gjkl2w4at2.execute-api.us-east-2.amazonaws.com/dev/agents/fetchfromhrms`, {
            headers: {
                'Authorization': sessionStorage.getItem('idtoken')
            }
        });
        console.log('loadAgentsFromHR api response:- ', response)
        let obj = {
            status: 200, list: response.data
        }
        return obj;
    } catch (err) {
        console.log(err.response.data);
        let obj = {
            status: 500, ...err.response.data
        }
        return obj;
    }
});


export const fetchLicenseInformation = createAsyncThunk("agents/fetchLicenseInformation", async () => {
    try {
        const response = await axios.get(`https://gjkl2w4at2.execute-api.us-east-2.amazonaws.com/dev/agents/updatelicenseinformation`, {
            headers: {
                'Authorization': sessionStorage.getItem('idtoken')
            }
        });
        console.log('fetchLicenseInformation api response:- ', response)
        let obj = {
            status: 200, list: response.data
        }
        return obj;
    } catch (err) {
        console.log(err.response.data);
        let obj = {
            status: 500, ...err.response.data
        }
        return obj;
    }
});

export const deleteAgent = createAsyncThunk("agents/deleteAgent", async (email) => {
    try {
        const response = await axios.delete(`https://gjkl2w4at2.execute-api.us-east-2.amazonaws.com/dev/agent?email=${email}`, {
            headers: {
                'Authorization': sessionStorage.getItem('idtoken')
            }
        });
        console.log('deleteAgent api response:- ', response)
        let obj = {
            status: 200, list: response.data
        }
        return obj;
    } catch (err) {
        console.log(err.response.data);
        let obj = {
            status: 500, ...err.response.data
        }
        return obj;
    }
});

const agentSlice = createSlice({
    name: "agent",
    initialState: {
        loading: false,
        agentList: [],
        agentByEmail: {}
    },
    reducers: {
    },
    extraReducers: {
        [getAllAgents.pending]: (state, action) => {
            state.loading = true;
        },
        [getAllAgents.fulfilled]: (state, action) => {
            state.loading = false;
            if (action.payload.status == 200) {

                console.log(action.payload);
                state.agentList = [...action.payload.list];
            }

        },
        [getAllAgents.rejected]: (state, action) => {
            state.loading = false;
        },

        [getAgentByEmail.pending]: (state, action) => {
            state.loading = true;
        },
        [getAgentByEmail.fulfilled]: (state, action) => {
            state.loading = false;
            if (action.payload.status == 200) {
                state.agentByEmail = action.payload.data;
            }

        },
        [getAgentByEmail.rejected]: (state, action) => {
            state.loading = false;
        },

        [editAgent.pending]: (state, action) => {
            state.loading = true;
        },
        [editAgent.fulfilled]: (state, action) => {
            state.loading = false;
        },
        [editAgent.rejected]: (state, action) => {
            state.loading = false;
        },
        [addNewAgent.pending]: (state, action) => {
            state.loading = true;
        },
        [addNewAgent.fulfilled]: (state, action) => {
            state.loading = false;
        },
        [addNewAgent.rejected]: (state, action) => {
            state.loading = false;
        },

        [loadAgentsFromHR.pending]: (state, action) => {
            state.loading = true;
        },
        [loadAgentsFromHR.fulfilled]: (state, action) => {
            state.loading = false;
        },
        [loadAgentsFromHR.rejected]: (state, action) => {
            state.loading = false;
        },
        [fetchLicenseInformation.pending]: (state, action) => {
            state.loading = true;
        },
        [fetchLicenseInformation.fulfilled]: (state, action) => {
            state.loading = false;
        },
        [fetchLicenseInformation.rejected]: (state, action) => {
            state.loading = false;
        },

        [deleteAgent.pending]: (state, action) => {
            state.loading = true;
        },
        [deleteAgent.fulfilled]: (state, action) => {
            state.loading = false;
        },
        [deleteAgent.rejected]: (state, action) => {
            state.loading = false;
        },
    },
});

// export const { setUserDetailsFromSS, logoutTheUser } = agentSlice.actions;

export default agentSlice.reducer;
