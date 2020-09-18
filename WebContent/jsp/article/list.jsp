<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/jsp/part/head.jspf"%>

<h1 class="title">
	${cateItemName} 게시물 리스트
</h1>

<div class="search-wrap con margin-bottom-10">
	<div class="input-box margin-top-10 flex-jc-end">
		<form action="${pageContext.request.contextPath}/s/article/list">
		<fieldset>
			<input type="hidden" name="page" value="1" /> 
			<input type="hidden" name="cateItemId" value="${param.cateItemId}" /> 
			<input type="hidden" name="searchKeywordType" value="title" /> 
			<input type="search" name="searchKeyword" value="${param.searchKeyword}" />
			<button type="submit"><i class="fa fa-search"></i></button>
		</fieldset>
		</form>
	</div>
</div>

<div class="table-box table-box-data con">
	<table>
		<colgroup>
			<col width="50" />
           	<col width="400" />
           	<col width="100" />
           	<col width="200"/>
           	<col width="75" />
           	<col width="75" />
           	<col width="100" />
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>조회수</th>
				<th>댓글수</th>
				<th>구분</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${articles}" var="article">
				<tr>
					<td>${article.id}</td>
					<td class="title-link" ><a href="./detail?id=${article.id}">${article.title}</a></td>
					<td>${article.extra.writer}</td>				
					 	<c:if test="${article.regDate == article.updateDate}">	 		
							<td>${article.regDate}</td>
						</c:if>
						<c:if test="${article.regDate != article.updateDate}">
							<td>${article.updateDate} </td>
						</c:if>
					<td>${article.hit}</td>
					<c:set var="replyCount" value="0"/>
					<c:forEach items="${articleReply}" var="reply">
						<c:if test="${article.id == reply.articleId}">
								<c:set var="replyCount" value="${replyCount + 1}" />
						</c:if>
					</c:forEach>		
					<td>${replyCount}</td>
					<c:forEach items="${cateItems}" var="cateItem">
						 <c:if test="${article.cateItemId == cateItem.id}">
							<td>${cateItem.name}</td>
						</c:if>
					</c:forEach>
					<td class="visible-on-sm-down">
	                     <a href="./detail?id=${article.id}" class="flex flex-row-wrap flex-ai-c">
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

<div class="create-box">
	<div class="create-button con flex-jc-s margin-top-10">
		<c:if test="${isLogined}">
	 		<button class="btn btn-primary" type="submit"><a href="${pageContext.request.contextPath}/s/article/write">게시물 작성</a></button>
		</c:if>
	</div>
</div>


<div class="page-box">
	<table class="page-navi flex-jc-c">
		<tr>
			<c:if test="${page != 1}">
				<td><a href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=1"><i class="fas fa-angle-double-left"></i></a></td>
				<c:set var="k" value="${page}" />
					<td><a href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=${k-1}"><i class="fas fa-angle-left"></i></a></td>
			</c:if>
			<c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">
				<td class="${i == page ? 'current' : ''}">
					<a href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=${i}" class="block">${i}</a>
				</td>
			</c:forEach>
			<c:if test="${page != totalPage}">
				<c:set var="k" value="${page}" />
					<td><a href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=${k+1}"><i class="fas fa-angle-right"></i></a></td>
				<td><a href="?cateItemId=${param.cateItemId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}&page=${totalPage}"><i class="fas fa-angle-double-right"></i></a></td>
			</c:if>
		</tr>
	</table>
</div>

<%@ include file="/jsp/part/foot.jspf"%>