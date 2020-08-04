<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/jsp/part/head.jspf"%>

<!-- 마이페이지 진입 전 비밀번호 확인 각 값 체크 -->
<script>
	var LoginFormSubmitted = false;

	function submitLoginForm(form) {
		if (LoginFormSubmitted) {
			alert('처리 중입니다.');
			return;
		}

		form.loginId.value = form.loginId.value.trim();
		if (form.loginId.value.length == 0) {
			alert('아이디를 입력해주세요.');
			form.loginId.focus();

			return;
		}

		form.loginPw.value = form.loginPw.value.trim();
		if (form.loginPw.value.length == 0) {
			alert('비밀번호를 입력해주세요.');
			form.loginPw.focus();

			return;
		}
		
		form.loginPwReal.value = sha256(form.loginPw.value);
		form.loginPw.value = '';

		form.submit();
		LoginFormSubmitted = true;
	}
</script>

<!-- 마이페이지 진입 전 비밀번호 확인 -->
<h1 class="certification-h">비밀번호 확인</h1>

<div class="certification-form-box con flex-jc-c">
	<form action="DoPasswordConfirm" method="POST" class="certification-form form6" onsubmit="submitLoginForm(this); return false;">
		<div class="form-row">
			<div class="label">아이디</div>
			<div class="input">
				<input type="hidden" name="loginId" maxlength="20"
					value="${loginedMember.loginId}">${loginedMember.loginId}
			</div>
		</div>
		<input type="hidden" name="loginPwReal">
		<div class="form-row">
			<div class="label">비밀번호</div>
			<div class="input">
				<input type="password" name="loginPw" maxlength="20"
					placeholder="password 입력">
			</div>
		</div>
		<div class="form-row">
			<div class="input flex-jc-c">
				<button type="submit" value="확인">확인</button>
			</div>
		</div>
	</form>
</div>

<%@ include file="/jsp/part/foot.jspf"%>