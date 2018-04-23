"use strict";

var Horyu = window.Horyu || {};
Horyu.View = window.Horyu.View || {};
Horyu.View.CompanySetting = function() {
    var _this = this;
    _this.deletingCompanyId = 0;

    _this.init = function() {
        _this.bindEvents();
    };

    _this.bindEvents = function() {
        $('.companyEditBtn').click(function(event) {
            _this.startCompanyEdit($(event.target));
        });

        $('.companyDeleteBtn').click(function(event) {
            _this.startDelete($(event.target));
        });

        $('#cancelEditBtn').click(function() {
            _this.abortCompanyEdit();
        });

        $('#deleteAbortBtn').click(function() {
            _this.abortDelete();
        });

        $('#deleteBtn').click(function() {
            _this.deleteConfirm();
        });
    };

    _this.startCompanyEdit = function(editBtn) {
        var $trGroup = editBtn.closest('tr');

        $('#companyId').val($trGroup.find('.company-id').text());
        $('#companyName').val($trGroup.find('.company-name').text());
        $('#companyDetail').val($trGroup.find('.company-detail').text());

        $('.card-header').text('도움을 주신 회사 수정');
        $('#submitBtn').text('회사 수정');
        $('#cancelEditBtn').css('visibility', 'visible');

        $('html, body').animate({scrollTop: 0}, 'fast');
    };

    _this.abortCompanyEdit = function() {
        $('#companyId').val('0');
        $('#companyName').val('');
        $('#companyDetail').val('');

        $('.card-header').text('도움을 주신 회사 추가');
        $('#submitBtn').text('회사 추가');
        $('#cancelEditBtn').css('visibility', 'hidden');
    };

    _this.startDelete = function(deleteBtn) {
        var $trGroup = deleteBtn.closest('tr');

        _this.deletingCompanyId = Number($trGroup.find('.company-id').text());

        $('#delete-modal').modal({
            backdrop: 'static',
            keyboard: false
        });
    };

    _this.abortDelete = function() {
        _this.deletingCompanyId = 0;
    };

    _this.deleteConfirm = function() {
        $('#deleteBtn').html('처리 중입니다.');
        $('#deleteBtn').attr('disabled', '');
        $('#deleteAbortBtn').css('visibility', 'hidden');

        $.ajax({
            type: 'POST',
            url: './deleteCompany',
            data: {
                companyId: _this.deletingCompanyId
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

    return {
        init: function() {
            _this.init();
        }
    };
};