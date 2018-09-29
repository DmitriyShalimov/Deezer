import DeezerUtil from "../deezer-util.js";
const URI_PREFIX = '/api/v1';

export default class MainController {
    constructor(playListView) {
        this.playlistView = playListView;
        $(this.playlistView).on('genre', (event, genre) => this.changeGenre(genre));
        $(this.playlistView).on('artist', (event, artist) => this.changeArtist(artist));
        $(this.playlistView).on('album', (event, album) => this.changeAlbum(album));
        $(this.playlistView).on('logout', () => this.logout());
        $(this.playlistView).on('load', () => {
            this.loadGenres();
            history.pushState('', 'Deezer', '/')
        });
        $(this.playlistView).on('random', () => this.getRandomSongs())
    }

    logout() {
        $.get('/logout', null, $(location).attr('pathname', '/login'));
    }

    loadGenres() {
        $.ajax({
            type: "GET",
            url: `${URI_PREFIX}/genres`,
            success: data => {
                //console.log(data);
                DeezerUtil.showGenres(data, this.playlistView);
                //history.pushState(data, 'Genres', '/genres');
            }
        });
    }


    changeAlbum(album) {
        console.log(album);
        $.ajax({
            type: "GET",
            url: `${URI_PREFIX}/album/${album.id}`,
            headers: {
                Accept: 'application/json'
            }
            , success: data => this.playlistView.createPlayer(data, album)
        });
    }

    changeGenre(genre) {
        $.ajax({
            type: "GET",
            url: `${URI_PREFIX}/genre/${genre.id}`,
            headers: {
                Accept: 'application/json'
            },
            success: data => {
                this.playlistView.createPlayer(data, genre);
            }
        });
    }

    changeArtist(artist) {
        $.ajax({
            type: "GET",
            url: `${URI_PREFIX}/artist/${artist.id}`,
            headers: {
                Accept: 'application/json'
            },
            success: data => this.playlistView.createPlayer(data, artist)
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

}