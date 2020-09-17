<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/jsp/part/head.jspf"%>
<%@ include file="/jsp/part/toastUiEditor.jspf"%>

<script>
	<!-- 댓글 수정 각 값(내용) 체크 -->
	var writeFormSubmitted = false;

	function submitWriteForm(form) {
		if (writeFormSubmitted) {
			alert('처리 중입니다.');
			return;
		}

		var editor = $(form).find('.toast-editor').data('data-toast-editor');

		var body = editor.getMarkdown();
		body = body.trim();
		
		if ( body.length == 0 ) {
		  alert('내용을 입력해주세요.');
		  body.focus();
		  
		  return;
		}
	  
		form.body.value = body;

		form.submit();
		submitModifyFormDone = true;
	}
</script>

<h1 class="title">댓글 수정</h1>

<form method="POST" class="table-box table-box-vertical con form1" action="doModifyReply" onsubmit="submitWriteForm(this); return false;">
	<table>
		<colgroup>
			<col width="250">
		</colgroup>
		<tbody>
			<tr>
				<th>댓글 번호</th>
				<td>
					<div class="form-control-box">
						<input name="articleId" type="hidden" value="${articleId}" />
						<input name="id" type="hidden" value="${id}" />	${id}
					</div>
				</td>
			</tr>
			<tr>
				<th>카테고리</th>
				<td>
					<div class="form-control-box">
						<select name="cateItemId">
							<c:forEach items="${cateItems}" var="cateItem">
								<option ${article.cateItemId == cateItem.id ? 'selected' : ''} value="${cateItem.id}">${cateItem.name}</option>
							</c:forEach>
						</select>
					</div>
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>
					<div class="form-control-box">
						<input name="body" type="hidden">
						<script type="text/x-template">${body}</script>
						<div class="toast-editor"></div>
					</div>
				</td>
			</tr>
			<tr>
				<th>수정</th>
				<td>
					<button class="btn btn-primary" type="submit" onclick="if ( confirm('수정하시겠습니까?') == false ) return false;">완료</button>
					<button class="btn btn-danger" type="button" onclick="history.back();">취소</button>
				</td>
			</tr>
		</tbody>
	</table>
</form>

<%@ include file="/jsp/part/foot.jspf"%>