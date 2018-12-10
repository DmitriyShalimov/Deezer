import {
    ERROR,
    FILL_SEARCH_OPTIONS,
    SET_IS_AUTHENTICATED,
    SET_CURRENT_PLAYLIST,
    SET_USER_PLAYLISTS,
    SET_ALL_PUBLIC_PLAYLISTS,
    SET_FAVOURITE_PLAYLISTS,
    SET_TOP_PLAYLISTS,
    SET_RECOMMENDED_PLAYLISTS,
    SET_PAGE_PLAYLIST_META,
    SET_PAGE_PLAYLIST,
    SET_ARTISTS,
    SET_ALBUMS,
    NOT_FOUND
} from "../actions/actionTypes.js";

const initialState = {
    notFound: false,
    user: {},
    errorMessage: '',
    searchOptions: [],
    isAuth: '',
    currentPlaylist: [],
    currentPlaylistTitle: "",
    userPlaylists: [],
    allPublicPlaylists: [],
    favouritePlaylists: [],
    topPlaylists: [],
    recommendedPlaylists: [],
    pagePlaylistMeta: {},
    pagePlaylist: [],
    albums: [],
    artists: [],
};

export const rootReducer = (state = initialState, action) => {
    const {payload} = action;
    switch (action.type) {
        case SET_IS_AUTHENTICATED:
            return {...state, isAuth: payload};
        case FILL_SEARCH_OPTIONS:
            return {...state, searchOptions: payload};
        case SET_CURRENT_PLAYLIST:
            return {...state, currentPlaylist: payload.songs, currentPlaylistTitle: payload.title};
        case SET_USER_PLAYLISTS:
            return {...state, userPlaylists: payload};
        case SET_ALL_PUBLIC_PLAYLISTS:
            return {...state, allPublicPlaylists: payload};
        case SET_FAVOURITE_PLAYLISTS:
            return {...state, favouritePlaylists: payload};
        case SET_TOP_PLAYLISTS:
            return {...state, topPlaylists: payload};
        case SET_RECOMMENDED_PLAYLISTS:
            return {...state, recommendedPlaylists: payload};
        case SET_PAGE_PLAYLIST_META:
            return {...state, pagePlaylistMeta: payload};
        case SET_PAGE_PLAYLIST:
            return {...state, pagePlaylist: payload};
        case SET_ARTISTS:
            return {...state, artists: payload};
        case SET_ALBUMS:
            return {...state, albums: payload};
        case NOT_FOUND:
            return {...state, notFound: payload};
        case ERROR:
            return {...state, errorMessage: payload};
        default:
            return state;
    }
};