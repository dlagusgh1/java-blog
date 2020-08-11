<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/jsp/part/head.jspf"%>


<script>

	<!-- 게시물 작성 각 값(제목, 내용) 체크 -->
	var submitWriteFormDone = false;
	function submitWriteForm(form) {
		if ( submitWriteFormDone ) {
			alert('처리중입니다.');
			return;
		}
		body = body.trim();
		if ( body.length == 0 ) {
			alert('내용을 입력해주세요.');
			editor.focus();
			return false;
		}
		form.body.value = body;
		form.submit();
		submitWriteFormDone = true;
	}
	
</script>

<h1 class="mypage-h">채팅</h1>

<div class="chat-view con" id="chatContent">
	<div class="con">	
		<c:forEach items="${chatMessage}" var="chat">	
			<c:choose>
				<c:when test="${chat.memberId == loginedMemberId}">
					<div class="chat-me-wrap">
						<div class="chat-me-box flex-jc-end">
							<div class="chattingMe">					
								<div>
									${chat.body}						
								</div>
								<span>${chat.regDate}</span> 
							</div>
						</div>		
					</div>			
				</c:when>
				<c:otherwise>
					<div class="chat-others-wrap">
						<div class="chat-others-box">
							<div class="chattingOthers">
								<span>[${chat.getExtra().get("writer")}]</span>
								<div>
									${chat.body}
								</div>
								<span>${chat.regDate}</span>
							</div>
						</div>
					</div>
				</c:otherwise>			
			</c:choose>
		</c:forEach>
	</div>
</div>

<div class="chat-box con">
	<c:choose>
		<c:when test="${isLogined}">
			<form class="chat-comment con flex-jc-c" method="POST" action="doChat" onsubmit="submitWriteForm(this); return false;">
				<input type="hidden" name="memberId" value="${loginedMemberId}"/>
				<input class="comment" type="text" name="body" placeholder="클린 채팅 부탁드립니다." />
				<button type="submit" onsubmit="scrollToBottom();">전송</button>
			</form>
		</c:when>
		<c:otherwise>
			<div class="chat-comment-none"">
				<p>로그인 한 회원만 채팅이 가능합니다. <a href="${pageContext.request.contextPath}/s/member/login?afterLoginRedirectUrl=${urlEncodedAfterLoginRedirectUrl}">로그인</a> 후 이용해 주세요.</p>
			</div>
		</c:otherwise>
	</c:choose>
	
</div>

<script>
		<!-- 채팅 스크롤 하단으로 보내기 -->
	$('#chatContent').scrollTop($('#chatContent')[0].scrollHeight);
	
</script>

<%@ include file="/jsp/part/foot.jspf"%>