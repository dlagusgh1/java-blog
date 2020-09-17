<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/jsp/part/head.jspf"%>

<h1 class="title">통계</h1>

<div class="mypage-box con flex-jc-c">
	<form method="POST" class="table-box con" action="statistics">
		<input type="hidden" name="redirectUri" value="/blog/s/home/main">
		<table>
			<colgroup>
				<col width="250">
			</colgroup>
			<tbody>
				<tr>
					<th>전체 회원 수</th>
					<td>
						<div class="form-control-box">
							${memberCount} 명
						</div>
					</td>
				</tr>
				<tr>
					<th>전체 게시물 수</th>
					<td>
						${articleCount} 개
					</td>
				</tr>
				<tr>
					<th>전체 댓글 수</th>
					<td>
						${articleReplyCount} 개
					</td>
				</tr>
				<tr>
					<th>방문자</th>
					<td>
						- 명
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

<%@ include file="/jsp/part/foot.jspf"%>