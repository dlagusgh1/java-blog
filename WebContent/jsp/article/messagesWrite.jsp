<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/jsp/article/messenger.jsp"%>
<%@ include file="/jsp/part/toastUiEditor.jspf"%>

<h3 class="title con flex-jc-c">쪽지 쓰기</h3>

<form method="POST" class="table-box table-box-vertical con form1" action="doMessagesWrite" onsubmit="submitWriteForm(this); return false;">
	<input name="memberId" type="hidden" value="${loginedMemberId}" />
	<table>
		<colgroup>
			<col width="250">
		</colgroup>
		<tbody>
			<tr>
				<th>받는사람</th>
				<td>
					<div class="form-control-box">
						<input name="nickName" type="text" placeholder="닉네임 입력">
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
				<th>보내기</th>
				<td>
					<button class="btn btn-primary" type="submit">보내기</button>
					<button class="btn btn-danger" type="button" onclick="history.back();">취소</button>
				</td>
			</tr>
		</tbody>
	</table>
</form>

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