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
/*
var googleUser = {};

var startApp = function () {
    gapi.load('auth2', function () {
        // Retrieve the singleton for the GoogleAuth library and set up the client.
        let auth2 = gapi.auth2.init({
            client_id: '582754350751-9l1l5bqs8bga96g6651mmn3d1qm4s3p4.apps.googleusercontent.com',
            cookiepolicy: 'single_host_origin',
            // Request scopes in addition to 'profile' and 'email'
            //scope: 'additional_scope'
        });
        attachSignin($('#google-login'), auth2);
    });
    console.log(googleUser);
};

function attachSignin(element, auth2) {
    console.log(element.id);
    auth2.attachClickHandler(element, {},
        function (googleUser) {
            console.log(googleUser.getBasicProfile());
        }, function (error) {
            alert(JSON.stringify(error, undefined, 2));
        });
}
startApp();*/
