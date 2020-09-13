<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/jsp/part/head.jspf"%>

<style>
	@media ( max-width : 800px) {
		body {
			margin-left: 0;
			margin-right: 0;
		}
	}
</style>
<!-- 메인 이미지 -->
<div id="main">
	<div id="contents">
		<h1>비전공 개발자</h1>
		<p>방문을 환영합니다.</p>
		<div class="main-contents">
			<c:if test="${isLogined == false}">
				<a href="${pageContext.request.contextPath}/s/member/login">함께하기</a>
			</c:if>
			<c:if test="${isLogined}">
				<a href="${pageContext.request.contextPath}/s/home/aboutMe">개발자 소개</a>
			</c:if>
		</div>
	</div>
</div>

<%@ include file="/jsp/part/foot.jspf"%>