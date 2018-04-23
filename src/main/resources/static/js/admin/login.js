function recaptcha_callback(response) {
    $('.submitBtn').attr('disabled', null);
    $('.submitBtn').text('로그인');

    $('#recaptchaResponse').val(response);
}

function recaptcha_expired_callback() {
    $('.submitBtn').attr('disabled', '');
    $('.submitBtn').text('상단의 "로봇이 아닙니다" 를 클릭해주세요.');

    $('#recaptchaResponse').val('');
}

$('.loginBtn').click(function() {
    $('.loginBtn').attr('disabled', '');
    $('.loginBtn').html('<i class="fas fa-spinner fa-spin"></i> 비밀번호를 암호화하는 중입니다...');

    $('#loginForm').submit();
});