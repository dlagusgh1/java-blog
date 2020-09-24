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
	var ModifyPrivateFormSubmitted = false;
	
	function ModifyPrivateForm__submit(form) {
		if (ModifyPrivateFormSubmitted) {
			alert('처리 중입니다.');
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
			alert('비밀번호 확인을 입력해주세요.');
			form.loginPwConfirm.focus();

			return;
		}
		
		if (form.loginPwConfirm.value != form.loginPw.value ) {
			alert('로그인 비밀번호 확인이 일치하지 않습니다.');
			form.loginPwConfirm.focus();

			return;
		}

		<!-- 패스워드 함호화 -->
		form.loginPwReal.value = sha256(form.loginPw.value);
		form.loginPw.value = '';
		form.loginPwConfirm.value = form.loginPw.value;

		form.submit();
		ModifyPrivateFormSubmitted = true;
	}
</script>

<h1 class="title">회원 정보 수정</h1>

<form method="POST" class="table-box table-box-vertical con form1" action="doModifyPrivate" onsubmit="ModifyPrivateForm__submit(this); return false;">
<input type="hidden" name="authCode" value="${param.authCode}" />
	<table>
		<colgroup>
			<col width="250">
		</colgroup>
		<tbody>
			<tr>
				<th>새 비밀번호</th>
				<td>
					<div class="form-control-box">
						<input name="loginPw" type="password" maxlength="20" placeholder="새 로그인 비밀번호 입력" />
					</div>
				</td>
			</tr>
			<tr>
				<th>새 비밀번호 확인</th>
				<td>
					<div class="form-control-box">
						<input name="loginPwConfirm" type="password" maxlength="20" placeholder="새 로그인 비밀번호 확인 입력" />
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