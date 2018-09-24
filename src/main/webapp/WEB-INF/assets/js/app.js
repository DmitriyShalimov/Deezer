import UserView from "./view/user-view.js";
import UserController from "./controller/user-controller.js";

//$(document).foundation();
window.fbAsyncInit = function () {
    FB.init({
        appId: '330979217471281',
        oauth: true,
        status: true, // check login status
        cookie: true, // enable cookies to allow the server to access the session
        xfbml: true // parse XFBML
    });

};
const userView = new UserView();
new UserController(userView);
