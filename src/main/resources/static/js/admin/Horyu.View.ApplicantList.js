"use strict";

var Horyu = window.Horyu || {};
Horyu.View = window.Horyu.View || {};
Horyu.View.ApplicantList = function() {
    var _this = this;

    _this.init = function() {
        _this.updateTable();
    };

    _this.updateTable = function() {
        $.ajax({
            type: 'POST',
            url: './getApplicant',
            data: {},
            timeout: 10000
        }).done(function(result) {
            console.log(result);

            _this.updatePagination(result);
        });
    };

    _this.updatePagination = function(applicantList) {
        $('.table-pagination').pagination({
            className: 'paginationjs-theme-blue paginationjs-big',
            dataSource: applicantList,
            pageSize: 50,
            autoHidePrevious: true,
            autoHideNext: true,
            callback: function(pageData, pagination) {
                _this.renderTable(pageData);
            }
        });
    };

    _this.renderTable = function(pageData) {
        var $table = $('.applicant-list-table');

        var $tableBody = $table.find('tbody');
        $tableBody.empty();

        pageData.forEach(function(data) {
            var $tr = $('<tr></tr>');
            $tr.append($('<td class="apply-time">').text(data.applyTime));
            $tr.append($('<td class="apply-email">').text(data.applyEmail));
            $tr.append($('<td class="apply-youtubeNickname">').text(data.youtubeNickname));
            $tr.append($('<td class="apply-ipAddress">').text(data.ipAddress));
            $tr.append($('<td class="apply-userAgent">').text(data.userAgent));
            $tr.append($('<td class="apply-fingerprint2">').text(data.fingerprint2));
            $tr.append($('<td class="apply-duplicated">').text(data.duplicated));

            $tableBody.append($tr);
        });
    };

    return {
        init: function() {
            _this.init();
        }
    };
};