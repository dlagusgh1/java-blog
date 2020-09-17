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

<h1 class="title">회원 아이디 찾기</h1>

<form method="POST" class="table-box table-box-vertical con form1" action="doFindLoginId" onsubmit="FindLoginIdForm__submit(this); return false;">
	<table>
		<colgroup>
			<col width="250">
		</colgroup>
		<tbody>
			<tr>
				<th>이름</th>
				<td>
					<div class="form-control-box">
						<input type="text" name="name" maxlength="20" placeholder="이름 입력">
					</div>
				</td>
			</tr>
			<tr>
				<th>이메일</th>
				<td>
					<div class="form-control-box">
						<input type="email" name="email" maxlength="20" placeholder="email 입력">
					</div>
				</td>
			</tr>
			<tr>
				<th>아이디 찾기</th>
				<td>
					<button class="btn btn-primary" type="submit">찾기</button>
					<button class="btn btn-danger" type="button" onclick="history.back();">취소</button>
				</td>
			</tr>
		</tbody>
	</table>
</form>

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
		FindLoginPwForm__submitDone = true;
	}
</script>

<h1 class="title">회원 비밀번호 찾기</h1>

<form method="POST" class="table-box table-box-vertical con form1" action="doFindPw" onsubmit="FindLoginPwForm__submit(this); return false;">
	<table>
		<colgroup>
			<col width="250">
		</colgroup>
		<tbody>
			<tr>
				<th>아이디</th>
				<td>
					<div class="form-control-box">
						<input type="text" name="loginId" maxlength="20" placeholder="회원 id 입력">
					</div>
				</td>
			</tr>
			<tr>
				<th>이름</th>
				<td>
					<div class="form-control-box">
						<input type="text" name="name" maxlength="20" placeholder="이름 입력">
					</div>
				</td>
			</tr>
			<tr>
				<th>이메일</th>
				<td>
					<div class="form-control-box">
						<input type="email" name="email" maxlength="20" placeholder="email 입력">
					</div>
				</td>
			</tr>
			<tr>
				<th>비밀번호 찾기</th>
				<td>
					<button class="btn btn-primary" type="submit">찾기</button>
					<button class="btn btn-danger" type="button" onclick="history.back();">취소</button>
				</td>
			</tr>
		</tbody>
	</table>
</form>

<%@ include file="/jsp/part/foot.jspf"%>