"use strict";

var Horyu = window.Horyu || {};
Horyu.View = window.Horyu.View || {};
Horyu.View.Login = function() {
    var _this = this;

    _this.init = function() {
        _this.bindEvents();
    };

    _this.bindEvents = function() {
        $('.loginBtn').click(function() {
            grecaptcha.execute();
        });
    };

    return {
        init: function() {
            _this.init();
        }
    };
};

function onReCaptchaCallback() {
    $('#loginForm').submit();
}