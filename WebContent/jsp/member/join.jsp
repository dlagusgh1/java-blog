<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/jsp/part/head.jspf"%>

<!-- 암호화 -->
<script	src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<!-- lodash 라이브러리 (debounce) -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.17.19/lodash.min.js"></script>

<h1 class="join-h">회원 가입</h1>

<div class="join-form-box con flex-jc-c">
	<form action="doJoin" method="POST" class="join-form form5" onsubmit="JoinForm__submit(this); return false;">
		<input type="hidden" name="loginPwReal">
		<div class="form-row">
			<div class="label">아이디</div>
			<div class="input">
				<input onkeyup="JoinForm__checkLoginIdDup(this);" type="text" name="loginId" maxlength="20" placeholder="회원 id 입력">
				<div class="message-msg"></div>
			</div>
		</div>
		<div class="form-row">
			<div class="label">비밀번호</div>
			<div class="input">
				<input type="password" name="loginPw" maxlength="20" placeholder="password 입력">
			</div>
		</div>
		<div class="form-row">
			<div class="label">비밀번호 확인</div>
			<div class="input">
				<input type="password" name="loginPwConfirm" maxlength="20" placeholder="password 확인 입력">
			</div>
		</div>
		<div class="form-row">
			<div class="label">이름</div>
			<div class="input">
				<input type="text" name="name" maxlength="20" placeholder="이름 입력">
			</div>
		</div>
		<div class="form-row">
			<div class="label">닉네임</div>
			<div class="input">
				<input onkeyup="JoinForm__checkNickNameDup(this);" type="text" name="nickName" maxlength="20" placeholder="닉네임 입력">
				<div class="message-msg"></div>
			</div>
		</div>
		<div class="form-row">
			<div class="label">이메일</div>
			<div class="input">
				<input onkeyup="JoinForm__checkEmailDup(this);" type="email" name="email" maxlength="20" placeholder="email 입력">
				<div class="message-msg"></div>
			</div>
		</div>
		<div class="form-row">
			<div class="input flex-jc-c">
				<button type="submit" value="완료">가입하기</button>
			</div>
		</div>
	</form>
</div>

<!-- 회원가입 각 값 체크 -->
<script>

	var JoinForm__validLoginId = '';
	var JoinForm__validEmail = '';
	var JoinForm__validNickName = '';

	var joinFormSubmitted = false;

	function JoinForm__submit(form) {
		if (joinFormSubmitted) {
			alert('처리 중입니다.');
			return;
		}

		form.loginId.value = form.loginId.value.trim();
		if (form.loginId.value.length == 0) {
			alert('아이디를 입력해주세요.');
			form.loginId.focus();

			return;
		}

		if (form.loginId.value != JoinForm__validLoginId) {
			alert('다른 아이디를 입력해주세요.');
			form.loginId.focus();
			return;
		}

		if (form.loginId.value.indexOf(' ') != -1) {
			alert('아이디를 영문소문자와 숫자의 조합으로 입력해주세요.')
			form.loginId.focus();

			return;
		}

		form.loginPw.value = form.loginPw.value.trim();
		if (form.loginPw.value.length == 0) {
			alert('비밀번호를 입력해주세요.');
			form.loginPw.focus();

			return;
		}

		if (form.loginPw.value.length <= 3) {
			alert('비밀번호를 4자 이상 입력해주세요.');
			form.loginPw.focus();

			return;
		}

		if (form.loginPw.value.indexOf(' ') != -1) {
			alert('비밀번호를 영문소문자와 숫자의 조합으로 입력해주세요.')
			form.loginPw.focus();

			return;
		}

		<!-- 입력된 비밀번호(loginPw)와 확인(loginPwConfirm) 일치하는지 체크 )-->
		form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
		if (form.loginPwConfirm.value.length == 0) {
			alert('비밀번호를 입력해주세요.');
			form.loginPw.focus();

			return;
		}
		
		if (form.loginPwConfirm.value != form.loginPw.value ) {
			alert('로그인 비밀번호 확인이 일치하지 않습니다.');
			form.loginPwConfirm.focus();

			return;
		}

		form.name.value = form.name.value.trim();
		if (form.name.value.length == 0) {
			alert('이름을 입력해주세요.');
			form.name.focus();

			return;
		}

		form.nickName.value = form.nickName.value.trim();
		if (form.nickName.value.length == 0) {
			alert('닉네임을 입력해주세요.');
			form.nickName.focus();

			return;
		}

		form.email.value = form.email.value.trim();
		if (form.email.value.length == 0) {
			alert('이메일을 입력해주세요.');
			form.email.focus();

			return;
		}
		
		<!-- 패스워드 함호화 -->
		form.loginPwReal.value = sha256(form.loginPw.value);
		form.loginPw.value = '';
		form.loginPwConfirm.value = form.loginPw.value;

		form.submit();
		joinFormSubmitted = true;
	}

	<!-- 회원가입 아이디 중복 체크 -->
	function JoinForm__checkLoginIdDup(input) {
		var form = input.form;

		form.loginId.value = form.loginId.value.trim();

		if (form.loginId.value.length == 0) {
			return;
		}

		$.get('getLoginIdDup', {
			loginId : form.loginId.value
		}, function(data) {
			var $message = $(form.loginId).next();

			// resultCode : 중복 체크 값 S- 중복 아님 / F- 중복
			if (data.resultCode.substr(0, 2) == 'S-') {
				$message.empty().append(
						'<div style="color:green;">' + data.msg + '</div>');
				JoinForm__validLoginId = data.loginId;
			} else {
				$message.empty().append(
						'<div style="color:red;">' + data.msg + '</div>');
				JoinForm__validLoginId = '';
			}
		}, 'json');
	}
	
	<!-- 회원가입 이메일 중복 체크 -->
	function JoinForm__checkEmailDup(input) {
		var form = input.form;

		form.email.value = form.email.value.trim();

		if (form.email.value.length == 0) {
			return;
		}

		$.get('getEmailDup', {
			email : form.email.value
		}, function(data) {
			var $message = $(form.email).next();

			// resultCode : 중복 체크 값 S- 중복 아님 / F- 중복
			if (data.resultCode.substr(0, 2) == 'S-') {
				$message.empty().append(
						'<div style="color:green;">' + data.msg + '</div>');
				JoinForm__validEmail = data.email;
			} else {
				$message.empty().append(
						'<div style="color:red;">' + data.msg + '</div>');
				JoinForm__validEmail = '';
			}
		}, 'json');
	}

	<!-- 회원가입 닉네임 중복 체크 -->
	function JoinForm__checkNickNameDup(input) {
		var form = input.form;

		form.nickName.value = form.nickName.value.trim();

		if (form.nickName.value.length == 0) {
			return;
		}

		$.get('getNickNameDup', {
			nickName : form.nickName.value
		}, function(data) {
			var $message = $(form.nickName).next();

			// resultCode : 중복 체크 값 S- 중복 아님 / F- 중복
			if (data.resultCode.substr(0, 2) == 'S-') {
				$message.empty().append(
						'<div style="color:green;">' + data.msg + '</div>');
				JoinForm__validNickName = data.nickName;
			} else {
				$message.empty().append(
						'<div style="color:red;">' + data.msg + '</div>');
				JoinForm__validNickName = '';
			}
		}, 'json');
	}

	<!-- lodash 라이브러리 (debounce) 를 이용한 딜레이 설정  -->
	JoinForm__checkLoginIdDup = _.debounce(JoinForm__checkLoginIdDup, 700);
	JoinForm__checkEmailDup = _.debounce(JoinForm__checkEmailDup, 700);
	JoinForm__checkNickNameDup = _.debounce(JoinForm__checkNickNameDup, 700);
	
</script>

<%@ include file="/jsp/part/foot.jspf"%>