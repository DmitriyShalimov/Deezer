export default class PlayListController {
    constructor(view) {
        this.view = view;

        $(this.view).on('genre', (event, id) => this.changeGenre(id));
        $(this.view).on('artist', (event, id) => this.changeArtist(id));
        $(this.view).on('albumSongs', (event, id) => this.changeAlbumSongs(id));
        $(this.view).on('logout', ()=> this.logout());

    }

    logout(){
        $.get('/logout', null, $(location).attr('pathname', '/login'));
    }

    changeAlbumSongs(id) {
        $.ajax({
            type: "GET",
            url: "/albumsongs/" + id,
            headers: {
                Accept: 'application/json'
            }
            , success: data => this.view.createPlayer(data)
        });
    }

    changeGenre(id) {
        console.log('genre ' + id);
        $.ajax({
            type: "GET",
            url: "/genre/" + id,
            headers: {
                Accept: 'application/json'
            },
            success: data => this.view.createPlayer(data)
        });
    }

    changeArtist(id) {
        $.ajax({
            type: "GET",
            url: "/album/" + id,
            headers: {
                Accept: 'application/json'
            },
            success: data =>
                this.view.createAlbums(data)
        });

        $.ajax({
            type: "GET",
            url: "/artist/" + id,
            headers: {
                Accept: 'application/json'
            },
            success: data => this.view.createPlayer(data)
        });
    }

}