"use strict";

var Horyu = window.Horyu || {};
Horyu.View = window.Horyu.View || {};
Horyu.View.EventHistory = function() {
    var _this = this;

    _this.init = function() {
        _this.bindEvents();
    };

    _this.bindEvents = function() {
        $('.viewEventBtn').click(function(event) {
            var eventId = $(event.target).attr('data-event-id');
            location.href = './view/' + eventId;
        });
    };

    return {
        init: function() {
            _this.init();
        }
    };
};