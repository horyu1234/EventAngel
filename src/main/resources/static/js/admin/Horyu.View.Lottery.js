"use strict";

var Horyu = window.Horyu || {};
Horyu.View = window.Horyu.View || {};
Horyu.View.Lottery = function(options) {
    var _this = this;
    _this.companyGiftTable = null;
    _this.currentEventWinnerData = [];

    var defaultOptions = {
        notDupApplyCount: 0,
        dupApplyCount: 0,
        companyGiftData: [],
        eventWinnerData: []
    };

    _this.options = $.extend(defaultOptions, options);

    _this.init = function() {
        _this.companyGiftTable = new Horyu.View.CompanyGiftTable();
        _this.companyGiftTable.init();

        _this.updateTable();
        _this.bindEvents();

        setInterval(function() {
            $('#currentTime').text(moment().format('YYYY년 MMMM D일 dddd A h시 mm분 ss초'));
        }, 500);

        var options = {
            useEasing: true,
            useGrouping: true,
            separator: ',',
            decimal: '.'
        };

        var notDupApplyCountUp = new CountUp('notDupApplyCount', 0, _this.options.notDupApplyCount, 0, 3, options);
        if (!notDupApplyCountUp.error) {
            notDupApplyCountUp.start();
        } else {
            console.error(notDupApplyCountUp.error);
        }

        var dupApplyCountUp = new CountUp('dupApplyCount', 0, _this.options.dupApplyCount, 0, 3, options);
        if (!dupApplyCountUp.error) {
            dupApplyCountUp.start();
        } else {
            console.error(dupApplyCountUp.error);
        }
    };

    _this.updateTable = function() {
        _this.companyGiftTable.updateTable(_this.options.companyGiftData, _this.options.eventWinnerData);

        var $nextGiftAlert = $('.next-gift-alert');
        var $nextGiftName = $('.next-gift-name');
        var $lotteryBtn = $('.lotteryBtn');

        var nextLotteryGift = _this.companyGiftTable.getNextLotteryGift();
        if (nextLotteryGift === null) {
            $nextGiftAlert.remove();

            $lotteryBtn.attr('disabled', '');
            $lotteryBtn.html('<i class="fas fa-random"></i>&nbsp; [추첨 불가] 모든 상품 추첨이 완료되었습니다.')
        } else {
            $nextGiftName.text(nextLotteryGift.prizeName);
            $lotteryBtn.html('<i class="fas fa-random"></i>&nbsp; 다음 상품 추첨하기')
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
                    prizeId: _this.companyGiftTable.getNextLotteryGift().prizeId
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

        var nextLotteryGift = _this.companyGiftTable.getNextLotteryGift();

        $('#modal-companyName').text(nextLotteryGift.companyName);
        $('#modal-companyDetail').text(nextLotteryGift.companyDetail);
        $('#modal-prizeName').text(nextLotteryGift.prizeName);
        $('#modal-youtubeNickname').text(result.youtubeNickname);
        $('#modal-email').text(result.applyEmail);

        $('#lottery-modal').modal({
            backdrop: 'static',
            keyboard: false
        });
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