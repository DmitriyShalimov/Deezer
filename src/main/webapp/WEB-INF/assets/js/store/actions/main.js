import {
    FILL_SEARCH_OPTIONS,
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
    NOT_FOUND,
    SET_GENRES
} from './actionTypes.js'
import {push} from 'react-router-redux';
import {setTrack} from "./track-actions.js";

const URL_PREFIX = '/api/v1/';
const USER_TOKEN_HEADER = 'User-Token';

const setNotFound = isNotFound => {
    return {
        type: NOT_FOUND,
        payload: isNotFound
    }
};
export const setCurrentPlaylist = (songs, title) => {
    return {
        type: SET_CURRENT_PLAYLIST,
        payload: {songs, title}
    }
};

const fillSearch = searchOptions => {
    return {
        type: FILL_SEARCH_OPTIONS,
        payload: searchOptions
    }
};
const setUserPlaylists = playlists => {
    return {
        type: SET_USER_PLAYLISTS,
        payload: playlists
    }
};


const setGenres = genres => {
    return {
        type: SET_GENRES,
        payload: genres
    }
};
const setAllPublicPlaylists = playlists => {
    return {
        type: SET_ALL_PUBLIC_PLAYLISTS,
        payload: playlists
    }
};

const setFavouritePlaylists = playlists => {
    return {
        type: SET_FAVOURITE_PLAYLISTS,
        payload: playlists
    }
};

const setTopPlaylists = playlists => {
    return {
        type: SET_TOP_PLAYLISTS,
        payload: playlists
    }
};

const setRecommendedPlaylists = playlists => {
    return {
        type: SET_RECOMMENDED_PLAYLISTS,
        payload: playlists
    }
};

const setPagePlaylistMeta = playlist => {
    return {
        type: SET_PAGE_PLAYLIST_META,
        payload: playlist
    }
};

const setPagePlaylist = tracks => {
    return {
        type: SET_PAGE_PLAYLIST,
        payload: tracks
    }
};

const setArtists = artists => {
    return {
        type: SET_ARTISTS,
        payload: artists
    }
};

const setAlbums = albums => {
    return {
        type: SET_ALBUMS,
        payload: albums
    }
};

export const getSearchOptions = () => {
    return dispatch => {
        const token = localStorage.getItem('user-token');
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


export const typeSearch = (type, id, title) => {
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
                dispatch(setCurrentPlaylist(res, title ? title : getTitle(res, type)));
                dispatch(setTrack(getTrack(res)))
            })
    };
};

const getTitle = (playlist, type) => {
    if (type === 'song' || type === 'playlist') {
        return playlist.title;
    } else if (type === 'artist') {
        return playlist[0].artist.name;
    } else if (type === 'album') {
        return playlist[0].album.title;
    }
};

const getTrack = (currentPlaylist) => {
    if (!currentPlaylist) return;
    if (!Array.isArray(currentPlaylist)) {
        currentPlaylist = [currentPlaylist];
    }
    if (currentPlaylist.length === 0) return;
    return currentPlaylist[0];
};

export const likeTrack = (id) => {
    return dispatch => {
        const token = localStorage.getItem('user-token');
        return fetch(`${URL_PREFIX}song/${id}/like`, {
            method: 'POST',
            headers: {
                [USER_TOKEN_HEADER]:
                token
            }
        })
            .then(res => {
                    if (res.status !== 200) {
                        console.log(res);
                    }
                }
            )
    };
};

export const likePlaylist = (id) => {
    return dispatch => {
        const token = localStorage.getItem('user-token');
        return fetch(`${URL_PREFIX}playlist/${id}/like`, {
            method: 'POST',
            headers: {
                [USER_TOKEN_HEADER]:
                token
            }
        })
            .then(res => {
                    if (res.status !== 200) {
                        console.log(res);
                    }
                }
            )
    };
};

export const getUserPlaylists = () => {
    return dispatch => {
        const token = localStorage.getItem('user-token');
        return fetch(`${URL_PREFIX}playlist/user`, {
            headers: {
                [USER_TOKEN_HEADER]:
                token
            }
        })
            .then(res => res.json())
            .then(res => dispatch(setUserPlaylists(res)));
    };
};

export const getGenres = () => {
    return dispatch => {
        const token = localStorage.getItem('user-token');
        return fetch(`${URL_PREFIX}genre`, {
            headers: {
                [USER_TOKEN_HEADER]:
                token
            }
        })
            .then(res => res.json())
            .then(res => dispatch(setGenres(res)));
    };
};

export const getAllPublicPlaylists = () => {
    return dispatch => {
        const token = localStorage.getItem('user-token');
        return fetch(`${URL_PREFIX}playlist/public`, {
            headers: {
                [USER_TOKEN_HEADER]:
                token
            }
        })
            .then(res => res.json())
            .then(res => dispatch(setAllPublicPlaylists(res)));
    };
};

export const getFavouritePlaylists = () => {
    return dispatch => {
        const token = localStorage.getItem('user-token');
        return fetch(`${URL_PREFIX}playlist/liked`, {
            headers: {
                [USER_TOKEN_HEADER]:
                token
            }
        })
            .then(res => res.json())
            .then(res => dispatch(setFavouritePlaylists(res)));
    };
};

export const getTopPlaylists = () => {
    return dispatch => {
        const token = localStorage.getItem('user-token');
        return fetch(`${URL_PREFIX}playlist/top`, {
            headers: {
                [USER_TOKEN_HEADER]:
                token
            }
        })
            .then(res => res.json())
            .then(res => dispatch(setTopPlaylists(res)));
    };
};

export const getRecommendedPlaylists = () => {
    return dispatch => {
        const token = localStorage.getItem('user-token');
        return fetch(`${URL_PREFIX}playlist/recommended`, {
            headers: {
                [USER_TOKEN_HEADER]:
                token
            }
        })
            .then(res => res.json())
            .then(res => dispatch(setRecommendedPlaylists(res)));
    };
};

export const createPlaylist = (playlistName, accessModifier, trackId) => {
    return dispatch => {
        const token = localStorage.getItem('user-token');
        return fetch(`${URL_PREFIX}playlist`, {
            method: 'POST',
            headers: new Headers({
                [USER_TOKEN_HEADER]: token,
                'Content-Type': 'application/x-www-form-urlencoded',
            }),
            body: `access=${accessModifier}&title=${playlistName}&song=${trackId}`
        })
            .then(res => {
                    if (res.status != 200) {
                        console.log(res);
                    } else {
                        dispatch(getUserPlaylists());
                    }
                }
            )
    }
};

export const addTrackToPlaylist = (playlistId, trackId) => {
    return dispatch => {
        const token = localStorage.getItem('user-token');
        return fetch(`${URL_PREFIX}playlist/${playlistId}/song/${trackId}`, {
            method: 'POST',
            headers: {
                [USER_TOKEN_HEADER]: token
            }
        })
            .then(res => {
                    if (res.status != 200) {
                        console.log(res);
                    }
                }
            )
    }
};

export const getPagePlaylistMeta = (type, id) => {
    return dispatch => {
        const token = localStorage.getItem('user-token');
        return fetch(`${URL_PREFIX}${type}/${id}`, {
            headers: {
                [USER_TOKEN_HEADER]: token
            }
        })
            .then(res => {
                if (res.status === 404) {
                    return dispatch(setNotFound(true));
                } else {
                    dispatch(setNotFound(false));
                    return res.json();
                }
            })
            .then(res => dispatch(setPagePlaylistMeta(res)));
    }
};

export const getPagePlaylist = (type, id) => {
    return dispatch => {
        const token = localStorage.getItem('user-token');
        return fetch(`${URL_PREFIX}song/${type}/${id}`, {
            headers: {
                [USER_TOKEN_HEADER]: token
            }
        })
            .then(res => {
                if (res.status === 404) {
                    return dispatch(setNotFound(true));
                } else {
                    dispatch(setNotFound(false));
                    return res.json();
                }
            })
            .then(res => dispatch(setPagePlaylist(res)));
    }
};

export const getSearchResult = (type, mask) => {
    return dispatch => {
        const token = localStorage.getItem('user-token');
        return fetch(`${URL_PREFIX}${type}/search/${mask}`, {
            headers: {
                [USER_TOKEN_HEADER]: token
            }
        })
            .then(res => res.json())
            .then(res => {
                if (type === 'artist') {
                    return dispatch(setArtists(res));
                }
                if (type === 'album') {
                    return dispatch(setAlbums(res));
                }
                if (type === 'song') {
                    return dispatch(setPagePlaylist(res));
                }
            });
    }
};

export const getRandom = () => {
    return dispatch => {
        const token = localStorage.getItem('user-token');
        return fetch(`${URL_PREFIX}song/random`, {
            headers: {
                [USER_TOKEN_HEADER]:
                token
            }
        })
            .then(res => {
                return res.json();
            })
            .then(res => {
                dispatch(setCurrentPlaylist(res, "Random"));
                dispatch(setTrack(getTrack(res)))
            })
    };
};