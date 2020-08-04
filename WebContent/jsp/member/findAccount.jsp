<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<c:set var="pageTitle" value="아이디/비번 찾기"></c:set>
<%@ include file="/jsp/part/head.jspf"%>

<!-- 암호화 -->
<script	src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<!-- 아이디 찾기 각 값 체크 -->
<script>
	var FindLoginIdForm__submitDone = false;
	function FindLoginIdForm__submit(form) {
		if (FindLoginIdForm__submitDone) {
			alert('처리중 입니다.');
			return;
		}

		form.name.value = form.name.value.trim();

		if (form.name.value.length == 0) {
			alert('이름을 입력해주세요.');
			form.name.focus();
			return;
		}

		form.email.value = form.email.value.trim();

		if (form.email.value.length == 0) {
			alert('이메일을 입력해주세요.');
			form.email.focus();
			return;
		}

		form.submit();
		FindLoginIdForm__submitDone = true;
	}
</script>

<h1 class="findId-h">회원 아이디 찾기</h1>

<div class="findId-form-box con flex-jc-c">
	<form action="doFindLoginId" method="POST" class="findId-form form3" onsubmit="FindLoginIdForm__submit(this); return false;">
		<div class="form-row">
			<div class="label">이름</div>
			<div class="input">
				<input type="text" name="name" maxlength="20" placeholder="이름 입력">
			</div>
		</div>
		<div class="form-row">
			<div class="label">이메일</div>
			<div class="input">
				<input type="email" name="email" maxlength="20"
					placeholder="email 입력">
			</div>
		</div>
		<div class="form-row">
			<div class="input flex-jc-c">
				<button type="submit" value="찾기">찾기</button>
				<button type="button" value="취소" onClick="alert('아이디 찾기가 취소되었습니다.')">
					<a href="/blog/s/home/main">취소</a>
				</button>
			</div>
		</div>
	</form>
</div>

<script>
	var FindLoginPwForm__submitDone = false;
	function FindLoginPwForm__submit(form) {
		if (FindLoginPwForm__submitDone) {
			alert('처리중 입니다.');
			return;
		}

		form.loginId.value = form.loginId.value.trim();

		if (form.loginId.value.length == 0) {
			alert('로그인 아이디를 입력해주세요.');
			form.loginId.focus();
			return;
		}

		form.email.value = form.email.value.trim();

		if (form.email.value.length == 0) {
			alert('이메일을 입력해주세요.');
			form.email.focus();
			return;
		}
		form.submit();
		FindLoginPwForm__submitDone = true;
	}
</script>

<h1 class="findPw-h">회원 비밀번호 찾기</h1>

<div class="findPw-form-box con flex-jc-c">
	<form action="doFindPw" method="POST" class="findPw-form form4"
		onsubmit="submitJoinForm(this); return false;">
		<div class="form-row">
			<div class="label">아이디</div>
			<div class="input">
				<input type="text" name="loginId" maxlength="20"
					placeholder="회원 id 입력">
			</div>
		</div>
		<div class="form-row">
			<div class="label">이름</div>
			<div class="input">
				<input type="text" name="name" maxlength="20" placeholder="이름 입력">
			</div>
		</div>
		<div class="form-row">
			<div class="label">이메일</div>
			<div class="input">
				<input type="email" name="email" maxlength="20"
					placeholder="email 입력">
			</div>
		</div>
		<div class="form-row">
			<div class="input flex-jc-c">
				<input type="hidden" name="tempPw" value="${tempPw}"/>
				<input type="hidden" name="loginRealPw" value="${tempPw}"/>
				<button type="submit" value="찾기">찾기</button>
				<button type="button" value="취소" onClick="alert('비밀번호 찾기가 취소되었습니다.')">
					<a href="/blog/s/home/main">취소</a>
				</button>
			</div>
		</div>
	</form>
</div>

<%@ include file="/jsp/part/foot.jspf"%>