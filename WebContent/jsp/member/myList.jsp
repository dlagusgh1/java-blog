<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/jsp/part/head.jspf"%>

<h1 class="article-h">
	${loginedMemberNickName}님 게시물 리스트
</h1>

<div class="article-box con flex-ai-c">
	<table class="article-list">
		<tr>
			<th style="width: 100px;">번호</th>
			<th style="width: 500px;">제목</th>
			<th>작성일</th>
			<th style="width: 100px;">조회수</th>
		</tr>
			<c:forEach items="${articles}" var="article">
				<tr>
					<td>${article.id}</td>
					<td class="title-link" ><a href="${pageContext.request.contextPath}/s/article/detail?id=${article.id}">${article.title}</a></td>
						<c:if test="${article.regDate == article.updateDate}">
							<td>${article.regDate}</td>
						</c:if>
						<c:if test="${article.regDate != article.updateDate}">
							<td>${article.updateDate}</td>
						</c:if>
					<td>${article.hit}</td>
				</tr>
			</c:forEach>
	</table>
</div>

<div class="total-box con flex-ai-c">
	<div class="total">
		총 게시물 수 : ${articleCount}
		<div class="article-create">
			<c:if test="${isLogined}">
				<a href="${pageContext.request.contextPath}/s/article/write" class="block">게시물 작성</a>
			</c:if>
		</div>
	</div>
</div>

<div class="replyPage-box">
	<table class="page-navi flex flex-jc-c">
		<tbody>
			<tr>
				<c:if test="${page != 1}">
					<c:set var="k" value="${page}" />
						<td><a href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=${k-1}">prev</a></td>
				</c:if>
				<c:forEach var="i" begin="1" end="${totalPage}" step="1">
					<td class="${i == page ? 'current' : ''}">
						<a href="?page=${i}" class="block">${i}</a>
					</td>
				</c:forEach>
				<c:if test="${page != totalPage}">
					<c:set var="k" value="${page}" />
						<td><a href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=${k+1}">next</a></td>
				</c:if>
			</tr>
		</tbody>
	</table>
</div>

<%@ include file="/jsp/part/foot.jspf"%>