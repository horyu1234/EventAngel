"use strict";

var Horyu = window.Horyu || {};
Horyu.View = window.Horyu.View || {};
Horyu.View.Duplicated = function() {
    var _this = this;

    _this.init = function() {
        _this.bindEvents();
        _this.updateTable(_this.getCurrentColumnId());
    };

    _this.bindEvents = function() {
        $('.columnNameButton').change(function() {
            var columnName = $(this).attr('id');

            _this.updateTable(columnName);
        });
    };

    _this.updateTable = function(columnName) {
        $.ajax({
            type: 'POST',
            url: './getDuplicated',
            data: {
                columnName: columnName
            },
            timeout: 10000
        }).done(function(result) {
            _this.updatePagination(result);
        });
    };

    _this.updatePagination = function(duplicatedApplicant) {
        $('.table-pagination').pagination({
            className: 'paginationjs-theme-blue paginationjs-big',
            dataSource: duplicatedApplicant,
            pageSize: 10,
            autoHidePrevious: true,
            autoHideNext: true,
            callback: function(pageData, pagination) {
                _this.renderTable(pageData);
            }
        });
    };

    _this.renderTable = function(pageData) {
        var $table = $('.duplicated-table');
        $table.find('.tableColumnName').text(_this.getCurrentColumnName());

        var $tableBody = $table.find('tbody');
        $tableBody.empty();

        pageData.forEach(function(data) {
            var $tr = $('<tr></tr>');
            $tr.append($('<td>').text(data.columnName));
            $tr.append($('<td class="duplicated-count">').text(data.duplicatedCount + '개'));

            $tableBody.append($tr);
        });
    };

    _this.getCurrentColumnId = function() {
        return $('.columnNameBtnGroup .active input').attr('id');
    };

    _this.getCurrentColumnName = function() {
        return $('.columnNameBtnGroup .active input').attr('name');
    };

    return {
        init: function() {
            _this.init();
        }
    };
};