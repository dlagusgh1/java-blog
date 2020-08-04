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

<h1 class="article-doWrite-h">게시물 수정</h1>

<!-- 게시물 수정기능 -->
<div class="write-form-box con">
	<form action="doModify" method="POST" class="write-form form1"
		onsubmit="submitModifyForm(this); return false;">
		<div class="form-row">
			<div class="label">게시물 번호</div>
			<div class="input">
				<input name="id" type="hidden" value="${article.id}" />
				${article.id}
			</div>
		</div>
		<div class="form-row">
			<div class="label">카테고리</div>
			<div class="input">
				<select name="cateItemId">
					<c:forEach items="${cateItems}" var="cateItem">
						<option ${article.cateItemId == cateItem.id ? 'selected' : ''} value="${cateItem.id}">${cateItem.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-row">
			<div class="label">제목</div>
			<div class="input">
				<input value="${article.title}" name="title" type="text" placeholder="제목을 입력해주세요." />
			</div>
		</div>
		<div class="form-row">
			<div class="label">내용</div>
			<div class="input">
				<input name="body" type="hidden">
				<script type="text/x-template">${article.bodyForXTemplate}</script>
				<div class="toast-editor"></div>
			</div>
		</div>
		<div class="form-row">
			<div class="label"></div>
			<div class="input flex-jc-c" style="margin: 30px 0px;">
				<button type="submit" value="완료" onclick="if ( confirm('수정하시겠습니까?') == false ) return false;" style="padding: 5px 15px; font-size: 20px;">완료</button>
				<button type="button" value="취소" onClick="alert('수정이 취소되었습니다.')" style="padding: 5px 15px; font-size: 20px;">
					<a href="/blog/s/article/list">취소</a>
				</button>
			</div>
		</div>
	</form>
</div>

<%@ include file="/jsp/part/foot.jspf"%>