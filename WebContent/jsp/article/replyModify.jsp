<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/jsp/part/head.jspf"%>
<%@ include file="/jsp/part/toastUiEditor.jspf"%>

<h1 class="article-doWrite-h">댓글 수정</h1>

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

<!-- 댓글 수정기능 -->
<div class="write-form-box con">
	<form action="doModifyReply" method="POST" class="write-form form1"
		onsubmit="submitWriteForm(this); return false;">
		<div class="form-row">
			<div class="label">댓글 번호</div>
			<div class="input">
				<input name="articleId" type="hidden" value="${articleId}" />
				<input name="id" type="hidden" value="${id}" />
				${id}
			</div>
		</div>
		<div class="form-row">
			<div class="label">내용</div>
			<div class="input">
				<input name="body" type="hidden">
				<script type="text/x-template">${body}</script>
				<div class="toast-editor"></div>
			</div>
		</div>
		<div class="form-row">
			<div class="label"></div>
			<div class="input flex-jc-c" style="margin: 30px 0px;">
				<button type="submit" value="완료" style="padding: 5px 15px; font-size: 20px;">완료</button>
				<button type="button" value="취소" onClick="alert('수정이 취소되었습니다.')" style="padding: 5px 15px; font-size: 20px;">
					<a href="/blog/s/article/list">취소</a>
				</button>
			</div>
		</div>
	</form>
</div>

<%@ include file="/jsp/part/foot.jspf"%>