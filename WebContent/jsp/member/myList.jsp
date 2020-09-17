<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/jsp/part/head.jspf"%>

<h1 class="title">
	${loginedMember.nickName}님 게시물 리스트
</h1>

<div class="table-box table-box-data con">
	<table>
		<colgroup>
			<col width="100" />
           	<col width="500" />
           	<col width="200" />
           	<col width="100" />
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성일</th>
				<th>조회수</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${articles}" var="article">
				<tr>
					<td>${article.id}</td>
					<td class="title-link" ><a href="${pageContext.request.contextPath}/s/article/detail?id=${article.id}">${article.title}</a></td>
						<c:if test="${article.regDate == article.updateDate}">
							<td>${article.regDate}</td>
						</c:if>
						<c:if test="${article.regDate != article.updateDate}">
							<td>${article.updateDate}</td>
						</c:if>
					<td>${article.hit}</td>
					<td class="visible-on-sm-down">
		            <a href="${pageContext.request.contextPath}/s/article/detail?id=${article.id}" class="flex flex-row-wrap flex-ai-c">
	               		<span class="badge badge-primary bold margin-right-10">${article.id}</span>
                            <div class="title flex-1-0-0 text-overflow-el ">${article.title}</div>
	                     </a>
	                         <div class="writer inline-block">${article.extra.writer}</div>
	                         &nbsp;|&nbsp;
                         	<div class="reg-date inline-block">${article.regDate}</div>
	            	</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<div class="total-box con flex-ai-c">
	<div class="total">
		총 게시물 수 : ${articleCount}
	</div>
</div>

<div class="replyPage-box">
	<table class="page-navi flex flex-jc-c">
		<tbody>
			<tr>
				<c:if test="${page != 1}">
					<c:set var="k" value="${page}" />
						<td><a href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=${k-1}">prev</a></td>
				</c:if>
				<c:forEach var="i" begin="1" end="${totalPage}" step="1">
					<td class="${i == page ? 'current' : ''}">
						<a href="?page=${i}" class="block">${i}</a>
					</td>
				</c:forEach>
				<c:if test="${page != totalPage}">
					<c:set var="k" value="${page}" />
						<td><a href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=${k+1}">next</a></td>
				</c:if>
			</tr>
		</tbody>
	</table>
</div>

<%@ include file="/jsp/part/foot.jspf"%>