import MainController from "./controller/main-controller.js";
import MainView from "./view/main-view.js";
import SearchView from "./view/search-view.js";
import SearchController from "./controller/search-controller.js";
import PlaylistView from "./view/playlist-view.js";
import PlaylistController from "./controller/playlist-controller.js";
import DeezerUtil from "./deezer-util.js";

$(document).foundation();
const view = new MainView();
const searchView = new SearchView(view);
const playlistView = new PlaylistView(view);
const playlistController = new PlaylistController(playlistView);
const mainController = new MainController(view);
const searchController = new SearchController(searchView);


jQuery(document).ready(() => {
    const pathname = window.location.pathname;
    console.info(`Requested path: ${pathname}`);
    if (pathname === '/') {
        mainController.load();
    } else if (pathname.startsWith('/search/')) {
        const mask = pathname.split('/search/').pop();
        if (mask) {
            searchController.search(mask);
        }
    } else if (pathname.startsWith('/genre/')) {
        const id = pathname.split('/genre/').pop();
        if (id) {
            mainController.loadCardMeta(id, 'genre');
        }
    } else if (pathname.startsWith('/playlist/')) {
        const id = pathname.split('/playlist/').pop();
        if (id) {
            mainController.loadCardMeta(id, 'playlist');
        }
    } else if (pathname.startsWith('/album/')) {
        const id = pathname.split('/album/').pop();
        if (id) {
            mainController.loadCardMeta(id, 'album');
        }
    } else if (pathname.startsWith('/artist')) {
        const id = pathname.split('/artist/').pop();
        if (id) {
            mainController.loadCardMeta(id, 'artist');
        }
    } else if (pathname === '/music-library') {
        playlistView.showLibrary();
    } else if (pathname === '/playlists') {
        playlistController.getAllPlaylists(playlistView.showPublicPlaylists.bind(playlistView));
    }
});


$(window).on('popstate', () => {
    const pathname = window.location.pathname;
    console.info(`Popstate requested path: ${pathname}`);
    if (pathname === '/') {
        DeezerUtil.hideMainPlaylists();
        DeezerUtil.showTopPlaylists(history.state.filter(st => st.key === 'top-playlists')[0].value, view);
        DeezerUtil.showGenres(history.state.filter(st => st.key === 'genres')[0].value, view)
    } else if (pathname.startsWith('/search/')) {
        DeezerUtil.hideMainPlaylists();
        searchView.showSongs(history.state.filter(st => st.key === 'songs')[0].value, true);
        DeezerUtil.showArtists(history.state.filter(st => st.key === 'artists')[0].value, view, true);
        DeezerUtil.showAlbums(history.state.filter(st => st.key === 'albums')[0].value, view);
    } else if (pathname.startsWith('/genre/') || pathname.startsWith('/playlist/')
        || pathname.startsWith('/album/') || pathname.startsWith('/artist')) {
        DeezerUtil.showItem(history.state.data, history.state.meta, view);
    } else if (pathname === '/music-library') {
        playlistView.handlePlaylists(history.state);
    } else if (pathname === '/playlists') {
        playlistView.showPublicPlaylists(history.state);
    }
});
