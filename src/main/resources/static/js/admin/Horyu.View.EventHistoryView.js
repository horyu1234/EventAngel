"use strict";

var Horyu = window.Horyu || {};
Horyu.View = window.Horyu.View || {};
Horyu.View.EventHistoryView = function(options) {
    var _this = this;

    var defaultOptions = {
        prizeList: []
    };

    _this.options = $.extend(defaultOptions, options);

    _this.init = function() {
        _this.updateTable();
    };

    _this.updateTable = function() {
        _this.nextLotteryPrize = null;

        var $prizeTableBody = $('#prize-table tbody');
        $prizeTableBody.empty();

        var groupedPrizeList = _this.options.prizeList.groupBy('companyId');
        Object.forEach(groupedPrizeList, function(prizeList) {
            prizeList = prizeList.sortBy('prizeId');

            var prizeIndex = 0;
            Object.forEach(prizeList, function(prize) {
                var $tr = $('<tr>');

                if (prizeIndex === 0) {
                    var $companyLogoTr = $('<td class="company-cell" rowspan="' + prizeList.length + '">' +
                        '<a style="font-size: 16pt;font-weight: bold;">' + prize.companyName + '</a>' +
                        '<br/>' +
                        '<a style="color: gray">' + prize.companyDetail + '</a>' +
                        '</td>');
                    $tr.append($companyLogoTr);
                }

                $tr.append($('<td>').text(prize.prizeName));

                if (prize.applicant === null) {
                    $tr.append($('<td>').html('<a style="color:gray">추첨 대기 중</a>'));
                    $tr.append($('<td>').html('<a style="color:gray">추첨 대기 중</a>'));
                    $tr.append($('<td>').text('추첨 대기 중'));
                    $tr.append($('<td>').text('추첨 대기 중'));

                    if (_this.nextLotteryPrize === null) {
                        _this.nextLotteryPrize = prize;
                    }
                } else {
                    $tr.append($('<td>').html('<a style="color:blue;font-weight: bold">' + prize.applicant.email + '</a>'));
                    $tr.append($('<td>').html('<a style="color:blue;font-weight: bold">' + prize.applicant.youtubeNickname + '</a>'));
                    $tr.append($('<td>').text(prize.applicant.ipAddress));
                    $tr.append($('<td>').text(prize.applicant.fingerprint2));
                }

                $prizeTableBody.append($tr);
                prizeIndex++;
            });
        });
    };

    return {
        init: function() {
            _this.init();
        }
    };
};