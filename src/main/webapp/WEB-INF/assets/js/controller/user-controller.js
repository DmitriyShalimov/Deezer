class UserController {
    constructor(view) {
        this.view = view;
        const _userController = this;

        $(this.view).on('login', function (event, user) {
            _userController.doLogin(user,
                function () {
                    _userController.view.showError("**Invalid login/password");
                });
        });

        $(this.view).on('register', function (event, user) {
            _userController.doRegister(user,
                function () {
                    _userController.view.showError(`User ${user.login} already exists`);
                });
        });

        $(this.view).on('fb-login', function () {
            _userController.fbLogin();
        });

        $(this.view).on('google-login', function (event, user) {
            _userController.googleLogin(user);
        });

    }

    doLogin(user, onError) {
        $.post('/login', {login: user.login, password: user.password}, function (data) {
                $(location).attr('pathname', '/');
        }.bind(this));
    }

    doRegister(user, onError) {
        $.post('/registration', {login: user.login, password: user.password}, function (data) {
                this.doLogin(user);

        }.bind(this));

    }


    fbLogin() {
        FB.login(function (response) {
            if (response.authResponse) {
                let user_id = response.authResponse.userID; //get FB UID
                let user = {password: user_id};
                FB.api('/me', function (response) {
                    user.login = response.name;
                    this.doLogin(user, function () {
                        this.doRegister(user);
                    }.bind(this));
                }.bind(this));
            } else {
                //user hit cancel button
                console.log('User cancelled login or did not fully authorize.');
            }
        }.bind(this), {
            scope: 'public_profile,email'
        });
    }

    googleLogin(user){
        this.doLogin(user, function () {
            this.doRegister(user);
        }.bind(this));
    }

}

export default UserController;