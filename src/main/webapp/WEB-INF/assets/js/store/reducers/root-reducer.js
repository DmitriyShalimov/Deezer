import {ERROR, FILL_SEARCH_OPTIONS, SET_IS_AUTHENTICATED, SET_CURRENT_PLAYLIST} from "../actions/actionTypes.js";

const initialState = {user: {}, errorMessage: '', searchOptions: [], isAuth: '', currentPlaylist:[]};

export const rootReducer = (state = initialState, action) => {
    console.log(action);
    const {payload} = action;
    switch (action.type) {
        case SET_IS_AUTHENTICATED:
            return {...state, isAuth: payload};
        case FILL_SEARCH_OPTIONS:
            return {...state, searchOptions: payload};
        case SET_CURRENT_PLAYLIST:
            return{...state, currentPlaylist: payload};
        case ERROR:
            return {...state, errorMessage: payload};
        default:
            return state;
    }
};