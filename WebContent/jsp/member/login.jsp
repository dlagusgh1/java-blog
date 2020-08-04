<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/jsp/part/head.jspf"%>

<!-- 로그인 각 값 체크 -->
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

<h1 class="login-h">로그인</h1>

<div class="login-form-box con flex-jc-c">
	<form action="doLogin" method="POST" class="join-form form2"
		onsubmit="submitLoginForm(this); return false;">
		<input type="hidden" name="redirectUrl" value="${param.afterLoginRedirectUrl}" />
		<div class="form-row">
			<div class="label">아이디</div>
			<div class="input">
				<input type="text" name="loginId" maxlength="20"
					placeholder="회원 id 입력">
			</div>
		</div>
		<div class="form-row">
			<div class="label">비밀번호</div>
			<div class="input">
				<input type="password" name="loginPw" maxlength="20"
					placeholder="password 입력">
			</div>
		</div>
		<div class="form-row">
			<div class="input flex-jc-c">
				<input type="hidden" name="loginPwReal">
				<button type="submit" value="로그인">로그인</button>
			</div>
		</div>
		<div class="form-row flex-jc-c">
			<a href="${pageContext.request.contextPath}/s/member/findAccount">아이디 / 비밀번호 찾기</a>
		</div>
	</form>
</div>

<%@ include file="/jsp/part/foot.jspf"%>