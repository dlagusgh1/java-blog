<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<c:set var="pageTitle" value="개인정보 수정"></c:set>
<%@ include file="/jsp/part/head.jspf"%>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<style>
/* cus */
.modify-private-form-box {
	margin-top: 30px;
}
</style>

<script>
	function ModifyPrivateForm__submit(form) {
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

<h1 class="member-modify-h">회원 정보 수정</h1>

<div class="modify-private-form-box con flex-jc-c">
	<form action="doModifyPrivate" method="POST" class="member-modify-form form8" onsubmit="ModifyPrivateForm__submit(this); return false;">
		<input type="hidden" name="authCode" value="${param.authCode}" />
		<input type="hidden" name="loginPwReal" />
		<div class="form-row">
			<div class="label">비밀번호</div>
			<div class="input">
				<input name="loginPw" type="password" maxlength="20" placeholder="새 로그인 비번 입력" />
			</div>
		</div>
		<div class="form-row">
			<div class="label">비밀번호 확인</div>
			<div class="input">
				<input name="loginPwConfirm" type="password" maxlength="20" placeholder="새 로그인 비번 확인 입력" />
			</div>
		</div>
		<div class="form-row">
			<div class="input flex-jc-c">
				<button type="submit" value="수정">수정</button>
				<button type="button" value="취소" onClick="alert('회원정보 수정이 취소되었습니다.')">
					<a href="/blog/s/home/main">취소</a>
				</button>
			</div>
		</div>
	</form>
</div>

<%@ include file="/jsp/part/foot.jspf"%>