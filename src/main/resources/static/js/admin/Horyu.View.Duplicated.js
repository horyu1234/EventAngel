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
        $('#loading-modal').modal({
            backdrop: 'static',
            keyboard: false
        });

        $.ajax({
            type: 'POST',
            url: './getDuplicated',
            data: {
                columnName: columnName
            },
            timeout: 10000
        }).done(function(result) {
            $('#loading-modal').modal('hide');

            if (!Array.isArray(result)) {
                $('#fail-modal').modal({
                    backdrop: 'static',
                    keyboard: false
                });

                setTimeout(function() {
                    location.reload();
                }, 4000);
                return;
            }

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
            $tr.append($('<td>').html('<a target="_blank" href="/admin/applicantList/?columnId=' + _this.getCurrentColumnId() + '&columnName=' + data.columnName + '">' + data.columnName + '</a>'));
            $tr.append($('<td class="duplicated-count">').text(data.duplicatedCount + '개'));

            if (_this.getCurrentColumnId() === 'IP_ADDRESS') {
                $tr.append($('<td class="remarks">').text(_this.getCarrierInformation(data.columnName)));
            } else {
                $tr.append($('<td class="remarks">').text(''));
            }

            $tableBody.append($tr);
        });
    };

    _this.getCarrierInformation = function(ip) {
        var SKT_3G = ['203.226', '211.234'];
        var SKT_LTE = ['115.161', '121.190', '122.202', '122.32', '175.202', '223.32', '223.33', '223.62', '223.38', '223.39', '223.57'];
        var KT_3G = ['39.7', '110.70', '175.223', '175.252', '211.246'];
        var KT_LTE = ['39.7', '110.70', '175.223', '175.252', '210.125', '211.246'];
        var LG_UPLUS_3G = ['61.43', '211.234'];
        var LG_UPLUS_LTE = ['114.200', '117.111', '211.36', '106.102', '125.188'];

        if (_this.containsIpRange(SKT_3G, ip)) {
            return '통신사 아이피 (SKT 3G)';
        } else if (_this.containsIpRange(SKT_LTE, ip)) {
            return '통신사 아이피 (SKT LTE)';
        } else if (_this.containsIpRange(KT_3G, ip)) {
            return '통신사 아이피 (KT 3G/LTE)';
        } else if (_this.containsIpRange(KT_LTE, ip)) {
            return '통신사 아이피 (LTE)';
        } else if (_this.containsIpRange(LG_UPLUS_3G, ip)) {
            return '통신사 아이피 (LG U+ 3G)';
        } else if (_this.containsIpRange(LG_UPLUS_LTE, ip)) {
            return '통신사 아이피 (LG U+ LTE)';
        } else {
            return '';
        }
    };

    _this.containsIpRange = function(ipRange, searchIp) {
        var hasIpRange = false;
        ipRange.forEach(function(ip) {
            if (searchIp.startsWith(ip)) {
                hasIpRange = true;
            }
        });

        return hasIpRange;
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