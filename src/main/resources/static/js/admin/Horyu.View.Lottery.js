"use strict";

var Horyu = window.Horyu || {};
Horyu.View = window.Horyu.View || {};
Horyu.View.Lottery = function(options) {
    var _this = this;
    _this.nextLotteryPrize = null;

    var defaultOptions = {
        prizeList: []
    };

    _this.options = $.extend(defaultOptions, options);

    _this.init = function() {
        _this.updateTable();
        _this.bindEvents();

        setInterval(function() {
            $('#currentTime').text(moment().format('YYYY년 MMMM D일 dddd A h시 mm분 ss초'));
        }, 500);

        var options = {
            useEasing: true,
            useGrouping: true,
            separator: ',',
            decimal: '.',
            prefix: '총 ',
            suffix: '명'
        };

        var countUp = new CountUp('applyCount', 0, _this.options.applyCount, 0, 3, options);
        if (!countUp.error) {
            countUp.start();
        } else {
            console.error(countUp.error);
        }
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

                    if (_this.nextLotteryPrize === null) {
                        _this.nextLotteryPrize = prize;
                    }
                } else {
                    $tr.append($('<td>').html('<a style="color:blue;font-weight: bold">' + _this.hideSomePartOfEmail(prize.applicant.email) + '</a>'));
                    $tr.append($('<td>').html('<a style="color:blue;font-weight: bold">' + prize.applicant.youtubeNickname + '</a>'));
                }

                $prizeTableBody.append($tr);
                prizeIndex++;
            });
        });

        if (_this.nextLotteryPrize === null) {
            $('.lotteryBtn').attr('disabled', '');
            $('.lotteryBtn').html('<i class="fas fa-random"></i>&nbsp; [추첨 불가] 모든 상품 추첨이 완료되었습니다.')
        } else {
            $('.lotteryBtn').html('<i class="fas fa-random"></i>&nbsp; 다음 상품 (' + _this.nextLotteryPrize.companyName + ' - ' + _this.nextLotteryPrize.prizeName + ') 추첨하기')
        }
    };

    _this.bindEvents = function() {
        $('.lotteryBtn').click(function() {
            if (_this.isIE()) {
                $('#ie-modal').modal({
                    backdrop: 'static',
                    keyboard: false
                });
                return;
            }

            $('.lotteryBtn').attr('disabled', '');
            $('.lotteryBtn').html('<i class="fas fa-spinner fa-pulse"></i>&nbsp; 추첨 중입니다...');

            var startTime = moment();
            $.ajax({
                type: 'POST',
                url: './lottery',
                data: {
                    prizeId: _this.nextLotteryPrize.prizeId
                },
                timeout: 120000
            }).done(function(result) {
                var endTime = moment();
                var duration = moment.duration(endTime.diff(startTime));

                if (duration.valueOf() >= 5000) {
                    _this.lotteryOut(result);
                } else {
                    var randomTime = Math.round(Math.random() * (6000 - 3000)) + 3000;
                    setTimeout(function() {
                        _this.lotteryOut(result);
                    }, randomTime);
                }
            }).fail(function(msg) {
                $('.lotteryBtn').html('<i class="fas fa-exclamation-triangle"></i>&nbsp; 추첨에 실패하였습니다.');
                $('.lotteryBtn').removeClass('btn-success');
                $('.lotteryBtn').addClass('btn-danger');

                setTimeout(function() {
                    location.reload();
                }, 4000);
            });
        });
    };

    _this.lotteryOut = function(result) {
        $('.lotteryBtn').html('열린 창을 확인해주세요.');

        $('#modal-companyName').text(_this.nextLotteryPrize.companyName);
        $('#modal-companyDetail').text(_this.nextLotteryPrize.companyDetail);
        $('#modal-prizeName').text(_this.nextLotteryPrize.prizeName);
        $('#modal-youtubeNickname').text(result.youtubeNickname);
        $('#modal-email').text(_this.hideSomePartOfEmail(result.email));

        $('#lottery-modal').modal({
            backdrop: 'static',
            keyboard: false
        });
    };

    _this.hideSomePartOfEmail = function(email) {
        var emailName = email.split('@')[0].replace(/$/g, '*');
        var emailHost = email.split('@')[1].replace(/[a-zA-Z가-힣]/g, '*');

        return emailName + '@' + emailHost;
    };

    _this.isIE = function() {
        var userAgent = window.navigator.userAgent.toLowerCase();
        var appName = navigator.appName.toLowerCase();

        if ((appName === 'netscape' && userAgent.search('trident') !== -1) || (userAgent.indexOf("msie") !== -1)) {
            return true;
        }

        return false;
    };

    return {
        init: function() {
            _this.init();
        }
    };
};