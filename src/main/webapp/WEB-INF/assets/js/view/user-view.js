class UserView {
    constructor() {
        const _userView = this;
        $('#login-btn').click(function () {
            _userView.handleLogin();
        });

        $('#register').click(function () {
            _userView.handleRegister();
        });
        $('#fb-login').click(function () {
            _userView.handleFbLogin();
        })
        this.initFbLogin();
        this.initGoogleLogin();
    }

    initGoogleLogin() {
        gapi.load('auth2', function () {
            let auth2 = gapi.auth2.init({
                client_id: '582754350751-9l1l5bqs8bga96g6651mmn3d1qm4s3p4.apps.googleusercontent.com',
                cookiepolicy: 'single_host_origin',
            });

            this.attachSignin($('#google-login')[0], auth2);
        }.bind(this));
    }

    attachSignin(element, auth2) {
        auth2.attachClickHandler(element, {},
            function (googleUser) {
                let user = googleUser.getBasicProfile();
                $(this).trigger('google-login', {login: user.U3, password:user.Eea});
            }.bind(this), function (error) {
                alert(JSON.stringify(error, undefined, 2));
            });
    }

    initFbLogin() {
        let e = document.createElement('script');
        e.src = $(location).attr('protocol') + '//connect.facebook.net/en_US/all.js';
        e.async = true;
        $('main').append(e);
    }

    handleLogin() {
        const login = $('#login').first().val();
        const password = $('#password').first().val();
        if (login && password) {
            $(this).trigger('login', {login, password});
        }
    }

    handleRegister() {
        const login = $('#login').first().val();
        const password = $('#password').first().val();
        const password2 = $('#password2').first().val();
        if (login && password && password2) {
            if (password === password2) {
                $(this).trigger('register', {login, password});
            } else {
                this.showError("**Passwords do not match");
            }
        }
    }

    handleFbLogin() {
        $(this).trigger('fb-login');
    }

    showError(message) {
        $("#error").text(message);
        $(":input").val('');
    }
}

export default UserView;