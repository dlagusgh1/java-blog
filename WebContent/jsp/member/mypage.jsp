<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/jsp/part/head.jspf"%>

<h1 class="mypage-h">마이 페이지</h1>

<div class="mypage-img con flex-jc-c">
	<div class="mypage-img-wrap">
		<c:if test="${loginedMember.myImg == null}">
			<img src="" alt="" /> 프로필이 없습니다.
		</c:if>
		<c:if test="${loginedMember.myImg != null}">
			<img src="${loginedMember.myImg}" alt="profile">
		</c:if>
		<div >
			<button type="button" value="이미지 변경">
				<a href="${pageContext.request.contextPath}/s/member/myImgModify" onclick="window.open(this.href,'프로필 변경', 'width=600px, height=300px, scrollbars=no, resizeble=0, directories=0' ); return false;">프로필 이미지 변경하기</a>
			</button>	
		</div>
	</div>
</div>


<div class="mypage-box con flex-jc-c">
	<form class="mypage-form form7">
		<div class="form-row">
			<div class="label">회원 아이디</div>
			<div class="input">
				<input name="id" type="hidden" value="${loginedMember.loginId}" />:
				${loginedMember.loginId}
			</div>
		</div>
		<div class="form-row">
			<div class="label">회원 이름</div>
			<div class="input">
				<input name="name" type="hidden" value="${loginedMember.name}" />:
				${loginedMember.name}
			</div>
		</div>
		<div class="form-row">
			<div class="label">회원 닉네임</div>
			<div class="input">
				<input name="nickName" type="hidden" value="${loginedMember.nickName}" />:
				${loginedMember.nickName}
			</div>
		</div>
		<div class="form-row">
			<div class="label">이메일</div>
			<div class="input">
				<input name="email" type="hidden" value="${loginedMember.email}" />:
				${loginedMember.email}
			</div>
		</div>
		<div class="form-row">
			<div class="label">이메일 인증</div>
			<div class="input">
				<input name="email" type="hidden" value="${loginedMember.email}" />:
					<c:choose>
						<c:when test="${emailAuthed.equals(loginedMember.email)}">
							인증완료
						</c:when>
						<c:otherwise>
							비 인증
						</c:otherwise>
					</c:choose>
			</div>
		</div>
		<div class="form-row">
			<div class="label">방문</div>
			<div class="input">
				: 1 회
			</div>
		</div>
		<div class="form-row">
			<div class="label">내가 쓴 글</div>
			<div class="input">
				: <a href="${pageContext.request.contextPath}/s/member/myList">${articleCount} 개</a>
			</div>
		</div>
		<div class="form-row">
			<div class="label">내가 쓴 댓글</div>
			<div class="input">
				: <a href="${pageContext.request.contextPath}/s/member/myReplyList">${replyCount} 개</a>
			</div>
		</div>
	</form>
</div>
<div class="mypage-cd">
	<div class="input flex-jc-c">
		<div >
			<button type="button" value="회원정보수정">
				<a href="${pageContext.request.contextPath}/s/member/passwordForPrivate">패스워드 수정</a>
			</button>	
		</div>
		<div>
			<button type="button" value="탈퇴" style="margin-left: 10px;">
				<a href="${pageContext.request.contextPath}/s/member/passwordForPrivate">회원 탈퇴</a>
			</button>	
		</div>
	</div>
</div>

<%@ include file="/jsp/part/foot.jspf"%>