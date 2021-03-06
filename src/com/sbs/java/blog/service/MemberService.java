package com.sbs.java.blog.service;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import com.sbs.java.blog.config.Config;
import com.sbs.java.blog.dao.MemberDao;
import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.ArticleReply;
import com.sbs.java.blog.dto.Member;
import com.sbs.java.blog.util.Util;

public class MemberService extends Service {
	private MailService mailService;
	private MemberDao memberDao;
	private AttrService attrService;

	public MemberService(Connection dbConn, MailService mailService, AttrService attrService) {
		memberDao = new MemberDao(dbConn);
		this.mailService = mailService;
		this.attrService = attrService;
	}
	
	/* 회원가입 관련 */
	// 회원가입 기능
	public void join(int memberId, String loginId, String name, String nickName, String loginPw, String email) {
		
		String to = email;
		
		String emailAuthCode =  UUID.randomUUID().toString();
		
		memberDao.join(memberId, loginId, name, nickName, loginPw, email);	
		
		// 현재 날짜 가져오기
		long systemTime = System.currentTimeMillis();			
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);			 
		String currentTime = formatter.format(systemTime);		 
		
		attrService.setValue("member__" + memberId + "__extra__lastPasswordChangeDate", currentTime);		
		attrService.setValue("member__" + memberId + "__extra__emailAuthCode", emailAuthCode);

		// 메일 발송(가입축하 & 메일 인증)
		String title = String.format("가입을 환영합니다!", Config.getSiteName());
		String body = String.format("<div><img src="+ Config.getSiteLogo + " style=\"height:150px; width:300px; background-color: #4BAF4B; margin-bottom: 20px; padding:10px; border-radius:20px; \"/></div><br>가입을 축하드립니다.<br>이메일 인증 부탁드립니다.<br> <a href=\"https://blog.n35.weone.kr/blog/s/member/authEmail?email="+ email +"&authCode=" + emailAuthCode + "&memberId=" + memberId + "\" target=\"_blank\"><br>이메일 인증하기</a>");
		mailService.send(to, title, body);				
	}
	
	// 회원가입 시 ID 중복유무 체크
	public boolean isJoinableLoginId(String loginId) {
		return memberDao.isJoinableLoginId(loginId);
	}
	
	// 회원가입 시 이메일 중복유무 체크
	public boolean isJoinableEmail(String email) {
		return memberDao.isJoinableEmail(email);
	}
	
	// 회원가입 시 닉네임 중복유무 체크
	public boolean isJoinableNickname(String nickName) {
		return memberDao.isJoinableNickname(nickName);
	}

	
	/* 로그인 관련 */
	// 로그인 기능
	public Member doLogin(String loginId, String loginPw) {
		return memberDao.doLogin(loginId, loginPw);
	}

	// 로그인 시 ID, PW 존재유무 체크
	public int loginedFact(String loginId, String loginPw) {
		return memberDao.loginedFact(loginId, loginPw);
	}

	
	/* 회원정보 찾기 */
	// 회원 아이디 찾기
	public Member getMemberByNameAndEmail(String name, String email) {
		
		return memberDao.getMemberByNameAndEmail(name, email);
	}
	
	// 회원 비밀번호 찾기(임시 비밀번호 발급)
	public void notifyTempLoginPw(Member member) {
		String to = member.getEmail();
		String tempPasswordOrigin = Util.getTempPassword(6);
		String tempPassword = Util.sha256(tempPasswordOrigin);

		modify(member.getId(), tempPassword);
		
		// 이메일 발송
		attrService.setValue("member", member.getId(), "extra", "useTempPassword", "1");

		String title = String.format("[%s] 임시패스워드 발송", Config.getSiteName());
		String body = String.format("<div><img src="+ Config.getSiteLogo + " style=\"height:150px; width:300px; background-color: #4BAF4B; margin-bottom: 20px; padding:10px; " + "border-radius:20px; \"/></div><br><div>임시 패스워드 : %s</div>\n", tempPasswordOrigin);
		mailService.send(to, title, body);
	}
	
	/* 회원정보 수정 */
	// 회원 정보 수정
	public void modify(int actorId, String loginPw) {	
		
		memberDao.modify(actorId, loginPw);
		
		attrService.remove("member", actorId, "extra", "useTempPassword");
		
		setLastPasswordChangeDate(actorId);
		
	}
	
	/* 마이페이지 */
	// 로그인 대상이 작성한 게시물 수 출력
	public int getArticleCount(int memberId) {
		return memberDao.getArticleCount(memberId);
	}

	// 로그인 대상이 작성한 댓글 수 출력
	public int getReplyCount(int memberId) {
		return memberDao.getReplyCount(memberId);
	}
	
	// 프로필 소개글 변경
	public void getModifyMemberIntro(String memberLoginId, String memberIntro) {
		memberDao.getModifyMemberIntro(memberLoginId, memberIntro);
	}
	
	// 프로필 이미지 변경
	public void getModifyMemberImg(String memberLoginId, String memberImg) {
		memberDao.getModifyMemberImg(memberLoginId, memberImg);
	}

	// 로그인 대상이 작성한 댓글 리스트 출력( 댓글 리스팅 포함)
	public List<ArticleReply> getForPrintListMyReplies(int memberId, int page, int itemsInAPage) {
		return memberDao.getForPrintListMyReplies(memberId, page, itemsInAPage);
	}
	
	// 로그인 대상이 작성한 게시물 리스트 출력(게시물 리스팅 포함)
	public List<Article> getForPrintListMyArticles(int memberId, int page, int itemsInAPage) {
		return memberDao.getForPrintListMyArticles(memberId, page, itemsInAPage);
	}	
	
	// 로그인 대상의 memberId로 member정보 가져오기
	public Member getMemberById(int loginedMemberId) {
		return memberDao.getMemberById(loginedMemberId);
	}

	/* 통계 */
	// 전체 맴버 수
	public int getTotalMemberCount() {
		return memberDao.getTotalMemberCount();
	}
	// 전체 게시물 수
	public int getTotalArticleCount() {
		return memberDao.getTotalArticleCount();
	}

	// 전체 댓글 수
	public int getTotalReplyCount() {
		return memberDao.getTotalReplyCount();
	}

	// 회원 정보 가져오기
	public Member getMember(String loginId, String loginPw) {
		return memberDao.getMember(loginId, loginPw);
	}
	
	// 회원 정보 전체 가져오기
	public List<Member> getMember() {
		return memberDao.getMember();
	}
	
	// 로그인 대상 아이디로 대상정보 가져오기
	public Member getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}
	
	// 회원 탈퇴
	public void doMemberDelete(String loginId, String loginPw) {
		memberDao.doMemberDelete(loginId, loginPw);
	}
	
	/* 각종 유틸 */
	// 인증메일 재 발송 기능
	public void reSendEmailAuthCode(int memberId, String email) {
		
		String emailAuthCode = attrService.getValue("member__" + memberId + "__extra__emailAuthCode");
		
		mailService.send(email, "이메일 인증", "<div><img src="+ Config.getSiteLogo + " style=\"height:150px; width:300px; background-color: #4BAF4B; margin-bottom: 20px; padding:10px; border-radius:20px; \"/></div><br>아래 링크를 클릭하여 이메일 인증 부탁드립니다.<br><a href=\"https://blog.n35.weone.kr/blog/s/member/authEmail?email="+ email +"&authCode=" + emailAuthCode + "&memberId=" + memberId + "\" target=\"_blank\"><br>이메일 인증하기</a>");
	}
	
	public boolean isNeedToChangePasswordForTemp(int actorId) {
		return attrService.getValue("member", actorId, "extra", "useTempPassword").equals("1");
	}

	public Member getMemberByIdForSession(int actorId) {
		Member member = getMemberById(actorId);

		boolean isNeedToChangePasswordForTemp = isNeedToChangePasswordForTemp(member.getId());
		member.getExtra().put("isNeedToChangePasswordForTemp", isNeedToChangePasswordForTemp);

		return member;
	}
	
	public String genModifyPrivateAuthCode(int actorId) {
		String authCode = UUID.randomUUID().toString();

		attrService.setValue("member__" + actorId + "__extra__modifyPrivateAuthCode", authCode);	
		
		return authCode;
	}
	
	public boolean isValidModifyPrivateAuthCode(int actorId, String authCode) {
		String authCodeOnDB = attrService.getValue("member__" + actorId + "__extra__modifyPrivateAuthCode");

		return authCodeOnDB.equals(authCode);
	}
	
	// 비밀번호 변경 시 attr에 변경일자 등록(장기간 미 변경 시 알림 출력할 때 사용)
	public void setLastPasswordChangeDate(int memberId) {
		
		String currentTime = Util.getNowDateStr();
		
		attrService.setValue("member__" + memberId + "__extra__lastPasswordChangeDate", currentTime);
	}
	
	// attr에서 비밀번호 변경 일자 가져오기
	public String getLastPasswordChangeDate(int memberId) {

		return attrService.getValue("member__" + memberId + "__extra__lastPasswordChangeDate");
	}
	
}
