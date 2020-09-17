<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/jsp/part/head.jspf"%>

<!-- 암호화 -->
<script	src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<!-- 회원 정보변경 전 각 값 체크 -->
<script>
	var joinFormSubmitted = false;

	function submitJoinForm(form) {
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

		if (form.loginId.value.length <= 3) {
			alert('아이디를 4자 이상 입력해주세요.');
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
		
		<!-- 패스워드 함호화 -->
		form.loginPwReal.value = sha256(form.loginPw.value);
		form.loginPw.value = '';

		form.submit();
		joinFormSubmitted = true;
	}
</script>

<h1 class="title">회원 탈퇴</h1>

<div class="member-delete-form-box con flex-jc-c">
	<form action="doDelete" method="POST" class="member-delete-form form9"
		onsubmit="submitJoinForm(this); return false;">
		<input type="hidden" name="loginPwReal">
		<div class="form-row">
			<div class="label">아이디</div>
			<div class="input">
				<input type="hidden" name="loginId" maxlength="20"
					value="${loginedMemberLoginId}">: ${loginedMemberLoginId}
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
			<div class="label">탈퇴합니다</div>
			<div class="input">
				<input type="text" name="memberDeleteConfirm" maxlength="20"
					placeholder="옆의 문구를 입력하세요.">
			</div>
		</div>
		<div class="form-row">
			<div class="input flex-jc-c">
				<button type="submit" value="탈퇴">탈퇴하기</button>
				<button type="button" value="취소" onClick="alert('회원 탈퇴가 취소되었습니다.')">
					<a href="/blog/s/member/mypage">취소</a>
				</button>
			</div>
		</div>
	</form>
</div>

<%@ include file="/jsp/part/foot.jspf"%>