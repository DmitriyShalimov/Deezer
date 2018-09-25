import PlayListController from "./controller/playlist-controller.js";
import PlayListView from "./view/playlist-view.js";
import SearchView from "./view/search-view.js";
import SearchController from "./controller/search-controller.js";

const playlistView = new PlayListView();
const searchView = new SearchView(playlistView);
new PlayListController(playlistView);
new SearchController(searchView);