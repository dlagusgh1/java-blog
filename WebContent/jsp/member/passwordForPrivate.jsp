<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<c:set var="pageTitle" value="비밀번호 확인"></c:set>
<%@ include file="/jsp/part/head.jspf"%>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<style>
/* cus */
.password-form-box {
	margin-top: 30px;
}
</style>

<script>
	function submitLoginForm(form) {
		form.loginPw.value = form.loginPw.value.trim();

		if (form.loginPw.value.length == 0) {
			alert('로그인 비번을 입력해주세요.');
			form.loginPw.focus();
			return;
		}

		form.loginPwReal.value = sha256(form.loginPw.value);
		form.loginPw.value = '';

		form.submit();
	}
</script>

<!-- 비밀번호 수정 전 비밀번호 확인 -->
<h1 class="certification-h">비밀번호 확인</h1>

<div class="certification-form-box con flex-jc-c">
	<form action="doPasswordForPrivate" method="POST" class="certification-form form6" onsubmit="submitLoginForm(this); return false;">
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