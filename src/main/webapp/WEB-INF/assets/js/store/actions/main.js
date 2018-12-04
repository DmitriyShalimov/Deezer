import {FILL_SEARCH_OPTIONS, SET_CURRENT_PLAYLIST} from './actionTypes.js'
import {push} from 'react-router-redux';

const URL_PREFIX = '/api/v1/';
const USER_TOKEN_HEADER = 'User-Token';


const setCurrentPlaylist = songs =>{
    return{
        type: SET_CURRENT_PLAYLIST,
        payload: songs
    }
};

const fillSearch = searchOptions => {
    return {
        type: FILL_SEARCH_OPTIONS,
        payload: searchOptions
    }
};

export const getSearchOptions = () => {
    return dispatch => {
        const token = localStorage.getItem('user-token');
        console.log(token);
        return fetch(`${URL_PREFIX}search`, {headers: {[USER_TOKEN_HEADER]: token}})
            .then(res => {
                return res.json();
            })
            .then(res => dispatch(fillSearch(res)))
    };
};

export const redirect = redirectUrl => {
    return dispatch => {
        dispatch(push(redirectUrl));
    }
};


export const typeSearch = (type, id) => {
    return dispatch => {
        const token = localStorage.getItem('user-token');
        let url = `${URL_PREFIX}`;
        if (type !== 'song') {
            url += '/song'
        }
        url += `/${type}/${id}`;
        return fetch(url, {
            headers: {
                [USER_TOKEN_HEADER]:
                token
            }
        })
            .then(res => {
                return res.json();
            })
            .then(res => {
                dispatch(setCurrentPlaylist(res));
            })
    };
};