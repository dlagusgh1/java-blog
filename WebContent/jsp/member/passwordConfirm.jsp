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
<h1 class="title">비밀번호 확인</h1>

<form method="POST" class="table-box table-box-vertical con form1" action="doPasswordConfirm" onsubmit="submitLoginForm(this); return false;">
<input type="hidden" name="redirectUri" value="/blog/s/${Util.currentUri}">
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