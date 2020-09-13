<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!-- JSTL -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- JSTL 데이터 포맷 -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>쪽지함</title>

<div class="visible-on-sm-down">
	<%@ include file="/jsp/part/head.jspf"%>
</div>
<!-- 구글 폰트 불러오기 -->
<!-- rotobo(400/900), notosanskr(400/900) -->
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;900&family=Roboto:wght@400;900&display=swap" rel="stylesheet">
	
<!-- 폰트어썸 불러오기 -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.1/css/all.min.css">

<!-- 제이쿼리 불러오기 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="/blog/resource/js/home/main.js"></script>

<!-- sha256 암호화 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<!-- 파비콘 -->
<link rel="shortcut icon" href="/blog/resource/img/logo5.ico">

<!-- cs, js 불러오기 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/home/main.css">
<script src="${pageContext.request.contextPath}/resource/js/home/main.js"></script>

<div class="messenger-top-bar">
	<div class="messenger-title-wrap flex-jc-c">
		<div><a href="${pageContext.request.contextPath}/s/article/receiveMessages"">쪽지</a></div>
	</div>
	<div class="messenger-bottom-wrap flex-jc-c">
		<div><a href="${pageContext.request.contextPath}/s/article/receiveMessages">받은 쪽지함</a></div>
		<div><a href="${pageContext.request.contextPath}/s/article/sendMessages">보낸 쪽지함</a></div>
		<div><a href="${pageContext.request.contextPath}/s/article/messagesWrite">쪽지 쓰기</a></div>
	</div>
</div>
	
</head>
<body style="padding-top: 0px;">