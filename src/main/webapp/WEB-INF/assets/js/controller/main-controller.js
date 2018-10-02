import DeezerUtil from "../deezer-util.js";

const URI_PREFIX = '/api/v1';

export default class MainController {
    constructor(playListView) {
        this.playlistView = playListView;
        $(this.playlistView).on('genre', (event, genre) => this.changeGenre(genre.item, genre.cb));
        $(this.playlistView).on('artist', (event, artist) => this.changeArtist(artist.item, artist.cb));
        $(this.playlistView).on('album', (event, album) => this.changeAlbum(album.item, album.cb));
        $(this.playlistView).on('playlist', (event, playlist) => this.changePlaylist(playlist.item, playlist.cb));
        $(this.playlistView).on('like', (event, id) => this.addLikeToSong(id));
        $(this.playlistView).on('logout', () => this.logout());
        $(this.playlistView).on('load', () => {
            this.load();

        });
        $(this.playlistView).on('random', () => this.getRandomSongs());
        $(this.playlistView).on('like-pl', (e, id) => this.likePlaylist(id));
    }

    logout() {
        $.get('/logout', null, $(location).attr('pathname', '/login'));
    }

    load() {
        let state = [];
        let genres = $.ajax({
            type: "GET",
            url: `${URI_PREFIX}/genres`,
            success: data => {
                state.push({key: 'genres', value: data});
                DeezerUtil.showGenres(data, this.playlistView);
            }
        });
        let playlists = $.ajax({
            type: "GET",
            url: `${URI_PREFIX}/top-playlists`,
            success: data => {
                state.push({key: 'top-playlists', value: data});
                DeezerUtil.showTopPlaylists(data, this.playlistView);
            }
        });

        $.when(genres, playlists).done(() => history.pushState(state, 'Deezer', '/'));
    }


    changeAlbum(album, success) {
        console.log(album);
        $.ajax({
            type: "GET",
            url: `${URI_PREFIX}/album/${album.id}/songs`,
            headers: {
                Accept: 'application/json'
            },
            success: data => {
                success(data, album, this.playlistView);
                if (album.pushState) {
                    history.pushState({meta: album, data}, 'album', `/album/${album.id}`);
                }
            }
        });
    }

    changeGenre(genre, success) {
        $.ajax({
            type: "GET",
            url: `${URI_PREFIX}/genre/${genre.id}/songs`,
            headers: {
                Accept: 'application/json'
            },
            success: data => {
                success(data, genre, this.playlistView);
                if (genre.pushState) {
                    history.pushState({meta: genre, data}, 'genre', `/genre/${genre.id}`);
                }
            }
        });
    }

    changeArtist(artist, success) {
        if (artist.albumsRequired) {
            $.ajax({
                type: "GET",
                url: `${URI_PREFIX}/artist/${artist.id}/albums`,
                headers: {
                    Accept: 'application/json'
                },
                success: data => {
                    artist.albums = data;
                    this.getArtistSongs(artist, success);

                }
            });
        } else {
            this.getArtistSongs(artist, success);
        }
    }

    getArtistSongs(artist, success) {
        $.ajax({
            type: "GET",
            url: `${URI_PREFIX}/artist/${artist.id}/songs`,
            headers: {
                Accept: 'application/json'
            },
            success: data => {
                success(data, artist, this.playlistView);
                if (artist.pushState) {
                    history.pushState({meta: artist, data}, 'artist', `/artist/${artist.id}`);
                }
            }
        });
    }

    changePlaylist(playlist, success) {
        $.ajax({
            type: "GET",
            url: `${URI_PREFIX}/playlist/${playlist.id}/songs`,
            headers: {
                Accept: 'application/json'
            },
            success: data => {
                success(data, playlist, this.playlistView);
                if (playlist.pushState) {
                    history.pushState({meta: playlist, data}, 'playlist', `/playlist/${playlist.id}`);
                }
            }
        });
    }

    getRandomSongs() {
        $.ajax({
            type: "GET",
            url: `${URI_PREFIX}/random`,
            headers: {
                Accept: 'application/json'
            },
            success: data => this.playlistView.createPlayer(data, {title: 'Random songs', picture: data[0].picture})
        });
    }

    loadCardMeta(id, type) {
        $.ajax({
            type: "GET",
            url: `${URI_PREFIX}/${type}/${id}`,
            headers: {
                Accept: 'application/json'
            },
            success: data => {
                data.type = type;
                this.playlistView.getItemToShowFromResult(data);
            }
        });
    }

    addLikeToSong(id) {
        $.ajax({
            type: "POST",
            url: `${URI_PREFIX}/song/${id}/like`,
            success: data => {
                this.playlistView.toggleLike(id);
            }
        });
    }

    likePlaylist(id) {
        $.ajax({
            type: "POST",
            url: `${URI_PREFIX}/playlist/${id}/like`,
            success: data => {
                this.playlistView.toggleLikePlaylist(id);
            }
        });
    }
}