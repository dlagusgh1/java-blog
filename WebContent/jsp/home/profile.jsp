<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/jsp/part/head.jspf"%>

<!-- 어바웃미(프로필 팝업 포함) -->
<div class="gallerylist">
	<input type="checkbox" id="popup" /> 
	<label for="popup">프로필 보기</label>
	<div>
		<div>
			<label for="popup"></label>
			<div class="profile-box con flex ">
				<div class="profile-img flex flex-ai-c">
					<img src="/blog/resource/img/profile.png" alt="profile">
				</div>
				<div class="p-txt">
					<h1>hyeonho</h1>
					<p>
						<i class="fas fa-phone-alt">&nbsp;010-1234-5678</i><br> <br>
						<i class="far fa-envelope">&nbsp;dlagusgh1@gmail.com</i><br> <br>
						<i class="fas fa-sms">&nbsp;saygof</i>
					</p>
				</div>
			</div>
		</div>
		<label for="popup"> </label>
	</div>
	<ul>
		<li><a href="https://codepen.io/dlagusgh1/pen/OJNEqEX" target="_blank">
				<div class="screen">
					<div class="top">비전공 개발자</div>
					<div class="bottom">hyeonho</div>
					<img src="/blog/resource/img/pro3.png" />
				</div>
				<div>
					<h3>프로젝트 관련 정보</h3>
				</div>
		</a></li>
		<li><a href="${pageContext.request.contextPath}/s/home/main" target="_blank">
				<div class="screen">
					<div class="top">비전공 개발자</div>
					<div class="bottom">hyeonho</div>
					<img src="/blog/resource/img/pro1.jpg" />
				</div>
				<div>
					<h3>비전공 개발자 개인 블로그</h3>
				</div>
		</a></li>
		<li><a href="https://wori.n35.weone.kr" target="_blank">
				<div class="screen">
					<div class="top">비전공 개발자</div>
					<div class="bottom">hyeonho</div>
					<img src="/blog/resource/img/pro2.png" />
				</div>
				<div>
					<h3>스프링 부트를 이용한 우리동네</h3>
				</div>
		</a></li>
		<li><a href="https://to2.kr/bft" target="_blank">
				<div class="screen">
					<div class="top">비전공 개발자</div>
					<div class="bottom">hyeonho</div>
					<img src="/blog/resource/img/blog.jpg" />
				</div>
				<div>
					<h3>티스토리 블로그</h3>
				</div>
		</a></li>
		<li><a href="https://github.com/dlagusgh1/java-blog" target="_blank">
				<div class="screen">
					<div class="top">비전공 개발자</div>
					<div class="bottom">hyeonho</div>
					<img src="/blog/resource/img/github.jpg" />
				</div>
				<div>
					<h3>깃허브</h3>
				</div>
		</a></li>
		<li><a href="http://to2.kr/bhw" target="_blank">
				<div class="screen">
					<div class="top">비전공 개발자</div>
					<div class="bottom">hyeonho</div>
					<img src="/blog/resource/img/youtube.jpg" />
				</div>
				<div>
					<h3>유튜브</h3>
				</div>
		</a></li>
	</ul>
</div>

<%@ include file="/jsp/part/foot.jspf"%>