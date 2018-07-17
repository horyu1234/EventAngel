"use strict";

var Horyu = window.Horyu || {};
Horyu.View = window.Horyu.View || {};
Horyu.View.Check = function() {
    var _this = this;

    _this.init = function() {
        _this.initParsley();
        _this.bindEvents();
    };

    _this.initParsley = function() {
        $("#checkForm").parsley({
            successClass: "is-valid",
            errorClass: "is-invalid",
            classHandler: function(element) {
                return element.$element.closest("input");
            }
        });
    };

    _this.bindEvents = function() {
        $('.submitBtn').click(function() {
            var isFormValidate = $('#checkForm').parsley().validate();
            if (isFormValidate) {
                grecaptcha.execute();
            }
        });
    };

    return {
        init: function() {
            _this.init();
        }
    };
};

function onReCaptchaCallback() {
    $('#checkForm').submit();
}