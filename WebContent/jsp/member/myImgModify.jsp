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
<title>프로필 이미지 변경</title>

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
 
<div class="mypage-img con flex-jc-c" style="margin-top: 30px; text-align: center;">
	<form action="doMyImgModify">
		<div class="mypage-img-wrap">
			<c:if test="${loginedMember.myImg == null}">
				<img src="" alt="" /> 프로필이 없습니다.<br>아래에 변경하고자하는 이미지 주소를 입력해주세요.
			</c:if>
			<c:if test="${loginedMember.myImg != null}">
				<img src="${loginedMember.myImg}" alt="프로필 이미지"><br>아래에 변경하고자하는 이미지 주소를 입력해주세요.
			</c:if>
			<div class="form-row" style="margin: 10px;">
				<div class="input">
					<input name="memberImg" type="url"/> 
				</div>
			</div>
			<div class="form-row">
				<div class="input">
					<input class="pro_ch" type="submit" value="프로필 변경">
				</div>
			</div>
		</div>
	</form>
</div>

</head>
<body style="padding-top: 0px;">