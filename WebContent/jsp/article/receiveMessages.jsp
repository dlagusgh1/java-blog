<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/jsp/article/messenger.jsp"%>

<h3 class="title con flex-jc-c">받은 쪽지함</h3>


<div class="con flex-jc-c">
	<div class="messages-box">
		<table class="messages-list">
			<tr>
				<th>번호</th>
				<th><a href="">제목</a></th>
				<th>보낸사람</th>
				<th>날짜</th>
			</tr>
			<c:forEach items="${receiveMessages}" var="receiveMessage">
				<tr>
					<td>${receiveMessage.id}</td>
					<td>${receiveMessage.title}</td>
					<td>${receiveMessage.getExtra().get("writer")}</td>
					<td>${receiveMessage.regDate}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>

<div class="total-box con flex-ai-c">
	<div class="total">
		받은 쪽지 수 : ${totalCount}
	</div>
</div>

<div class="page-box">
	<table class="page-navi flex-jc-c">
		<tr>
			<c:if test="${page != 1}">
				<c:set var="k" value="${page}" />
					<td><a href="?page=${k-1}"><</a></td>
			</c:if>
			<c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">
				<td class="${i == page ? 'current' : ''}">
					<a href="?page=${i}" class="block">${i}</a>
				</td>
			</c:forEach>
			<c:if test="${page != totalPage}">
				<c:set var="k" value="${page}" />
					<td><a href="?page=${k+1}">></a></td>
			</c:if>
		</tr>
	</table>
</div>

<%@ include file="/jsp/part/foot.jspf"%>