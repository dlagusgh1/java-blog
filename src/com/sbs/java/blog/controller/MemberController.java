package com.sbs.java.blog.controller;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.jdbc.Blob;
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
			return actionJoin(); // 회원가입 폼
		case "doJoin":
			return actionDoJoin(); // 회원가입 기능
		case "authEmail":
			return actionAuthEmail(); // 이메일 인증 기능
		case "getNickNameDup":
			return actionGetNickNameDup(); // 회원가입 닉네임 중복체크
		case "getEmailDup":
			return actionGetEmailDup(); // 회원가입 이메일 중복체크
		case "getLoginIdDup":
			return actionGetLoginIdDup(); // 회원가입 로그인 아이디 중복체크
		case "passwordForPrivate":
			return actionPasswordForPrivate(); // 회원정보(비밀번호) - 비밀번호 변경 전 비밀번호 확인 폼
		case "doPasswordForPrivate":
			return actionDoPasswordForPrivate(); // 회원정보(비밀번호) - 비밀번호 변경 전 확인 기능
		case "modifyPrivate":
			return actionModifyPrivate();	// 회원정보(비밀번호) 변경 폼
		case "doModifyPrivate":
			return actionDoModifyPrivate(); // 회원정보(비밀번호) 변경 기능
		case "passwordConfirm":
			return actionpasswordConfirm(); // 마이페이지 진입 전 비밀번호 확인 폼
		case "DoPasswordConfirm":
			return actionDoPasswordConfirm(); // 마이페이지 진입 전 비밀번호 확인 기능
		case "findAccount":
			return actionFindAccount();	// 아이디 찾기
		case "doFindLoginId":
			return actionDoFindLoginId();	// 아이디 찾기 기능
		case "findPw":
			return actionFindPw(); // 비밀번호 찾기
		case "doFindPw":
			return actionDoFindLoginPw(); // 비밀번호 찾기 기능
		case "mypage":
			return actionMypage();	// 마이페이지
		case "myList":
			return actionMyList();	// 마이페이지 내 게시물 리스트
		case "myReplyList":
			return actionMyReplyList();	// 마이페이지 내 댓글 리스트
		case "myImgModify":
			return actionMyImgModify();	// 마이페이지 내 프로필 이미지 변경 폼
		case "doMyImgModify":
			return actionDoMyImgModify();	// 마이페이지 내 프로필 이미지 변경 기능
		case "siteStatistics":
			return actionSiteStatistics(); // 통계 기능
		case "doDelete":
			return actionDoDelete();	
		}

		return "";
	}
	
	
	// 마이페이지 이미지 수정 폼
	private String actionMyImgModify() {
		// TODO Auto-generated method stub
		return "member/myImgModify.jsp";
	}
	
	// 마이페이지 이미지 수정 기능
	private String actionDoMyImgModify() {
		
		Member loginedMember = (Member) req.getAttribute("loginedMember");
		
		String memberImg = req.getParameter("memberImg");
		
		memberService.getModifyMemberImg(loginedMember.getLoginId() ,memberImg);
		
		session.removeAttribute("loginedMemberId");
		
		return String.format("html:<script> alert('프로필 사진이 변경되었습니다.\\n적용을 위해 다시 로그인 해주세요.'); location.replace('login');</script>");
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
	
	// 회원정보 - 비밀번호 변경 폼
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
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");
		String authCode = req.getParameter("authCode");

		if (memberService.isValidModifyPrivateAuthCode(loginedMemberId, authCode) == false) {
			req.setAttribute("jsAlertMsg", "비밀번호를 다시 체크해주세요.");
			req.setAttribute("redirectUri", "../member/passwordForPrivate");
			
			return "common/data.jsp";
		}
		
		String useTempPassword = attrService.getValue("member__" + loginedMemberId + "__extra__useTempPassword");
		
		if ( useTempPassword.equals("1") ) {
			attrService.remove("member__" + loginedMemberId + "__extra__useTempPassword");
		}

		String loginPw = req.getParameter("loginPwReal");

		memberService.modify(loginedMemberId, loginPw);
		Member loginedMember = (Member) req.getAttribute("loginedMember");
		loginedMember.setLoginPw(loginPw); // 크게 의미는 없지만, 의미론적인 면에서 해야 하는

		req.setAttribute("jsAlertMsg", "개인정보가 수정되었습니다.");
		req.setAttribute("redirectUri", "../home/main");
		
		return "common/data.jsp";
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

	// 마이페이지 들어가기 전 비밀번호 확인 폼
	private String actionpasswordConfirm() {		
		
		return "member/passwordConfirm.jsp";
	}
	
	// 마이페이지 들어가기 전 비밀번호 확인 기능
	private String actionDoPasswordConfirm() {
		
		String loginId = req.getParameter("loginId");
		String loginPw = req.getParameter("loginPwReal");	
		
		if ( memberService.loginedFact(loginId, loginPw) != 1) {
			return String.format("html:<script> alert('ID/PW가 일치하지 않습니다.'); history.back(); </script>");
		} 		
		
		return String.format("html:<script>location.replace('mypage'); </script>");
	}
	
	// 마이페이지 기능
	private String actionMypage() {
		
		Member loginedMember = (Member) req.getAttribute("loginedMember");
		
		int memberId = (int) session.getAttribute("loginedMemberId");
		
		int articleCount = memberService.getArticleCount(memberId);
		
		int replyCount = memberService.getReplyCount(memberId);
		
		String emailAuthed = attrService.getValue("member__" + memberId + "__extra__emailAuthed");
		
		req.setAttribute("articleCount", articleCount);
		req.setAttribute("replyCount", replyCount);
		req.setAttribute("emailAuthed", emailAuthed);

		return "member/mypage.jsp";
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
		String email = Util.getString(req, "email");

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null || member.getEmail().equals(email) == false) {
			req.setAttribute("jsAlertMsg", "일치하는 회원이 없습니다.");
			req.setAttribute("jsHistoryBack", true);
			return "common/data.jsp";
		}

		memberService.notifyTempLoginPw(member);
		req.setAttribute("jsAlertMsg", "메일로 임시패스워드가 발송되었습니다.");
		req.setAttribute("redirectUri", "../member/login");

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
	
	// 회원 탈퇴 ( 작업 중 - 아이디를 지우기 보다는 delStatus 1로 설정 탈퇴된 회원으로 표시		 )
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

	// 로그아웃
	private String actionDoLogout() {
			
		session.removeAttribute("loginedMemberId");

		req.setAttribute("jsAlertMsg", "로그아웃 되었습니다.");
		req.setAttribute("redirectUri", "../home/main");
		
		return "common/data.jsp";
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
		
		Member getMember = memberService.getMember(loginId, loginPw);
		String emailAuthed = attrService.getValue("member__" + getMember.getId() + "__extra__emailAuthed");
		
		if ( emailAuthed.equals("") ) {
			
			String emailAuthCode = attrService.getValue("member__" + getMember.getId() + "__extra__emailAuthCode");
			
			mailService.send(getMember.getEmail(), "이메일 인증", "이메일 인증 부탁드립니다.<br><a href=\"https://dlagusgh1.my.iu.gy/blog/s/member/authEmail?email="+ getMember.getEmail() +"&authCode=" + emailAuthCode + "&memberId=" + getMember.getId() + "\" target=\"_blank\"><br>이메일 인증하기</a>");
			
			req.setAttribute("jsAlertMsg", "이메일 인증이 필요합니다.\\n보내드린 인증메일 확인 후 이메일 인증 부탁드립니다.");
			req.setAttribute("redirectUri", "../home/main");

			return "common/data.jsp";
		
		} else {
			
			Member member = memberService.doLogin(loginId, loginPw);
			
			session.setAttribute("loginedMemberId", member.getId());
			
			String useTempPassword =  attrService.getValue("member__" + member.getId() + "__extra__useTempPassword");
			
			if ( useTempPassword.equals("1") ) {
				req.setAttribute("jsAlertMsg", "현재 임시패스워드를 사용중 입니다. \\n비밀번호 변경 부탁드립니다.");
				req.setAttribute("redirectUri", "../member/passwordConfirm");

				return "common/data.jsp";
			}
			
			// 현재 날짜 가져오기
			long systemTime = System.currentTimeMillis();			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);			 
			String currentTime = formatter.format(systemTime);	
			
			String lastTime = attrService.getValue("member__" + member.getId() + "__extra__lastPasswordChangeDate");
			
			String[] timeBits = currentTime.split("-");
			int year = Integer.parseInt(timeBits[0]);
			int month = Integer.parseInt(timeBits[1]);
			int day = Integer.parseInt(timeBits[2]);
					
			req.setAttribute("jsAlertMsg", loginId + "님 로그인되었습니다. \\n환영합니다.");
			req.setAttribute("redirectUri", "../home/main");
			
			return "common/data.jsp";
		}
		
	}

	// 이메일 인증 기능
	private String actionAuthEmail() {

		String email = (String) req.getParameter("email");
		String authCode = (String) req.getParameter("authCode");
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
		
		req.setAttribute("jsAlertMsg", "가입을 축하 합니다.\\n이메일로 보내드린 링크로 접속하여 이메일 인증 후 이용 부탁드립니다.");
		req.setAttribute("redirectUri", "../member/login");

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
