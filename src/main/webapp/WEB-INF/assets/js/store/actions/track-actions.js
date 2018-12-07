import {SET_TRACK_INDEX, SET_TRACK, SET_PLAYING, SET_AUDIO, SET_TIME, SET_DURATION} from "./actionTypes.js";

export const setTrackIndex = (idx) => {
    return {
        type: SET_TRACK_INDEX,
        payload: idx
    }
};

export const setTrack = (track) => {
    return {
        type: SET_TRACK,
        payload: track
    }
};

export const setPlaying = (playing) => {
    return {
        type: SET_PLAYING,
        payload: playing
    }
};

export const setAudio = (audio) => {
    return {
        type: SET_AUDIO,
        payload: audio
    }
};

export const setTrackTime = (time) =>{
    return {
        type: SET_TIME,
        payload: time
    }
};

export const setTrackDuration = (duration) => {
    return{
        type: SET_DURATION,
        payload: duration
    }
};