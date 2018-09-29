import MainController from "./controller/main-controller.js";
import MainView from "./view/main-view.js";
import SearchView from "./view/search-view.js";
import SearchController from "./controller/search-controller.js";
import PlaylistView from "./view/playlist-view.js";
import PlaylistController from "./controller/playlist-controller.js";

$(document).foundation();
const view = new MainView();
const searchView = new SearchView(view);
const playlistView = new PlaylistView();
new PlaylistController(playlistView);
const mainController = new MainController(view);
const searchController = new SearchController(searchView);


jQuery(document).ready(function ($) {
    const pathname = window.location.pathname;
    console.info(`Requested path: ${pathname}`);
    if (pathname === '/') {
        mainController.loadGenres();
    } else if (pathname.startsWith('/search/')) {
        const mask = pathname.split('/search/').pop();
        if (mask) {
            searchController.search(mask);
        }
    }
});