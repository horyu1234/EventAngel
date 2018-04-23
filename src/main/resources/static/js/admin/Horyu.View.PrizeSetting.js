"use strict";

var Horyu = window.Horyu || {};
Horyu.View = window.Horyu.View || {};
Horyu.View.PrizeSetting = function(options) {
    var _this = this;
    _this.deletingPrizeId = 0;

    var defaultOptions = {
        prizeList: []
    };

    _this.options = $.extend(defaultOptions, options);

    _this.init = function() {
        _this.updateTable();
        _this.bindEvents();
    };

    _this.bindEvents = function() {
        $('.prizeEditBtn').click(function(event) {
            _this.startPrizeEdit($(event.target));
        });

        $('.prizeDeleteBtn').click(function(event) {
            _this.startDelete($(event.target));
        });

        $('#cancelEditBtn').click(function() {
            _this.abortPrizeEdit();
        });

        $('#deleteAbortBtn').click(function() {
            _this.abortDelete();
        });

        $('#deleteBtn').click(function() {
            _this.deleteConfirm();
        });
    };

    _this.startPrizeEdit = function(editBtn) {
        var $trGroup = editBtn.closest('tr');

        $('#prizeId').val($trGroup.find('.prize-name').attr('data-prize-id'));
        $('#companyId').val($trGroup.find('.prize-name').attr('data-company-id'));
        $('#prizeName').val($trGroup.find('.prize-name').text());
        $('#prizeAmount').val($trGroup.find('.prize-amount').text());

        $('.card-header').text('상품 수정');
        $('#submitBtn').text('상품 수정');
        $('#cancelEditBtn').css('visibility', 'visible');

        $('html, body').animate({scrollTop: 0}, 'fast');
    };

    _this.abortPrizeEdit = function() {
        $('#prizeId').val('0');
        $('#prizeName').val('');
        $('#prizeAmount').val('');

        $('.card-header').text('상품 추가');
        $('#submitBtn').text('상품 추가');
        $('#cancelEditBtn').css('visibility', 'hidden');
    };

    _this.startDelete = function(deleteBtn) {
        var $trGroup = deleteBtn.closest('tr');

        _this.deletingPrizeId = Number($trGroup.find('.prize-name').attr('data-prize-id'));

        $('#delete-modal').modal({
            backdrop: 'static',
            keyboard: false
        });
    };

    _this.abortDelete = function() {
        _this.deletingPrizeId = 0;
    };

    _this.deleteConfirm = function() {
        $('#deleteBtn').html('처리 중입니다.');
        $('#deleteBtn').attr('disabled', '');
        $('#deleteAbortBtn').css('visibility', 'hidden');

        $.ajax({
            type: 'POST',
            url: './deletePrize',
            data: {
                prizeId: _this.deletingPrizeId
            },
            timeout: 5000
        }).done(function() {
            $('#deleteBtn').html('완료되었습니다.');
            setTimeout(function() {
                location.reload();
            }, 500);
        }).fail(function(msg) {
            $('#deleteBtn').html('통신에 실패했습니다. 잠시 후 다시 시도해주세요.');
            setTimeout(function() {
                location.reload();
            }, 3000);
        });
    };

    _this.updateTable = function() {
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

                $tr.append($('<td class="prize-name">').attr('data-prize-id', prize.prizeId).attr('data-company-id', prize.companyId).text(prize.prizeName));
                $tr.append($('<td class="prize-amount">').text(prize.prizeAmount));
                $tr.append($('<td>' +
                    '<button type="button" class="btn btn-info btn-sm prizeEditBtn">수정</button>' +
                    '</td>'));
                $tr.append($('<td>' +
                    '<button type="button" class="btn btn-danger btn-sm prizeDeleteBtn">삭제</button>' +
                    '</td>'));

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