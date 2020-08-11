<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/jsp/part/head.jspf"%>
<!-- 페이지 로딩 효과(부드럽게) -->
<script>
	jQuery(function($) {
		$("body").css("display", "none");
		$("body").fadeIn(2000);
		$("a.transition").click(function(event){
			event.preventDefault();
			linkLocation = this.href;
			$("body").fadeOut(1000, redirectPage);
			});
			function redirectPage() {
			window.location = linkLocation;
		}
	});
</script>


<!-- 메인 이미지 -->
<div id="main">
	<div id="contents">
		<h1>비전공 개발자</h1>
		<p>홈페이지에 오신걸 환영합니다.</p>
		<c:if test="${isLogined == false}">
			<a href="${pageContext.request.contextPath}/s/member/login">함께하기</a>
		</c:if>
		<c:if test="${isLogined}">
			<a href="${pageContext.request.contextPath}/s/home/aboutMe">개발자 소개</a>
		</c:if>
	</div>
</div>

<%@ include file="/jsp/part/foot.jspf"%>