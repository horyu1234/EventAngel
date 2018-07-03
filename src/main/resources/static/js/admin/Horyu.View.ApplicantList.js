"use strict";

var Horyu = window.Horyu || {};
Horyu.View = window.Horyu.View || {};
Horyu.View.ApplicantList = function(options) {
    var _this = this;

    var defaultOptions = {
        columnId: '',
        columnName: ''
    };

    _this.options = $.extend(defaultOptions, options);

    _this.init = function() {
        _this.changeMainContainerWidth();
        _this.updateTable();
    };

    _this.changeMainContainerWidth = function() {
        var $mainContainer = $('#main-container');
        $mainContainer.removeClass('container');
        $mainContainer.addClass('container-full');
    };

    _this.updateTable = function() {
        $('#loading-modal').modal({
            backdrop: 'static',
            keyboard: false
        });

        $.ajax({
            type: 'POST',
            url: './getApplicant',
            data: {
                columnId: _this.options.columnId === null ? undefined : _this.options.columnId,
                columnName: _this.options.columnName === null ? undefined : _this.options.columnName
            },
            timeout: 10000
        }).done(function(result) {
            $('#loading-modal').modal('hide');

            _this.updatePagination(result);
        });
    };

    _this.updatePagination = function(applicantList) {
        $('#applyCount').text(applicantList.length);

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

        $('.duplicatedBtn').click(function() {
            var applyEmail = $(this).attr('data-apply-email');
            _this.updateDuplicated(applyEmail, true);
        });

        $('.notDuplicatedBtn').click(function() {
            var applyEmail = $(this).attr('data-apply-email');
            _this.updateDuplicated(applyEmail, false);
        });
    };

    _this.renderTable = function(pageData) {
        var $table = $('.applicant-list-table');

        var $tableBody = $table.find('tbody');
        $tableBody.empty();

        pageData.forEach(function(data) {
            var $tr = $('<tr></tr>').attr('data-apply-email', data.applyEmail);
            $tr.append($('<td class="apply-time">').text(data.applyTime));
            $tr.append($('<td class="apply-email">').text(data.applyEmail));
            $tr.append($('<td class="apply-youtubeNickname">').text(data.youtubeNickname));
            $tr.append($('<td class="apply-ipAddress">').text(data.ipAddress));
            $tr.append($('<td class="apply-userAgent">').text(data.userAgent));
            $tr.append($('<td class="apply-fingerprint2">').text(data.fingerprint2));
            $tr.append($('<td class="apply-duplicated">').text(data.duplicated));

            if (data.duplicated) {
                $tr.addClass('table-warning');
                $tr.append($('<td>').html($('<button type="button" class="btn btn-danger btn-sm notDuplicatedBtn">중복 응모 처리 취소</button>').attr('data-apply-email', data.applyEmail)));
            } else {
                $tr.append($('<td>').html($('<button type="button" class="btn btn-warning btn-sm duplicatedBtn">중복 응모 처리(추첨 제외)</button>').attr('data-apply-email', data.applyEmail)));
            }

            $tr.append($('<td class="apply-dupCheckAdminName">').text(data.dupCheckAdminName));

            $tableBody.append($tr);
        });
    };

    _this.updateDuplicated = function(applyEmail, isDuplicated) {
        $.ajax({
            type: 'POST',
            url: './changeDuplicated',
            data: {
                applyEmail: applyEmail,
                isDuplicated: isDuplicated
            },
            timeout: 10000
        }).done(function() {
            var $targetTr = $('tr[data-apply-email=\'' + applyEmail + '\']');
            var $dupManageTd = $targetTr.find('td:eq(7)');
            var $dupManageAdminTd = $targetTr.find('td:eq(8)');

            if (isDuplicated) {
                $targetTr.addClass('table-warning');
                $dupManageTd.text('중복 처리됨');
                $dupManageAdminTd.text('중복 처리됨');
            } else {
                $targetTr.removeClass('table-warning');
                $dupManageTd.text('중복 처리 취소됨');
                $dupManageAdminTd.text('중복 처리 취소됨');
            }
        });
    };

    return {
        init: function() {
            _this.init();
        }
    };
};