<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/jsp/part/head.jspf"%>

<h1 class="article-h">
	${loginedMemberNickName}님 댓글 리스트
</h1>

<div class="article-box con flex-ai-c">
	<table class="article-list">
		<tr>
			<th style="width: 100px;">게시물 번호</th>
			<th style="width: 500px;">댓글</th>
			<th>작성일</th>
		</tr>
			<c:forEach items="${replies}" var="reply">
				<tr>
					<td>${reply.articleId}</td>
					<td class="title-link" ><a href="${pageContext.request.contextPath}/s/article/detail?id=${reply.articleId}">${reply.body}</a></td>
					<c:if test="${reply.regDate == reply.updateDate}">
						<td>${reply.regDate}</td>
					</c:if>
					<c:if test="${reply.regDate != reply.updateDate}">
						<td>${reply.updateDate}</td>
					</c:if>
				</tr>
			</c:forEach>
	</table>
</div>

<div class="total-box con flex-ai-c">
	<div class="total">
		총 댓글 수 : ${articleReplyCount}
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