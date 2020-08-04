<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/jsp/part/head.jspf"%>

<h1 class="cs-h">통계</h1>
<div class="mypage-box con flex-jc-c">
	<form class="mypage-form form2">
		<div class="form-row">
			<div class="label">전체 회원</div>
			<div class="input">
				: ${memberCount} 명
			</div>
		</div>
		<div class="form-row">
			<div class="label">전체 게시물</div>
			<div class="input">
				: ${articleCount} 개
			</div>
		</div>
		<div class="form-row">
			<div class="label">전체 댓글</div>
			<div class="input">
				: ${articleReplyCount} 개
			</div>
		</div>
		<div class="form-row">
			<div class="label">방문자</div>
			<div class="input">
				: - 명
			</div>
		</div>
	</form>
</div>

<%@ include file="/jsp/part/foot.jspf"%>