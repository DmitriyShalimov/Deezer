import {
    SET_TRACK_INDEX,
    SET_TRACK,
    SET_PLAYING,
    SET_AUDIO,
    SET_TIME,
    SET_DURATION
} from "../actions/actionTypes.js";

const initialState = {
    audio: null,
    trackIndex: 0,
    track: null,
    playing: false,
    currentTime: '0:00',
    duration: '0:00',
};

export const trackReducer = (state = initialState, action) => {
    const {payload} = action;
    switch (action.type) {
        case SET_AUDIO:
            return {...state, audio: payload};
        case SET_TRACK_INDEX:
            return {...state, trackIndex: payload};
        case SET_TRACK:
            return {...state, track: payload};
        case SET_PLAYING:
            return {...state, playing: payload};
        case SET_TIME:
            return {...state, currentTime: payload};
        case SET_DURATION:
            return {...state, duration: payload};
        default:
            return state;
    }
};