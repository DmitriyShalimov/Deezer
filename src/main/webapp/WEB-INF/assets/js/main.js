import MainController from "./controller/main-controller.js";
import MainView from "./view/main-view.js";
import SearchView from "./view/search-view.js";
import SearchController from "./controller/search-controller.js";
$(document).foundation();
const view = new MainView();
const searchView = new SearchView(view);
new MainController(view);
new SearchController(searchView);