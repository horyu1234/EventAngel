"use strict";

var Horyu = window.Horyu || {};
Horyu.View = window.Horyu.View || {};
Horyu.View.EventHistoryView = function(options) {
    var _this = this;

    var defaultOptions = {
        companyGiftData: [],
        eventWinnerData: []
    };

    _this.options = $.extend(defaultOptions, options);

    _this.init = function() {
        _this.companyGiftTable = new Horyu.View.CompanyGiftTable();
        _this.companyGiftTable.init();

        _this.companyGiftTable.updateTable(_this.options.companyGiftData, _this.options.eventWinnerData);
    };

    return {
        init: function() {
            _this.init();
        }
    };
};