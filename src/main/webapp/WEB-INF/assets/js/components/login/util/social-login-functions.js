export const initGoogleLogin = loginCallback => {
    gapi.load('auth2', () => {
        let auth2 = gapi.auth2.init({
            client_id: '582754350751-9l1l5bqs8bga96g6651mmn3d1qm4s3p4.apps.googleusercontent.com',
            cookiepolicy: 'single_host_origin',
        });
        attachSignin(document.getElementById('google-login'), auth2, loginCallback);
    });
};

const attachSignin = (element, auth2, callback) => {
    auth2.attachClickHandler(element, {},
        (googleUser) => {
            let user = googleUser.getBasicProfile();
            callback(user.U3, user.Eea);
        }, error => {
            console.log(JSON.stringify(error, undefined, 2));
        });
};

export const initFbLogin = () => {
    let e = document.createElement('script');
    e.src = location.protocol + '//connect.facebook.net/en_US/all.js';
    e.async = true;
    document.getElementById('react').append(e);
};
