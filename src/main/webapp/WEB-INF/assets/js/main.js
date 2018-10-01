import MainController from "./controller/main-controller.js";
import MainView from "./view/main-view.js";
import SearchView from "./view/search-view.js";
import SearchController from "./controller/search-controller.js";
import PlaylistView from "./view/playlist-view.js";
import PlaylistController from "./controller/playlist-controller.js";

$(document).foundation();
const view = new MainView();
const searchView = new SearchView(view);
const playlistView = new PlaylistView(view);
const playlistController = new PlaylistController(playlistView);
const mainController = new MainController(view);
const searchController = new SearchController(searchView);


jQuery(document).ready(() => locationChange());

/* //TODO:popstate
$(window).on('popstate', () => {
    locationChange();
});
*/

function locationChange() {
    const pathname = window.location.pathname;
    console.info(`Requested path: ${pathname}`);
    if (pathname === '/') {
        mainController.loadGenres();
        mainController.loadTopPlaylists();
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
        playlistController.getUserPlaylists(playlistView.showUserPlaylists.bind(playlistView))
    }
}