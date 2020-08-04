<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/jsp/article/messenger.jsp"%>
<%@ include file="/jsp/part/toastUiEditor.jspf"%>

<h3 class="messages-h con flex-jc-c">쪽지 쓰기</h3>

<div class="con flex-jc-c">
	<div class="messagesWrite-box">
		<form action="doMessagesWrite" method="POST" class="write-form form1"
		onsubmit="submitWriteForm(this); return false;">
		<input name="memberId" type="hidden" value="${loginedMemberId}" />
		<div class="form-row">
			<div class="label">받는사람</div>
			<div class="input">
				<input name="nickName" type="text" placeholder="닉네임 입력">
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
				<div class="toast-editor"></div>
			</div>
		</div>
		<div class="form-row">
			<div class="label"></div>
			<div class="input flex-jc-c" style="margin: 30px 0px;">
				<button type="submit" value="보내기" style="padding: 5px 15px; font-size: 20px;">보내기</button>
				<button type="button" value="취소" onClick="alert('작성이 취소되었습니다.')" style="padding: 5px 15px; font-size: 20px;">
					<a href="/blog/s/article/receiveMessages">취소</a>
				</button>
			</div>
		</div>
	</form>
	</div>
</div>

<script>
	<!-- 게시물 작성 각 값(제목, 내용) 체크 -->
	var submitWriteFormDone = false;
	function submitWriteForm(form) {
		if ( submitWriteFormDone ) {
			alert('처리중입니다.');
			return;
		}

		form.memberId.value = form.memberId.value.trim();
		if ( form.memberId.value.length == 0 ) {
			alert('작성자를 입력해주세요.');
			form.memberId.focus();
			return false;
		}

		form.nickName.value = form.nickName.value.trim();
		if ( form.nickName.value.length == 0 ) {
			alert('닉네임을 입력해주세요.');
			form.nickName.focus();
			return false;
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

<%@ include file="/jsp/part/foot.jspf"%>