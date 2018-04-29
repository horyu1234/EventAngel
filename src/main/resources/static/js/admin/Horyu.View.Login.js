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
            $('.loginBtn').attr('disabled', '');
            $('.loginBtn').html('<i class="fas fa-spinner fa-spin"></i> 비밀번호를 암호화하는 중입니다...');

            $('#loginForm').submit();
        });
    };

    return {
        init: function() {
            _this.init();
        }
    };
};