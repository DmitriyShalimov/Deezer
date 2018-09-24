import PlayListController from "./controller/playlist-controller.js";
import PlayListView from "./view/playlist-view.js";

const playlistView = new PlayListView();
new PlayListController(playlistView);