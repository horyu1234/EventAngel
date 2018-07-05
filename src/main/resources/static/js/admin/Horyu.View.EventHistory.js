"use strict";

var Horyu = window.Horyu || {};
Horyu.View = window.Horyu.View || {};
Horyu.View.EventHistory = function(options) {
    var _this = this;

    var defaultOptions = {
        eventList: [],
        applicantCountMap: {}
    };

    _this.options = $.extend(defaultOptions, options);

    _this.init = function() {
        _this.changeMainContainerWidth();
        _this.updateTable();
        _this.bindEvents();
    };

    _this.changeMainContainerWidth = function() {
        var $mainContainer = $('#main-container');
        $mainContainer.removeClass('container');
        $mainContainer.addClass('container-full');
    };

    _this.updateTable = function() {
        var $table = $('.event-history-table');

        var $tableBody = $table.find('tbody');
        $tableBody.empty();

        _this.options.eventList.forEach(function(event) {
            var $tr = $('<tr></tr>');
            $tr.append($('<td>').text(event.eventId));
            $tr.append($('<td>').text(event.eventTitle));
            $tr.append($('<td>').text(event.eventDetail));
            $tr.append($('<td>').text(_this.getEventStatusText(event.eventStatus)));
            $tr.append($('<td>').html(moment(event.eventStartTime).format('YYYY년 MMMM D일 dddd<br/>A h시 mm분 ss초')));
            $tr.append($('<td>').html(moment(event.eventEndTime).format('YYYY년 MMMM D일 dddd<br/>A h시 mm분 ss초')));

            var applicantCount = _this.options.applicantCountMap[event.eventId];
            if (event.eventId > 5) {
                $tr.append($('<td>').text(applicantCount + '개'));
            } else {
                $tr.append($('<td>').text('-'));
            }

            var $eventDetailBtn = $('<button type="button" class="btn btn-success btn-sm viewEventBtn"></button>')
                .text('이벤트 결과 확인')
                .attr('data-event-id', event.eventId);

            $tr.append($('<td>').append($eventDetailBtn));

            $tableBody.append($tr);
        });
    };

    _this.getEventStatusText = function(eventStatus) {
        if (eventStatus === 'OPEN') {
            return '응모 중';
        } else if (eventStatus === 'CLOSE') {
            return '응모 닫힘';
        } else if (eventStatus === 'LOTTERY') {
            return '추첨 진행';
        } else {
            return eventStatus;
        }
    };

    _this.bindEvents = function() {
        $('.viewEventBtn').click(function(event) {
            var eventId = $(event.target).attr('data-event-id');
            location.href = './view/' + eventId;
        });
    };

    return {
        init: function() {
            _this.init();
        }
    };
};