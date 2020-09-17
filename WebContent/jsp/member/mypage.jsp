<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/jsp/part/head.jspf"%>

<h1 class="title">마이 페이지</h1>

<div class="mypage-box con flex-jc-c">
	<form method="POST" class="table-box con" action="myPage">
		<input type="hidden" name="redirectUri" value="/blog/s/home/main">
		<table>
			<colgroup>
				<col width="250">
			</colgroup>
			<tbody>
				<tr>
					<th>회원 이미지</th>
					<td>
						<div class="form-control-box myImg-box">
							<c:choose>
								<c:when test="${loginedMember.myImg.equals(\"null\")}">
								</c:when>
								<c:otherwise>
									<img class="profileImg" src="${loginedMember.myImg}" alt="">
								</c:otherwise>
							</c:choose>
						</div>
					</td>
				</tr>
				<tr>
					<th>회원 소개글</th>
					<td>
						<c:choose>
							<c:when test="${loginedMember.myIntro.equals(\"null\")}">
								<span style="color: #888;">소개글이 없습니다. 지금 소개글을 등록해보세요!</span>
							</c:when>
							<c:otherwise>
								<span>${loginedMember.myIntro}</span>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th>프로필 / 소개글 변경</th>
					<td>
						<button class="btn btn-primary" type="button"><a href="${pageContext.request.contextPath}/s/member/myImgModify" onclick="openInNewTab(this.href,'프로필 변경', 'width=600px, height=400px, scrollbars=no, resizeble=0, directories=0' ); return false;">프로필 변경</a></button>
						<button class="btn btn-primary" type="button"><a href="${pageContext.request.contextPath}/s/member/myIntroModify" onclick="openInNewTab(this.href,'프로필 소개글 변경', 'width=600px, height=400px, scrollbars=no, resizeble=0, directories=0' ); return false;">소개글 변경</a></button>
					</td>
				</tr>
				<tr>
					<th>회원 아이디</th>
					<td>
						<div class="form-control-box">
							<input type="hidden" name="loginId"	maxlength="30" />${loginedMember.loginId}
						</div>
					</td>
				</tr>
				<tr>
					<th>이름</th>
					<td>
						<div class="form-control-box">
							<input type="hidden" name="name" maxlength="20" />${loginedMember.name}
						</div>
					</td>
				</tr>
				<tr>
					<th>회원 닉네임</th>
					<td>
						<div class="form-control-box">
							<input type="hidden" name="nickName" maxlength="20" />${loginedMember.nickName}
						</div>
					</td>
				</tr>
				<tr>
					<th>이메일</th>
					<td>
						<div class="form-control-box">
							<input type="hidden" name="email"	maxlength="50" />${loginedMember.email}
						</div>
					</td>
				</tr>
				<tr>
					<th>이메일 인증여부</th>
					<td>
						<div class="form-control-box">
							<c:choose>
								<c:when test="${emailAuthed != \"\"}">
									인증 완료
								</c:when>
								<c:otherwise>
									비 인증
								</c:otherwise>
							</c:choose>
						</div>
					</td>
				</tr>
				<c:if test="${emailAuthed == \"\"}">
					<tr>
						<th>인증 메일 발송</th>
						<td>
							<button class="btn"><a href="${pageContext.request.contextPath}/s/member/reAuthEmail" style="width:150px;">인증메일 재 발송</a></button>
						</td>
					</tr>
				</c:if>
				<tr>
					<th>방문횟수</th>
					<td>
						<div class="form-control-box">
							1회
						</div>
					</td>
				</tr>
				<tr>
					<th>내가쓴 글 수</th>
					<td>
						<div class="form-control-box">
							<a href="${pageContext.request.contextPath}/s/member/myList">${articleCount} 개</a>
						</div>
					</td>
				</tr>
				<tr>
					<th>내가쓴 댓글 수</th>
					<td>
						<div class="form-control-box">
							<a href="${pageContext.request.contextPath}/s/member/myReplyList">${replyCount} 개</a>
						</div>
					</td>
				</tr>
				<tr>
					<th>회원정보 변경</th>
					<td>
						<button class="btn btn-primary" type="button"><a href="${pageContext.request.contextPath}/s/member/passwordForPrivate">비밀번호 변경</a></button>
					</td>
				</tr>
				<tr>
					<th>회원 탈퇴</th>
					<td>
						<button class="btn btn-danger" type="button"><a href="${pageContext.request.contextPath}/s/member/passwordForPrivate">회원탈퇴</a></button>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

<%@ include file="/jsp/part/foot.jspf"%>