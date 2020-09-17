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

<h1 class="title">로그인</h1>

<form method="POST" class="table-box table-box-vertical con form1" action="doLogin" onsubmit="submitLoginForm(this); return false;">
	<input type="hidden" name="redirectUrl" value="${param.afterLoginRedirectUrl}" />
	<table>
		<colgroup>
			<col width="250">
		</colgroup>
		<tbody>
			<tr>
				<th>아이디</th>
				<td>
					<div class="form-control-box">
						<input type="text" placeholder="로그인 아이디 입력해주세요." name="loginId" maxlength="30" autofocus="autofocus"/>
					</div>
				</td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td>
					<div class="form-control-box">
						<input type="password" placeholder="비밀번호를 입력해주세요." name="loginPw" maxlength="30" />
						<input type="hidden" name="loginPwReal">
					</div>
				</td>
			</tr>
			<tr>
				<th>로그인</th>
				<td>
					<div class="form-control-box">
						<button class="btn btn-primary" type="submit" value="로그인">로그인</button>
					</div>
				</td>
			</tr>
			<tr>
				<th>회원정보 찾기</th>
				<td>
					<button class="btn btn-primary" type="button"><a href="${pageContext.request.contextPath}/s/member/findAccount">아이디 / 비밀번호 찾기</a></button>
				</td>
			</tr>
			<tr>
				<td>
					<a href="${pageContext.request.contextPath}/s/member/join">아직 회원이 아니신가요? 회원가입</a>
				</td>
			</tr>
		</tbody>
	</table>
</form>


<%@ include file="/jsp/part/foot.jspf"%>