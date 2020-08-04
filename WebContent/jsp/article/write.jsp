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

<h1 class="article-doWrite-h">게시물 작성</h1>

<!-- 게시물 작성기능 -->
<div class="write-form-box con">
	<form action="doWrite" method="POST" class="write-form form1"
		onsubmit="submitWriteForm(this); return false;">
		<input name="memberLevel" type="hidden" value="${loginedMember.level}" />
		<input name="memberId" type="hidden" value="${loginedMemberId}" />
		<div class="form-row">
			<div class="label">카테고리</div>
			<div class="input">
				<select name="cateItemId">
					<c:forEach items="${cateItems}" var="cateItem">
						<option id="article-cateItem" value="${cateItem.id}">${cateItem.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-row">
			<div class="label">제목</div>
			<div class="input">
				<input name="title" type="text" placeholder="제목 입력">
			</div>
		</div>
		<div class="form-row">
			<div class="label">내용</div>
			<div class="input">
				<input name="body" type="hidden">			
				<script type="text/x-template"></script>
				<div class="toast-editor" style="width: 300px; height: 100px;"></div>
			</div>
		</div>
		<div class="form-row">
			<div class="label"></div>
			<div class="input flex-jc-c" style="margin: 30px 0px;">
				<button type="submit" value="완료" style="padding: 5px 15px; font-size: 20px;">완료</button>
				<button type="button" value="취소" onClick="alert('작성이 취소되었습니다.')" style="padding: 5px 15px; font-size: 20px;">
					<a href="/blog/s/article/list">취소</a>
				</button>
			</div>
		</div>
	</form>
</div>

<%@ include file="/jsp/part/foot.jspf"%>