<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>

<%@ include file="/jsp/part/toastUiEditor.jspf"%>

<script>
		var WriteReplyForm__submitDone = false;

		function WriteReplyForm__focus() {
			var editor = $('.write-reply-form .toast-editor').data(
					'data-toast-editor');

			editor.focus();
		}

		function WriteReplyForm__submit(form) {
			if (WriteReplyForm__submitDone) {
				alert('처리중입니다.');
				return;
			}

			var editor = $(form).find('.toast-editor')
					.data('data-toast-editor');

			var body = editor.getMarkdown();
			body = body.trim();

			if (body.length == 0) {
				alert('내용을 입력해주세요.');
				editor.focus();

				return false;
			}

			form.body.value = body;

			form.submit();
			WriteReplyForm__submitDone = true;
		}

		function WriteReplyForm__init() {
			$('.write-reply-form .cancel').click(
					function() {
						var editor = $('.write-reply-form .toast-editor').data(
								'data-toast-editor');
						editor.setMarkdown('');
					});
		}

		$(function() {
			WriteReplyForm__init();
		});
	</script>

<h1 class="title">${article.title}</h1>
<div class="article-detail-info con flex-ai-c">
	<c:forEach items="${articleNickName}" var="articleName">
		<c:if test="${article.id == articleName.id}">
			<div>작성자 : ${articleName.getExtra().get("writer")}</div>
		</c:if>
	</c:forEach>
	<c:if test="${article.regDate == article.updateDate}">	
		<div>
			작성일 : ${article.regDate}
		</div>
	</c:if>
	<c:if test="${article.regDate != article.updateDate}">
		<div>
			수정일 : ${article.updateDate}
		</div>
	</c:if>
	<div>
		조회 : ${article.hit}
	</div>
	<div>
		댓글 : ${articleReplyCount}
	</div>
	<c:if test="${isLogined}">
		<c:if test="${article.extra.modifyAvailable}">		
			<div class="info-mf">
				<div class="info-mf">
		
					<form name="modify-Article-box" method="POST" action="modify">
						<input type="hidden" name="id" value="${article.id}" /> 
						<input type="hidden" name="title" value="${article.title}" /> 
						<input type="hidden" name="body" value="${article.body}" />
						<button class="btn btn-primary" type="submit">수정</button>
					</form>
				</div>
			</div>
		</c:if>
		<c:if test="${article.extra.deleteAvailable}">
			<div class="info-del">
				<form name="delete-Article-box" method="POST" action="doDelete">
					<input type="hidden" name="id" value="${article.id}" />
					<button class="btn btn-danger" onclick="if ( confirm('삭제하시겠습니까?') == false ) return false;" type="submit" onClick="alert('게시물 삭제가 완료되었습니다.')">삭제</button>
				</form>	
			</div>
		</c:if>
	</c:if>
</div>

<!-- 상세보기 내용 보여줌 -->
<div class="article-detail-box con flex">
	<script type="text/x-template">${article.bodyForXTemplate}</script>
	<div class="toast-editor toast-editor-viewer con flex-jc-c"></div>
</div>

<!-- 상세보기 내 작성자 프로필 -->

<div class="detail-profile-box con flex">
	<div class="detail-profile-img flex flex-ai-c">
		<c:forEach items="${members}" var="member">
			<c:if test="${article.memberId == member.id}">
				<c:choose>
					<c:when test="${member.myImg.equals(\"null\")}">
						<div class="noneProfileImg"></div>
					</c:when>
					<c:otherwise>
						<img class="profileImg" src="${member.myImg}" alt="">
					</c:otherwise>
				</c:choose>
			</c:if>
		</c:forEach>
	</div>
	<div class="p-txt">
		<c:forEach items="${articleNickName}" var="articleName">
			<c:if test="${article.id == articleName.id}">
				<h1>${articleName.getExtra().get("writer")}</h1>
			</c:if>
		</c:forEach>
		<p>
			<c:forEach items="${members}" var="member">
				<c:if test="${article.memberId == member.id}">
					<c:choose>
						<c:when test="${member.myIntro.equals(\"\")}">
							<span style="color: #888;">
								소개글이 없습니다.<br>
							</span>
						</c:when>
						<c:otherwise>
							<span>
								${member.myIntro}
							</span>
						</c:otherwise>
					</c:choose>			
				</c:if>
			</c:forEach>
		</p>
		<c:if test="${article.regDate == article.updateDate}">	
			<div class="regDate">
				${article.regDate}
			</div>
		</c:if>
		<c:if test="${article.regDate != article.updateDate}">
			<div class="regDate">
				${article.updateDate}
			</div>
		</c:if>
	</div>
</div>


<!-- 댓글 출력 -->
<div class="comment-view-box con flex">
	<div class="comment-view">
		<c:forEach items="${articleReplies}" var="articleReplies">
			<div class="comment-top" style="font-weight: bold;">
				작성자 : ${articleReplies.extra.writer}
			</div>
			<div>
				${articleReplies.body}
			</div>
			<div class="comment-bottom" style="font-size: 15px; border-bottom: 2px solid #ddd;">
				<c:if test="${articleReplies.regDate == articleReplies.updateDate}">
					${articleReplies.regDate}
				</c:if> 
				<c:if test="${articleReplies.regDate != articleReplies.updateDate}">
					${articleReplies.updateDate}
				</c:if>
			</div>
			<c:if test="${isLogined}">	
				<c:if test="${articleReplies.memberId == loginedMemberId}">
					<div class="comment-info con flex">
						<form name="reply-modify" method="POST" action="modifyReply">
							<input type="hidden" name="articleId" value="${article.id}" />
							<input type="hidden" name="id" value="${articleReplies.id}" />
							<input type="hidden" name="body" value="${articleReplies.body}" />
							<button class="btn btn-primary" type="submit">수정</button>
						</form>
						<form name="reply-delete" method="POST" action="doDeleteReply">
							<input type="hidden" name="articleId" value="${article.id}" />
							<input type="hidden" name="id" value="${articleReplies.id}" />
							<button class="btn btn-danger" type="submit" onClick="alert('댓글 삭제가 완료되었습니다.')">삭제</button>
						</form>
					</div>
				</c:if>
			</c:if>
		</c:forEach>
	</div>
</div>
		
<!-- 댓글 페이징 -->
<div class="Page-box">
	<table class="page-navi flex-jc-c margin-bottom-20">
		<tbody>
			<tr>			
				<c:if test="${totalPage == 0}">
					<div class="con flex-jc-c">댓글이 없습니다.</div>
				</c:if>
				<c:if test="${totalPage != 0}">
					<c:if test="${page != 1 && totalPage != 0}">
						<td><a href="?id=${param.id}&page=${startPage}"><i class="fas fa-angle-double-left"></i></a></td>
						<c:set var="k" value="${page}" />
							<td>
								<a href="?id=${param.id}&page=${k-1}"><i class="fas fa-angle-left"></i></a>
							</td>
					</c:if>
					<c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">
						<td class="${i == page ? 'current' : ''}">
							<a href="?id=${param.id}&page=${i}"	class="block">${i}</a>
						</td>
					</c:forEach>
					<c:if test="${page != totalPage && totalPage != 0}">
						<c:set var="k" value="${page}" />
							<td>
								<a href="?id=${param.id}&page=${k+1}"><i class="fas fa-angle-right"></i></a>
							</td>
							<td><a href="?id=${param.id}&page=${endPage}"><i class="fas fa-angle-double-right"></i></a></td>
					</c:if>
				</c:if>
			</tr>
		</tbody>
	</table>
</div>

<!-- 댓글 작성 -->
<c:if test="${isLogined}">
	<form method="POST" class="table-box table-box-vertical con form1" action="doWriteReply" onsubmit="WriteReplyForm__submit(this); return false;">
		<input type="hidden" name="articleId" value="${article.id}">
		<input type="hidden" name="body">
		<table>
			<colgroup>
				<col width="250">
			</colgroup>
			<tbody>
				<tr>
					<th>댓글 내용 입력</th>
					<td>
						<div class="form-control-box">
							<script type="text/x-template"></script>
							<div data-toast-editor-height="300" class="toast-editor"></div>
						</div>
					</td>
				</tr>
				<tr>
					<th>댓글 작성</th>
					<td>
						<button class="btn btn-primary" type="submit">작성</button>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</c:if>

<%@ include file="/jsp/part/foot.jspf"%>