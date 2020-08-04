<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/jsp/article/messenger.jsp"%>

<h3 class="messages-h con flex-jc-c">보낸 쪽지함</h3>

<div class="con flex-jc-c">
	<div class="messages-box">
		<table class="messages-list">
			<tr>
				<th>번호</th>
				<th><a href="">제목</a></th>
				<th>받는사람</th>
				<th>날짜</th>
			</tr>
			<c:forEach items="${sendMessages}" var="sendMessage">
				<tr>
					<td>${sendMessage.id}</td>
					<td>${sendMessage.title}</td>
					<td>${sendMessage.nickName}</td>
					<td>${sendMessage.regDate}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>

<%@ include file="/jsp/part/foot.jspf"%>