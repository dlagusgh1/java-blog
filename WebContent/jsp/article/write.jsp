<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/jsp/part/head.jspf"%>
<%@ include file="/jsp/part/toastUiEditor.jspf"%>

<script>
	<!-- 게시물 작성 각 값(제목, 내용) 체크 -->
	var submitWriteFormDone = false;
	function submitWriteForm(form) {
		if ( submitWriteFormDone ) {
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
		submitWriteFormDone = true;
	}
</script>

<h1 class="title">게시물 작성</h1>

<form method="POST" class="table-box table-box-vertical con form1" action="doWrite" onsubmit="submitWriteForm(this); return false;">
	<input name="memberLevel" type="hidden" value="${loginedMember.level}" />
	<input name="memberId" type="hidden" value="${loginedMemberId}" />
	<table>
		<colgroup>
			<col width="250">
		</colgroup>
		<tbody>
			<tr>
				<th>카테고리</th>
				<td>
					<div class="form-control-box">
						<select name="cateItemId">
							<option>게시판 선택</option>	
							<c:forEach items="${cateItems}" var="cateItem">
								<option id="article-cateItem" value="${cateItem.id}">${cateItem.name}</option>
							</c:forEach>
						</select>
					</div>
				</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>
					<div class="form-control-box">
						<input name="title" type="text" placeholder="제목 입력">
					</div>
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>
					<div class="form-control-box">
						<input name="body" type="hidden">			
						<script type="text/x-template"></script>
						<div class="toast-editor"></div>
					</div>
				</td>
			</tr>
			<tr>
				<th>작성</th>
				<td>
					<button class="btn btn-primary" type="submit">완료</button>
					<button class="btn btn-danger" type="button" onclick="history.back();">취소</button>
				</td>
			</tr>
		</tbody>
	</table>
</form>

<%@ include file="/jsp/part/foot.jspf"%>