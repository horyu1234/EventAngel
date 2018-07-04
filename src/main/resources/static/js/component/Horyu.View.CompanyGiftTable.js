"use strict";

var Horyu = window.Horyu || {};
Horyu.View = window.Horyu.View || {};
Horyu.View.CompanyGiftTable = function(options) {
    var $table = $('#company-gift-table');
    var $tableBody = $table.find('tbody');
    var $tableHead = $table.find('thead');

    var _this = this;
    _this.companyGiftData = [];
    _this.currentEventWinnerData = [];
    _this.nextLotteryGift = null;

    var defaultOptions = {
        includeEventWinner: true
    };

    _this.options = $.extend(defaultOptions, options);

    _this.init = function() {
        _this.updateTableHeaders();
    };

    _this.updateTableHeaders = function() {
        var $tableHeadTr = $tableHead.find('tr');
        $tableHeadTr.append('<th>도움을 주신 회사</th>');
        $tableHeadTr.append('<th>상품</th>');

        if (_this.options.includeEventWinner) {
            $tableHeadTr.append('<th width="250px">당첨자 이메일</th>');
            $tableHeadTr.append('<th width="250px">당첨자 유튜브 닉네임</th>');
        } else {
            $tableHeadTr.append('<th width="100px">상품 개수</th>');
        }
    };

    _this.updateTable = function() {
        _this.nextLotteryGift = null;

        $tableBody.empty();

        var giftDataGroupedByCompanyId = _this.companyGiftData.groupBy('companyId');
        Object.keys(giftDataGroupedByCompanyId).forEach(function(companyId) {
            var giftList = giftDataGroupedByCompanyId[companyId];
            giftList = giftList.sortBy('prizeId');

            var totalGiftIndex = 0;
            giftList.forEach(function(gift) {
                var giftAmount = gift.prizeAmount;

                if (_this.options.includeEventWinner) {
                    for (var giftIndex = 0; giftIndex < giftAmount; giftIndex++) {
                        var $tr = $('<tr></tr>');

                        if (totalGiftIndex === 0) {
                            $tr.append(_this.makeCompanyCell(_this.getTotalGiftAmount(companyId), gift));
                        }

                        if (giftIndex === 0) {
                            $tr.append($('<td class="gift-cell" rowspan="' + giftAmount + '">' + gift.prizeName + '</td>'));
                        }

                        var eventWinner = _this.popEventWinner(gift.prizeId);
                        if (eventWinner === null) {
                            if (_this.nextLotteryGift === null) {
                                _this.nextLotteryGift = gift;
                            }

                            $tr.append($('<td>').html('<a class="event-winner-pending-cell">추첨을 기다리는 중...</a>'));
                            $tr.append($('<td>').html('<a class="event-winner-pending-cell">추첨을 기다리는 중...</a>'));
                        } else {
                            $tr.append($('<td>').html('<a class="event-winner-cell">' + eventWinner.applicant.applyEmail + '</a>'));
                            $tr.append($('<td>').html('<a class="event-winner-cell">' + eventWinner.applicant.youtubeNickname + '</a>'));
                        }

                        $tableBody.append($tr);

                        totalGiftIndex++;
                    }
                } else {
                    var $tr = $('<tr></tr>');

                    if (totalGiftIndex === 0) {
                        $tr.append(_this.makeCompanyCell(_this.getTotalGiftSize(companyId), gift));
                    }

                    $tr.append($('<td>').text(gift.prizeName));
                    $tr.append($('<td>').text(giftAmount + '개'));

                    $tableBody.append($tr);

                    totalGiftIndex++;
                }
            });
        });
    };

    _this.makeCompanyCell = function(rowspan, gift) {
        var $td = $('<td class="company-cell">').attr('rowspan', rowspan);

        var $companyLogo = $('<img class="company-logo" src="/companyLogo?companyId=' + gift.companyId + '"/>');
        var $companyName = $('<a style="font-size: 16pt;font-weight: bold;">' + gift.companyName + '</a>');
        var $companyDetail = $('<a style="color: gray">' + gift.companyDetail + '</a>');

        if (gift.companyLogoImageFileName !== null) {
            $td.append($companyLogo);
        } else {
            $td.append($companyName);
        }

        $td.append('<br/>');
        $td.append($companyDetail);

        return $td;
    };

    _this.popEventWinner = function(prizeId) {
        var eventWinnerDataFilteredByPrizeId = _this.currentEventWinnerData.filter(function(eventWinner) {
            return eventWinner.prizeId === prizeId;
        });

        eventWinnerDataFilteredByPrizeId = eventWinnerDataFilteredByPrizeId.sortBy(function(eventWinner) {
            return moment(eventWinner.winTime).unix();
        });

        if (eventWinnerDataFilteredByPrizeId.length === 0) {
            return null;
        }

        var targetEventWinner = eventWinnerDataFilteredByPrizeId[0];
        _this.currentEventWinnerData.remove(function(eventWinner) {
            return eventWinner.applicant.applyEmail === targetEventWinner.applicant.applyEmail;
        });

        return targetEventWinner;
    };

    _this.getTotalGiftSize = function(companyId) {
        var totalGiftAmount = 0;

        var giftDataFilteredByCompanyId = _this.companyGiftData.filter(function(companyGift) {
            return companyGift.companyId === Number(companyId);
        });

        giftDataFilteredByCompanyId.forEach(function(gift) {
            totalGiftAmount++;
        });

        return totalGiftAmount;
    };

    _this.getTotalGiftAmount = function(companyId) {
        var totalGiftAmount = 0;

        var giftDataFilteredByCompanyId = _this.companyGiftData.filter(function(companyGift) {
            return companyGift.companyId === Number(companyId);
        });

        giftDataFilteredByCompanyId.forEach(function(gift) {
            totalGiftAmount += gift.prizeAmount;
        });

        return totalGiftAmount;
    };

    _this.deepCopy = function(data) {
        return JSON.parse(JSON.stringify(data));
    };

    return {
        init: _this.init,
        updateTable: function(companyGiftData, eventWinnerData) {
            _this.companyGiftData = _this.deepCopy(companyGiftData);
            _this.currentEventWinnerData = _this.deepCopy(eventWinnerData);

            _this.updateTable();
        },
        getNextLotteryGift: function() {
            return _this.nextLotteryGift;
        }
    };
};