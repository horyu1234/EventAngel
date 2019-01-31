"use strict";

var Horyu = window.Horyu || {};
Horyu.View = window.Horyu.View || {};
Horyu.View.Apply = function(options) {
    var _this = this;
    _this.companyGiftTable = null;

    var defaultOptions = {
        applyCount: 0,
        eventStatus: 'CLOSE',
        eventStartTime: 0,
        eventEndTime: 0,
        companyGiftData: [],
        eventWinnerData: []
    };

    _this.options = $.extend(defaultOptions, options);

    _this.init = function() {
        _this.initFingerprint2();
        _this.initParsley();
        _this.updateAlertBanner();
        _this.bindEvents();
    };

    _this.initFingerprint2 = function() {
        var fp = new Fingerprint2({
            excludeWebGL: true
        });

        fp.get(function(result) {
            $('#fingerprint2').val(result);
        });
    };

    _this.initParsley = function() {
        $("#applyForm").parsley({
            successClass: "is-valid",
            errorClass: "is-invalid",
            classHandler: function(element) {
                return element.$element.closest("input");
            }
        });
    };

    _this.updateAlertBanner = function() {
        var $alertBanner = $('.alert-banner');
        if (_this.options.eventStatus === 'START_SOON') {
            $alertBanner.html('<a style="font-weight: bold; font-size: 20pt;">다음 시간 이후에 응모가 가능합니다.</a>' +
                '<br/>' +
                '<a id="time-countdown" style="font-weight: bold; font-size: 32pt;"></a>');

            $('#time-countdown').countdown(moment.unix(_this.options.eventStartTime).toDate())
                .on('update.countdown', function(event) {
                    $(this).html(event.strftime('%D일 %H시간 %M분 %S초'));
                })
                .on('finish.countdown', function(event) {
                    $('.alert-banner').html('<a style="font-weight: bold; font-size: 20pt;">페이지가 자동 새로고침됩니다.' +
                        '<br/>' +
                        '새로고침이 안될 경우, 직접 새로고침 버튼을 눌러주세요.</a>');
                    window.location.reload();
                });
        } else if (_this.options.eventStatus === 'ALREADY_END') {
            var options = {
                useEasing: true,
                useGrouping: true,
                separator: ',',
                decimal: '.',
                suffix: '명'
            };

            var countUp = new CountUp('applyCount', 0, _this.options.applyCount, 0, 5, options);
            if (!countUp.error) {
                countUp.start();
            } else {
                console.error(countUp.error);
            }

            $alertBanner.html('<a style="font-weight: bold; font-size: 20pt;">다음 시간 전에 이미 응모 신청이 마감되었습니다.</a>' +
                '<br/>' +
                '<a id="time-countdown" style="font-weight: bold; font-size: 32pt;"></a>');

            var endTime = moment.unix(_this.options.eventEndTime);
            var subtractedTime = endTime.subtract(new Date());

            $('#time-countdown').countdown(subtractedTime.toDate(), {
                elapse: true
            }).on('update.countdown', function(event) {
                $(this).html(event.strftime('%D일 %H시간 %M분 %S초'));
            })
        } else if (_this.options.eventStatus === 'OPEN') {
            $alertBanner.html('<a style="font-size: 12pt;"><a id="time-countdown" style="font-weight: bold;"></a> 이후 응모 신청이 마감됩니다.</a>');

            var options = {
                useEasing: true,
                useGrouping: true,
                separator: ',',
                decimal: '.',
                suffix: '명'
            };

            var countUp = new CountUp('applyCount', 0, _this.options.applyCount, 0, 3, options);
            if (!countUp.error) {
                countUp.start();
            } else {
                console.error(countUp.error);
            }

            $('#time-countdown').countdown(moment.unix(_this.options.eventEndTime).toDate())
                .on('update.countdown', function(event) {
                    $(this).html(event.strftime('%D일 %H시간 %M분 %S초'));
                })
                .on('finish.countdown', function(event) {
                    $('.alert-banner').html('<a style="font-weight: bold; font-size: 20pt;">응모 신청이 마감되었습니다.</a>');
                    window.location.reload();
                });

            _this.companyGiftTable = new Horyu.View.CompanyGiftTable({
                includeEventWinner: false
            });
            _this.companyGiftTable.init();

            _this.companyGiftTable.updateTable(_this.options.companyGiftData, _this.options.eventWinnerData);
        } else if (_this.options.eventStatus === 'CLOSE') {
            $alertBanner.text('현재 응모 신청을 받지 않습니다.');
        } else if (_this.options.eventStatus === 'LOTTERY') {
            $alertBanner.html('아래에서 현재 이벤트 당첨자 목록을 확인하실 수 있습니다.');
            $alertBanner.removeClass('alert-warning');
            $alertBanner.addClass('alert-success');

            _this.companyGiftTable = new Horyu.View.CompanyGiftTable();
            _this.companyGiftTable.init();

            _this.companyGiftTable.updateTable(_this.options.companyGiftData, _this.options.eventWinnerData);
        }
    };

    _this.bindEvents = function() {
        $('.submitBtn').click(function() {
            var isFormValidate = $('#applyForm').parsley().validate();
            if (isFormValidate) {
                grecaptcha.execute();
            }
        });

        $('.notSubscriberBtn').click(function() {
            $('#submit-modal').modal();
        });
    };

    return {
        init: function() {
            _this.init();
        }
    };
};

function onReCaptchaCallback() {
    $('#applyForm').submit();
}