import DeezerUtil from "../deezer-util.js";

export default class SearchController {
    constructor(view) {
        this.view = view;
        $(this.view).on('typeSearch', (event, data) => this.searchByType(data));
        $(this.view).on('generalSearch', (event, mask) => this.search(mask));
        this.loadOptions();
    }

    loadOptions() {
        $.get('/search', {}, (data) => this.view.setOptions(data));
    }

    search(mask) {
        this.view.hideMainPlaylists();
        $.ajax({
            type: "GET",
            url: `/song/search/${mask}`,
            headers: {
                Accept: 'application/json'
            },
            success: result =>
                this.view.showSongs(result, true)
        });
        $.ajax({
            type: "GET",
            url: `/artist/search/${mask}`,
            headers: {
                Accept: 'application/json'
            },
            success: result => DeezerUtil.showArtists(result, this.view.playlistView, true)
        });
        $.ajax({
            type: "GET",
            url: `/album/search/${mask}`,
            headers: {
                Accept: 'application/json'
            },
            success: result => DeezerUtil.showAlbums(result, this.view.playlistView)
        });
    }

    searchByType(data) {
        $.ajax({
            type: "GET",
            url: `/${data.type}/${data.id}`,
            headers: {
                Accept: 'application/json'
            },
            success: result => this.view.playSearchResult(data.type, result, data)
        });
    }
}