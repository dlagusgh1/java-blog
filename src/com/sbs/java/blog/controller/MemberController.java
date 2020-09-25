package com.sbs.java.blog.controller;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.ArticleReply;
import com.sbs.java.blog.dto.Member;
import com.sbs.java.blog.util.Util;


public class MemberController extends Controller {

	public MemberController(Connection dbConn, String actionMethodName, HttpServletRequest req,
			HttpServletResponse resp) {
		super(dbConn, actionMethodName, req, resp);
	}

	@Override
	public String getControllerName() {
		return "member";
	}

	@Override
	public String doAction() {
		switch (actionMethodName) {
		case "doLogout":
			return actionDoLogout();
		case "login":
			return actionLogin();
		case "doLogin":
			return actionDoLogin();
		case "join":
			return actionJoin();
		case "doJoin":
			return actionDoJoin();
		case "reAuthEmail":
			return reAuthEmail();	// 인증 메일 재 발송 기능
		case "authEmail":
			return actionAuthEmail(); // 이메일 인증 기능
		case "getNickNameDup":
			return actionGetNickNameDup();
		case "getEmailDup":
			return actionGetEmailDup();
		case "getLoginIdDup":
			return actionGetLoginIdDup();
		case "passwordForPrivate":
			return actionPasswordForPrivate(); // 회원정보(비밀번호) - 비밀번호 확인 폼
		case "doPasswordForPrivate":
			return actionDoPasswordForPrivate(); // 회원정보(비밀번호) - 비밀번호 확인 기능
		case "modifyPrivate":
			return actionModifyPrivate();
		case "doModifyPrivate":
			return actionDoModifyPrivate();
		case "passwordConfirm":
			return actionpasswordConfirm(); // 마이페이지 - 비밀번호 확인 폼
		case "doPasswordConfirm":
			return actionDoPasswordConfirm(); // 마이페이지 - 비밀번호 확인 기능
		case "findAccount":
			return actionFindAccount();
		case "doFindLoginId":
			return actionDoFindLoginId();
		case "findPw":
			return actionFindPw();
		case "doFindPw":
			return actionDoFindLoginPw();
		case "mypage":
			return actionMypage();
		case "myList":
			return actionMyList();
		case "myReplyList":
			return actionMyReplyList();
		case "myImgModify":
			return actionMyImgModify();
		case "doMyImgModify":
			return actionDoMyImgModify();
		case "myIntroModify":
			return actionMyIntroModify();
		case "doMyIntroModify":
			return actionDoMyIntroModify();
		case "siteStatistics":
			return actionSiteStatistics();
		case "doDelete":
			return actionDoDelete();	
		}

		return "";
	}
	
	// 회원가입 입력 폼
	private String actionJoin() {

		return "member/join.jsp";
	}

	// 회원가입 기능
	private String actionDoJoin() {

		String loginId = req.getParameter("loginId");
		String name = req.getParameter("name");
		String nickName = req.getParameter("nickName");
		String loginPw = req.getParameter("loginPwReal");
		String email = req.getParameter("email");
		
		boolean isJoinableLoginId = memberService.isJoinableLoginId(loginId);
		boolean isJoinableNickname = memberService.isJoinableNickname(nickName);
		boolean isJoinableEmail = memberService.isJoinableEmail(email);
		
		// 아이디 중복체크
		if ( isJoinableLoginId == false ) {
			return String.format("html:<script> alert('%s(은)는 이미 사용중인 아이디 입니다.'); history.back(); </script>", loginId);
		}
		
		// 닉네임 중복체크
		if ( isJoinableNickname == false ) {
			return String.format("html:<script> alert('%s(은)는 이미 사용중인 닉네임 입니다.'); history.back(); </script>", nickName);
		}
		
		// 이메일 중복체크
		if ( isJoinableEmail == false ) {
			return String.format("html:<script> alert('%s(은)는 이미 사용중인 이메일 입니다.'); history.back(); </script>", email);
		}
		
		int memberId;
		memberId = memberService.getTotalMemberCount();
		
		if ( memberId == 0 ) {
			memberId = 1;
		}
		
		memberService.join(memberId, loginId, name, nickName, loginPw, email);
		
		// 비밀번호 변경일자 등록(일정기간 지낡경우 알림 위함)
		memberService.setLastPasswordChangeDate(memberId);
		
		req.setAttribute("jsAlertMsg", "가입을 환영 합니다.\\n보내드린 메일 확인 후 이용 부탁드립니다.");
		req.setAttribute("redirectUri", "../member/login");

		return "common/data.jsp";
	}
	
	// 회원가입 로그인 아이디 중복체크(AJAX이용)
	private String actionGetLoginIdDup() {		
		String loginId = req.getParameter("loginId");
		boolean isJoinableLoginId = memberService.isJoinableLoginId(loginId);
		if (isJoinableLoginId) {
			if(loginId.length() <= 3) {
				return "json:{\"msg\":\"아이디를 3자 이상 입력해주세요.\", \"resultCode\": \"F-1\", \"loginId\":\"" + loginId + "\"}";
			} else {
				return "json:{\"msg\":\"사용할 수 있는 아이디 입니다.\", \"resultCode\": \"S-1\", \"loginId\":\"" + loginId + "\"}";
			}
		} else {
			return "json:{\"msg\":\"사용할 수 없는 아이디 입니다.\", \"resultCode\": \"F-1\", \"loginId\":\"" + loginId + "\"}";
		}	
	}
	
	// 회원가입 닉네임 중복체크(AJAX이용)
	private String actionGetNickNameDup() {		
		String nickName = req.getParameter("nickName");
		boolean isJoinableNickName = memberService.isJoinableNickname(nickName);
		if (isJoinableNickName) {
			if(nickName.length() <= 1) {
				return "json:{\"msg\":\"닉네임을 2자 이상 입력해주세요.\", \"resultCode\": \"F-1\", \"nickName\":\"" + nickName + "\"}";
			} else {
				return "json:{\"msg\":\"사용할 수 있는 닉네임 입니다.\", \"resultCode\": \"S-1\", \"nickName\":\"" + nickName + "\"}";
			}
		} else {
			return "json:{\"msg\":\"사용할 수 없는 닉네임 입니다.\", \"resultCode\": \"F-1\", \"nickName\":\"" + nickName + "\"}";
		}
	}
	
	// 회원가입 이메일 중복체크(AJAX이용)
	private String actionGetEmailDup() {	
		String email = req.getParameter("email");
		boolean isJoinableEmail = memberService.isJoinableEmail(email);
		if (isJoinableEmail) {
			return "json:{\"msg\":\"사용할 수 있는 이메일 입니다.\", \"resultCode\": \"S-1\", \"email\":\"" + email + "\"}";
		} else {
			return "json:{\"msg\":\"사용할 수 없는 이메일 입니다.\", \"resultCode\": \"F-1\", \"email\":\"" + email + "\"}";
		}	
	}

	// 로그인 입력 폼
	private String actionLogin() {

		return "member/login.jsp";
	}

	// 로그인 기능
	private String actionDoLogin() {	
		String loginId = req.getParameter("loginId");
		String loginPw = req.getParameter("loginPwReal");				

		int isExistMemberLoginIdPw = memberService.loginedFact(loginId, loginPw);		
		// 먼저, 아이디, 비밀번호 일치하는게 있는지 검토
		if ( isExistMemberLoginIdPw != 1 ) {
			return String.format("html:<script> alert('ID/PW가 일치하지 않습니다.'); history.back(); </script>");
		}
		if ( session.getAttribute("loginedMemberId") != null ) {
			return "html:<script> alert('다른 회원이 로그인 중 입니다.'); history.back(); </script>";
		}
			
		Member member = memberService.doLogin(loginId, loginPw);
		session.setAttribute("loginedMemberId", member.getId());
		
		String useTempPassword =  attrService.getValue("member__" + member.getId() + "__extra__useTempPassword");	
		
		if ( useTempPassword.equals("1") ) {
			req.setAttribute("jsAlertMsg", "현재 임시패스워드를 사용중 입니다. \\n비밀번호 변경 부탁드립니다.");
			req.setAttribute("redirectUri", "../member/passwordForPrivate");
			return "common/data.jsp";
		}	
		
		String current = Util.getNowDateStr();
		
		String last = memberService.getLastPasswordChangeDate(member.getId());
		
		long differenceDate = Util.getTime(current, last);
		
		if ( differenceDate > 30 ) {

			req.setAttribute("jsAlertMsg", "장기간 동일한 비밀번호를 사용중 입니다.\\n비밀번호 변경 부탁드립니다.");	
			req.setAttribute("redirectUri", "../member/passwordForPrivate");
			
			return "common/data.jsp";
		}
		
		
		req.setAttribute("jsAlertMsg", loginId + "님 로그인되었습니다. \\n환영합니다.");
		req.setAttribute("redirectUri", "../home/main");
		
		return "common/data.jsp";

	}
	
	// 로그아웃
	private String actionDoLogout() {
			
		session.removeAttribute("loginedMemberId");

		req.setAttribute("jsAlertMsg", "로그아웃 되었습니다.");
		req.setAttribute("redirectUri", "../home/main");
		
		return "common/data.jsp";
	}
	
	// 회원 아이디 찾기 폼
	private String actionFindAccount() {
		return "member/findAccount.jsp";
	}

	// 회원 아이디 찾기 기능
	private String actionDoFindLoginId() {
		
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		
		Member member = memberService.getMemberByNameAndEmail(name, email);
		
		if ( member == null ) {
			req.setAttribute("jsAlertMsg", "일치하는 회원이 없습니다.");
			req.setAttribute("jsHistoryBack", true);
			return "common/data.jsp";
		}
		
		req.setAttribute("jsAlertMsg", "일치하는 회원을 찾았습니다.\\n아이디 : " + member.getLoginId());
		req.setAttribute("jsHistoryBack", true);
		
		return "common/data.jsp";
	}
	
	
	// 회원 비밀번호 찾기 폼
	private String actionFindPw() {
		
		// 암호화 전 비밀번호
		String tempPw = Util.numberGen();
	
		req.setAttribute("tempPw", tempPw);
		
		return "member/findAccount.jsp";
	}
	
	// 회원 비밀번호 찾기 기능
	private String actionDoFindLoginPw() {
		
		String loginId = Util.getString(req, "loginId");
		String name = Util.getString(req, "name");
		String email = Util.getString(req, "email");

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null || member.getEmail().equals(email) == false && member.getEmail().equals(name) == false) {
			req.setAttribute("jsAlertMsg", "일치하는 회원이 없습니다.");
			req.setAttribute("jsHistoryBack", true);
			return "common/data.jsp";
		}

		memberService.notifyTempLoginPw(member);
		memberService.setLastPasswordChangeDate(member.getId());
		
		req.setAttribute("jsAlertMsg", "메일로 임시패스워드가 발송되었습니다.");
		req.setAttribute("redirectUri", "../member/login");

		return "common/data.jsp";
	}
	
	// 회원정보 - 비밀번호 변경 전 확인 폼
	private String actionPasswordForPrivate() {
		return "member/passwordForPrivate.jsp";
	}

	// 회원정보 - 비밀번호 변경 전 확인 기능
	private String actionDoPasswordForPrivate() {
		
		String loginPw = req.getParameter("loginPwReal");

		Member loginedMember = (Member) req.getAttribute("loginedMember");
		
		int loginedMemberId = loginedMember.getId();
		
		if (loginedMember.getLoginPw().equals(loginPw)) {
			
			String authCode = memberService.genModifyPrivateAuthCode(loginedMemberId);

			return String.format("html:<script> location.replace('modifyPrivate?authCode=" + authCode + "'); </script>");
		}

		return String.format("html:<script> alert('비밀번호를 다시 입력해주세요.'); history.back(); </script>");
	}

	// 마이페이지 들어가기 전 비밀번호 확인 폼
	private String actionpasswordConfirm() {			
		return "member/passwordConfirm.jsp";
	}
	
	// 마이페이지 들어가기 전 비밀번호 확인 기능
	private String actionDoPasswordConfirm() {
		
		String loginId = req.getParameter("loginId");
		String loginPw = req.getParameter("loginPwReal");	
		
		String redirectUri = req.getParameter("redirectUri");
		System.out.println("확인 " + redirectUri);
		if ( memberService.loginedFact(loginId, loginPw) != 1) {
			return String.format("html:<script> alert('ID/PW가 일치하지 않습니다.'); history.back(); </script>");
		} 		

		return String.format("html:<script>location.replace('mypage'); </script>");
	}
		
	
	// 회원정보 - 비밀번호 변경 전 비밀번호 확인 폼
	private String actionModifyPrivate() {
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		String authCode = req.getParameter("authCode");
		if (memberService.isValidModifyPrivateAuthCode(loginedMemberId, authCode) == false) {
			req.setAttribute("jsAlertMsg", "비밀번호를 다시 체크해주세요.");
			req.setAttribute("redirectUri", "../member/passwordForPrivate");
			
			return "common/data.jsp";
		}

		return "member/modifyPrivate.jsp";
	}
		
	// 회원정보 - 비밀번호 변경 기능
	private String actionDoModifyPrivate() {
		int memberId = (int) req.getAttribute("loginedMemberId");
		String authCode = req.getParameter("authCode");

		if (memberService.isValidModifyPrivateAuthCode(memberId, authCode) == false) {
			req.setAttribute("jsAlertMsg", "비밀번호를 다시 체크해주세요.");
			req.setAttribute("redirectUri", "../member/passwordForPrivate");
			
			return "common/data.jsp";
		}
		
		String useTempPassword = attrService.getValue("member__" + memberId + "__extra__useTempPassword");
		
		if ( useTempPassword.equals("1") ) {
			attrService.remove("member__" + memberId + "__extra__useTempPassword");
		}
		
		String loginPw = req.getParameter("loginPwReal");

		memberService.modify(memberId, loginPw);
		
		memberService.setLastPasswordChangeDate(memberId);

		req.setAttribute("jsAlertMsg", "개인정보가 수정되었습니다.");
		req.setAttribute("redirectUri", "../home/main");
		
		return "common/data.jsp";
	}
	
	// 마이페이지 기능
	private String actionMypage() {
			
		int memberId = (int) session.getAttribute("loginedMemberId");	
		int articleCount = memberService.getArticleCount(memberId);		
		int replyCount = memberService.getReplyCount(memberId);
		
		String emailAuthed = attrService.getValue("member__" + memberId + "__extra__emailAuthed");
		
		req.setAttribute("articleCount", articleCount);
		req.setAttribute("replyCount", replyCount);
		req.setAttribute("emailAuthed", emailAuthed);

		return "member/mypage.jsp";
	}
	
	// 마이페이지 소개글 수정 폼
	private String actionMyIntroModify() {
		return "member/myIntroModify.jsp";
	}
	
	// 마이페이지 소개글 수정 기능
	private String actionDoMyIntroModify() {
		Member loginedMember = (Member) req.getAttribute("loginedMember");
		
		String memberIntro = req.getParameter("memberIntro");
		
		memberService.getModifyMemberIntro(loginedMember.getLoginId() ,memberIntro);
		
		req.setAttribute("jsAlertMsg", "프로필 소개글이 변경되었습니다.");
		req.setAttribute("redirectUri", "passwordConfirm");

		return "common/data.jsp";
	}

	// 마이페이지 이미지 수정 폼
	private String actionMyImgModify() {
		return "member/myImgModify.jsp";
	}
	
	// 마이페이지 이미지 수정 기능
	private String actionDoMyImgModify() {
		
		Member loginedMember = (Member) req.getAttribute("loginedMember");
		
		String memberImg = req.getParameter("memberImg");
		
		memberService.getModifyMemberImg(loginedMember.getLoginId() ,memberImg);
		
		req.setAttribute("jsAlertMsg", "프로필 사진이 변경되었습니다.");
		req.setAttribute("redirectUri", "passwordConfirm");

		return "common/data.jsp";
	}

	// 내 댓글 리스트
	private String actionMyReplyList() {
		
		int memberId = (int) session.getAttribute("loginedMemberId");
		
		// 내 댓글 리스트 페이징
		int page = 1;

		if (!Util.empty(req, "page") && Util.isNum(req, "page")) {
			page = Util.getInt(req, "page");
		}

		int articleReplyCount = memberService.getReplyCount(memberId);	
		
		int itemsInAPage = 10;
		int totalCount = articleReplyCount;
		int totalPage = (int) Math.ceil(totalCount / (double) itemsInAPage);
		
		List<ArticleReply> replies = memberService.getForPrintListMyReplies(memberId, page, itemsInAPage);
		
		req.setAttribute("totalCount", totalCount);
		req.setAttribute("totalPage", totalPage);
		req.setAttribute("page", page);
		
		req.setAttribute("replies", replies);	
		req.setAttribute("articleReplyCount", articleReplyCount);	
		
		return "member/myReplyList.jsp";
	}

	// 내 게시물 리스트
	private String actionMyList() {
		
		int memberId = (int) session.getAttribute("loginedMemberId");
		
		// 내 게시물 리스트 페이징
		int page = 1;

		if (!Util.empty(req, "page") && Util.isNum(req, "page")) {
			page = Util.getInt(req, "page");
		}

		int articleCount = memberService.getArticleCount(memberId);	
		
		int itemsInAPage = 10;
		int totalCount = articleCount;
		int totalPage = (int) Math.ceil(totalCount / (double) itemsInAPage);
		
		List<Article> articles = memberService.getForPrintListMyArticles(memberId, page, itemsInAPage);
		
		req.setAttribute("totalCount", totalCount);
		req.setAttribute("totalPage", totalPage);
		req.setAttribute("page", page);
		
		req.setAttribute("articles", articles);	
		req.setAttribute("articleCount", articleCount);	
		
		return "member/myList.jsp";
	}
	
	// 이메일 인증 재 발송 기능
	private String reAuthEmail() {
		Member loginedMember = (Member) req.getAttribute("loginedMember");
		
		memberService.reSendEmailAuthCode(loginedMember.getId(), loginedMember.getEmail());
		
		return String.format("html:<script>alert('인증 메일이 재 발송 되었습니다.\\n확인 후 이메일 인증 부탁드립니다.'); history.back(); </script>");
	}

	// 이메일 인증 기능
	private String actionAuthEmail() {

		String email = (String) req.getParameter("email");
		int memberId = Integer.parseInt(req.getParameter("memberId"));
		
		String emailAuthed = attrService.getValue("member__" + memberId + "__extra__emailAuthed");
		
		boolean isEmailAuthedEmpty = Util.empty(emailAuthed);
		
		// 이메일 인증 중복 방지
		if ( isEmailAuthedEmpty != true ) {
			req.setAttribute("jsAlertMsg", "이메일 인증이 이미 완료되었습니다.");
			req.setAttribute("redirectUri", "../member/login");

			return "common/data.jsp";
		}
			
		Member member = memberService.getMemberById(memberId);
		
		boolean isExistMemberId = memberId == member.getId();
		boolean isExistMemberEmail = email.equals(member.getEmail());
		String mailAuthCode = attrService.getValue("member__" + memberId + "__extra__emailAuthCode");
		
		if (isExistMemberId == false || isExistMemberEmail == false || mailAuthCode == null ) {
			req.setAttribute("jsAlertMsg", "이메일 인증에 사용되는 정보가 잘못되었습니다.");
			req.setAttribute("redirectUri", "../member/login");

			return "common/data.jsp";
		}
	
		attrService.setValue("member__" + memberId + "__extra__emailAuthed", email);
		
		req.setAttribute("jsAlertMsg", "이메일 인증이 완료되었습니다.");
		req.setAttribute("redirectUri", "../member/login");

		return "common/data.jsp";
	}
	
	// 회원 탈퇴 ( 작업 중 - 아이디를 지우기 보다는 delStatus 1로 설정 탈퇴된 회원으로 표시 )
	private String actionDoDelete() {
		
		String loginId = req.getParameter("loginId");
		String loginPw = req.getParameter("loginPwReal");
		String memberDeleteConfirm = req.getParameter("memberDeleteConfirm");
				
		if ( memberDeleteConfirm.equals("탈퇴합니다") == false ) {
			return String.format("html:<script> alert('입력하신 문구가 일치하지 않습니다.ㅠㅠ'); history.back(); </script>");
		} 		
		
		memberService.doMemberDelete(loginId, loginPw);
		
		session.removeAttribute("loginedMemberId");

		req.setAttribute("jsAlertMsg", "탈퇴가 정상적으로 완료되었습니다.");
		req.setAttribute("redirectUri", "../home/main");
		
		return "common/data.jsp";
	}

	// 통계 기능
	private String actionSiteStatistics() {
		
		int memberCount = memberService.getTotalMemberCount();
		int articleCount = memberService.getTotalArticleCount();
		int articleReplyCount = memberService.getTotalReplyCount();
		
		req.setAttribute("memberCount", memberCount);
		req.setAttribute("articleCount", articleCount);	
		req.setAttribute("articleReplyCount", articleReplyCount);
		
		return "member/siteStatistics.jsp";
	}

}
