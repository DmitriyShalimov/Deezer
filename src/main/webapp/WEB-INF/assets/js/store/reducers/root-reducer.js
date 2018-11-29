import {ERROR} from "../actions/actionTypes.js";

const initialState = {user: {}, errorMessage:''};

export const rootReducer = (state = initialState, action) => {
    console.log(action);
    const {payload} = action;
    switch (action.type) {
        case ERROR:
            return{...state, errorMessage: payload};
        default:
            return state;
    }
};