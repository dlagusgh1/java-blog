<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/jsp/article/messenger.jsp"%>

<h3 class="messages-h con flex-jc-c">받은 쪽지함</h3>

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

<%@ include file="/jsp/part/foot.jspf"%>