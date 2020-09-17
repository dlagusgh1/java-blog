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
<h1 class="title">비밀번호 확인</h1>

<form method="POST" class="table-box table-box-vertical con form1" action="doPasswordForPrivate" onsubmit="submitLoginForm(this); return false;">
<input type="hidden" name="redirectUri" value="/blog/s/${param.currentUri}">
	<table>
		<colgroup>
			<col width="250">
		</colgroup>
		<tbody>
			<tr>
				<th>아이디</th>
				<td>
					<div class="form-control-box">
						<input type="hidden" name="loginId" maxlength="20" value="${loginedMember.loginId}">${loginedMember.loginId}
					</div>
				</td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td>
					<div class="form-control-box">
						<input type="password" name="loginPw" maxlength="20" placeholder="비밀번호 입력">
					</div>
				</td>
			</tr>
			<tr>
				<th>비밀번호 확인</th>
				<td>
					<input type="hidden" name="loginPwReal">
					<button class="btn btn-primary" type="submit">확인</button>
					<button class="btn btn-danger" type="button" onclick="history.back();">취소</button>
				</td>
			</tr>
		</tbody>
	</table>
</form>

<%@ include file="/jsp/part/foot.jspf"%>