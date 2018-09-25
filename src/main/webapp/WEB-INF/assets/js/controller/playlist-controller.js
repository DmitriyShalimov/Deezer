import DeezerUtil from "../deezer-util.js";

export default class PlayListController {
    constructor(playListView) {
        this.playlistView = playListView;

        $(this.playlistView).on('genre', (event, id) => this.changeGenre(id));
        $(this.playlistView).on('artist', (event, id) => this.changeArtist(id));
        $(this.playlistView).on('album', (event, id) => this.changeAlbum(id));
        $(this.playlistView).on('logout', ()=> this.logout());
        $(this.playlistView).on('load', ()=>{
            this.loadGenres();
            this.loadArtists();
        })
    }

    logout(){
        $.get('/logout', null, $(location).attr('pathname', '/login'));
    }
    loadGenres(){
        $.ajax({
            type: "GET",
            url: "/genres",
            headers: {
                Accept: 'application/json'
            },
            success: data => DeezerUtil.showGenres(data, this.playlistView)
        });
    }
    loadArtists(){
        $.ajax({
            type: "GET",
            url: "/artists",
            headers: {
                Accept: 'application/json'
            },
            success: data => DeezerUtil.showArtists(data, this.playlistView)
        });
    }

    changeAlbum(id) {
        $.ajax({
            type: "GET",
            url: "/album/" + id,
            headers: {
                Accept: 'application/json'
            }
            , success: data => this.playlistView.createPlayer(data)
        });
    }

    changeGenre(id) {
        $.ajax({
            type: "GET",
            url: "/genre/" + id,
            headers: {
                Accept: 'application/json'
            },
            success: data => {
                console.log(data);
                this.playlistView.createPlayer(data)
            }
        });
    }

    changeArtist(id) {
        $.ajax({
            type: "GET",
            url: "/artist/" + id,
            headers: {
                Accept: 'application/json'
            },
            success: data => this.playlistView.createPlayer(data)
        });
    }



}