<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/jsp/part/head.jspf"%>
<%@ include file="/jsp/part/toastUiEditor.jspf"%>

<script>
	<!-- 게시물 수정 각 값(제목, 내용) 체크 -->
	var submitModifyFormDone = false;
	
	function submitModifyForm(form) {
		if ( submitModifyFormDone ) {
			alert('처리중입니다.');
			return;
		}
		
		form.title.value = form.title.value.trim();

		if ( form.title.value.length == 0 ) {
			alert('제목을 입력해주세요.');
			form.title.focus();

			return false;
		}
		
		var editor = $(form).find('.toast-editor').data('data-toast-editor');

		var body = editor.getMarkdown();
		body = body.trim();

		if ( body.length == 0 ) {
			alert('내용을 입력해주세요.');
			editor.focus();

			return false;
		}

		form.body.value = body;

		form.submit();
		submitModifyFormDone = true;
	}
</script>

<h1 class="title">게시물 수정</h1>

<form method="POST" class="table-box table-box-vertical con form1" action="doModify" onsubmit="submitModifyForm(this); return false;">
	<table>
		<colgroup>
			<col width="250">
		</colgroup>
		<tbody>
			<tr>
				<th>게시물 번호</th>
				<td>
					<div class="form-control-box">
						<input name="id" type="hidden" value="${article.id}" />${article.id}
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
				<th>제목</th>
				<td>
					<div class="form-control-box">
						<input value="${article.title}" name="title" type="text" placeholder="제목을 입력해주세요." />
					</div>
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>
					<div class="form-control-box">
						<input name="body" type="hidden">
						<script type="text/x-template">${article.bodyForXTemplate}</script>
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