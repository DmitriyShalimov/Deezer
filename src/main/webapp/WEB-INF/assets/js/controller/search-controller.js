import DeezerUtil from "../deezer-util.js";
const URI_PREFIX = '/api/v1';
export default class SearchController {
    constructor(view) {
        this.view = view;
        $(this.view).on('typeSearch', (event, data) => this.searchByType(data));
        $(this.view).on('generalSearch', (event, mask) => this.search(mask));
        this.loadOptions();
    }

    loadOptions() {
        $.get(`${URI_PREFIX}/search`, {}, (data) => this.view.setOptions(data));
    }

    search(mask) {
        DeezerUtil.hideMainPlaylists();
        $.ajax({
            type: "GET",
            url: `${URI_PREFIX}/song/search/${mask}`,
            headers: {
                Accept: 'application/json'
            },
            success: result => {
                this.view.showSongs(result, true);
                history.pushState(result, 'Search', `/search/${mask}`);
            }

        });
        $.ajax({
            type: "GET",
            url: `${URI_PREFIX}/artist/search/${mask}`,
            headers: {
                Accept: 'application/json'
            },
            success: result => {
                DeezerUtil.showArtists(result, this.view.playlistView, true);
                history.pushState(result, 'Search', `/search/${mask}`);
            }
        });
        $.ajax({
            type: "GET",
            url: `${URI_PREFIX}/album/search/${mask}`,
            headers: {
                Accept: 'application/json'
            },
            success: result => {
                DeezerUtil.showAlbums(result, this.view.playlistView);
                history.pushState(result, 'Search', `/search/${mask}`);
            }
        });
    }

    searchByType(data) {
        $.ajax({
            type: "GET",
            url: `${URI_PREFIX}/${data.type}/${data.id}`,
            headers: {
                Accept: 'application/json'
            },
            success: result => this.view.playSearchResult(data.type, result, data)
        });
    }
}