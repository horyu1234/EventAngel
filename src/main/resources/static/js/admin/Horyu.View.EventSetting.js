"use strict";

var Horyu = window.Horyu || {};
Horyu.View = window.Horyu.View || {};
Horyu.View.EventSetting = function(options) {
    var _this = this;

    var defaultOptions = {
        eventStartTime: 0,
        eventEndTime: 0
    };

    _this.options = $.extend(defaultOptions, options);

    _this.init = function() {
        _this.initDateRangePicker();
        _this.bindEvents();

        _this.updateVisibleStatus(_this.getOpenEventBtnId() === 'OPEN');
    };

    _this.initDateRangePicker = function() {
        $('#eventTerm').daterangepicker({
            timePicker: true,
            timePickerIncrement: 1,
            timePickerSeconds: true,
            showDropdowns: true,
            todayHighlight: true,
            startDate: moment.unix(_this.options.eventStartTime),
            endDate: moment.unix(_this.options.eventEndTime),
            dateLimit: {
                year: 1
            },
            locale: {
                format: 'YYYY년 MM월 DD일 A h시 mm분 ss초',
                applyLabel: "적용",
                cancelLabel: "취소",
                daysOfWeek: [
                    "일", "월", "화", "수", "목", "금", "토"
                ],
                monthNames: [
                    "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"
                ]
            }
        });
    };

    _this.bindEvents = function() {
        $('.eventStatusButton').change(function() {
            _this.updateVisibleStatus($(this).attr('id') === 'OPEN');
        });

        $('.submitBtn').click(function() {
            var daterangepicker = $('#eventTerm').data('daterangepicker');

            $('#eventStatus').val(_this.getOpenEventBtnId());
            $('#eventStartTime').val(daterangepicker.startDate.unix());
            $('#eventEndTime').val(daterangepicker.endDate.unix());

            $('#eventSettingForm').submit();
        });

        $('.endEventBtn').click(function() {
            $('#end-event-modal').modal({
                backdrop: 'static',
                keyboard: false
            });
        });

        $('#endEventConfirmBtn').click(function() {
            $('#endEventConfirmBtn').html('처리 중입니다.');
            $('#endEventConfirmBtn').attr('disabled', '');
            $('.abortBtn').css('visibility', 'hidden');

            $.ajax({
                type: 'GET',
                url: './endEvent',
                timeout: 5000
            }).done(function() {
                $('#endEventConfirmBtn').html('완료되었습니다.');
                setTimeout(function() {
                    location.reload();
                }, 500);
            }).fail(function(msg) {
                $('#endEventConfirmBtn').html('통신에 실패했습니다. 잠시 후 다시 시도해주세요.');
                setTimeout(function() {
                    location.reload();
                }, 3000);
            });
        });

        $('.resetEventWinner').click(function() {
            $('#reset-event-winner-modal').modal({
                backdrop: 'static',
                keyboard: false
            });
        });

        $('#resetEventWinnerConfirmBtn').click(function() {
            $('#resetEventWinnerConfirmBtn').html('처리 중입니다.');
            $('#resetEventWinnerConfirmBtn').attr('disabled', '');
            $('.abortBtn').css('visibility', 'hidden');

            $.ajax({
                type: 'GET',
                url: './resetEventWinner',
                timeout: 5000
            }).done(function() {
                $('#resetEventWinnerConfirmBtn').html('완료되었습니다.');
                setTimeout(function() {
                    location.reload();
                }, 500);
            }).fail(function(msg) {
                $('#resetEventWinnerConfirmBtn').html('통신에 실패했습니다. 잠시 후 다시 시도해주세요.');
                setTimeout(function() {
                    location.reload();
                }, 3000);
            });
        });
    };

    _this.getOpenEventBtnId = function() {
        return $('.eventStatusBtnGroup .active input').attr('id');
    };

    _this.updateVisibleStatus = function(isActive) {
        if (isActive) {
            $('.eventOpenSettingGroup').show(500);
        } else {
            $('.eventOpenSettingGroup').hide(500);
        }
    };

    return {
        init: function() {
            _this.init();
        }
    };
};