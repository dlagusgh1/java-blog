package com.sbs.java.blog.controller;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.ArticleReply;
import com.sbs.java.blog.dto.CateItem;
import com.sbs.java.blog.dto.ChatMessage;
import com.sbs.java.blog.dto.Member;
import com.sbs.java.blog.dto.Message;
import com.sbs.java.blog.util.Util;

public class ArticleController extends Controller {
	public ArticleController(Connection dbConn, String actionMethodName, HttpServletRequest req,
			HttpServletResponse resp) {
		super(dbConn, actionMethodName, req, resp);
	}

	@Override
	public String getControllerName() {
		return "article";
	}

	public void beforeAction() {
		super.beforeAction();
		// 이 메서드는 게시물 컨트롤러의 모든 액션이 실행되기 전에 실행된다.
		// 필요없다면 지워도 된다.
	}

	public String doAction() {
		switch (actionMethodName) {
		case "list":
			return actionList();
		case "detail":
			return actionDetail();
		case "write":
			return actionWrite();
		case "doWrite":
			return actionDoWrite();
		case "doDelete":
			return actionDoDelete();
		case "modify":
			return actionModify();
		case "doModify":
			return actionDoModify();
		case "doWriteReply":
			return actionDoWriteReply();
		case "doDeleteReply":
			return actionDoDeleteReply();
		case "modifyReply":
			return actionModifyReply();
		case "doModifyReply":
			return actionDoModifyReply();
		case "chat":
			return actionChat();
		case "doChat":
			return actionDoChat();
		case "messagesWrite":
			return actionMessagesWrite();
		case "doMessagesWrite":
			return actionDoMessagesWrite();
		case "sendMessages":
			return actionSendMessages();
		case "receiveMessages":
			return actionReceiveMessages();
		}

		return "";
	}

	// 쪽지 작성 폼
	private String actionMessagesWrite() {

		return "article/messagesWrite.jsp";
	}
	
	// 쪽지 보내기 기능
	private String actionDoMessagesWrite() {
		
		String memberId = Util.getString(req, "memberId");
		String nickName = Util.getString(req, "nickName");
		String title = Util.getString(req, "title");
		String body = Util.getString(req, "body");
		
		boolean isExistNickName = memberService.isJoinableNickname(nickName);
		
		if (isExistNickName) {
			System.out.println(isExistNickName);
			return String.format("html:<script> alert('존재하지 않는 닉네임 입니다.'); history.back();</script>");
		}
		
		articleService.writeMessage(memberId, nickName, title, body);
		
		return String.format("html:<script> location.replace('sendMessages'); </script>");
	}

	// 받은 쪽지함
	private String actionReceiveMessages() {
		
		Member loginedMember = (Member) req.getAttribute("loginedMember");
		
		List<Message> receiveMessages = articleService.getreceiveMessage();
		req.setAttribute("receiveMessages", receiveMessages);	

		return "article/receiveMessages.jsp";
	}

	// 보낸 쪽지함
	private String actionSendMessages() {
		
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");
		
		List<Message> sendMessages = articleService.getSendMessage(loginedMemberId);
		req.setAttribute("sendMessages", sendMessages);		
		
		return "article/sendMessages.jsp";
	}

	// 채팅 입력 폼
	private String actionChat() {
		
		// 채팅 가져오기 (작성자 이름 포함)
		List<ChatMessage> chatMessage = articleService.getChatNickName();
		req.setAttribute("chatMessage", chatMessage);
		
		return "article/chat.jsp";
	}
	
	// 채팅 기능
	private String actionDoChat() {
		
		String memberId = Util.getString(req, "memberId");
		String body = Util.getString(req, "body");
		
		if (Util.empty("body") || body.equals("")) {
			return String.format("html:<script> alert('채팅 내용을 입력해 주세요.'); history.back();</script>");
		}
		
		articleService.doChat(memberId, body);
		
		return String.format("html:<script> location.replace('chat'); </script>");
	}
	

	// 댓글 수정 입력 폼
	private String actionModifyReply() {

		String articleId = Util.getString(req, "articleId");
		String id = Util.getString(req, "id");
		String body = Util.getString(req, "body");

		req.setAttribute("articleId", articleId);
		req.setAttribute("id", id);
		req.setAttribute("body", body);

		return "article/replyModify.jsp";
	}

	// 댓글 수정 기능
	private String actionDoModifyReply() {
		
		if (Util.empty(req, "id")) {
			return "html:id를 입력해주세요.";
		}

		if (Util.isNum(req, "id") == false) {
			return "html:id를 정수로 입력해주세요.";
		}

		int articleId = Util.getInt(req, "articleId");
		int id = Util.getInt(req, "id");
		String body = req.getParameter("body");
		
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");
		
		Map<String, Object> getReplyCheckRsModifyAvailableRs = articleService.getReplyCheckRsModifyAvailable(id, loginedMemberId);
		
		if (Util.isSuccess(getReplyCheckRsModifyAvailableRs) == false) {
			return "html:<script> alert('" + getReplyCheckRsModifyAvailableRs.get("msg") + "'); history.back(); </script>";
		}
		
		articleService.doReplyModify(id, body, articleId);
		
		String redirectUri = Util.getString(req, "redirectUri", "detail?id=" + articleId);

		return String.format("html:<script> alert('" + id + "번 댓글이 수정되었습니다.'); location.replace('" + redirectUri + "'); </script>");
	}

	// 댓글 삭제
	private String actionDoDeleteReply() {
		
		if (Util.empty(req, "id")) {
			return "html:id를 입력해주세요.";
		}

		if (Util.isNum(req, "id") == false) {
			return "html:id를 정수로 입력해주세요.";
		}

		int articleId = Util.getInt(req, "articleId");
		
		int id = Util.getInt(req, "id");
		
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");
		
		Map<String, Object> getReplyCheckRsDeleteAvailableRs = articleService.getReplyCheckRsDeleteAvailable(id, loginedMemberId);

		if (Util.isSuccess(getReplyCheckRsDeleteAvailableRs) == false) {
			return String.format("html:<script> alert('" + getReplyCheckRsDeleteAvailableRs.get("msg") + "'); history.back(); </script>");
		}

		articleService.deleteArticleReply(id);

		String redirectUri = Util.getString(req, "redirectUri", "detail?id=" + articleId);
		
		return String.format("html:<script> alert('" + id + "번 댓글이 삭제되었습니다.'); location.replace('" + redirectUri + "'); </script>");
	}

	// 댓글 작성 기능
	private String actionDoWriteReply() {
		
		if (Util.empty(req, "articleId")) {
			return "html:articleId를 입력해주세요.";
		}

		if (Util.isNum(req, "articleId") == false) {
			return "html:articleId를 정수로 입력해주세요.";
		}

		int articleId = Util.getInt(req, "articleId");

		int loginedMemberId = (int) req.getAttribute("loginedMemberId");
		String body = Util.getString(req, "body");
		
		int id = articleService.writeArticleReply(articleId, loginedMemberId, body);
		
		String redirectUri = Util.getString(req, "redirectUri", "detail?id=" + articleId);
		
		return String.format("html:<script> alert('" + id + "번 댓글이 작성되었습니다.'); location.replace('" + redirectUri + "'); </script>");
	}

	// 게시물 수정 입력 폼( detail에서 수정 클릭 시 오는 부분 )
	private String actionModify() {

		if (Util.empty(req, "id")) {
			return "html:id를 입력해주세요.";
		}

		if (Util.isNum(req, "id") == false) {
			return "html:id를 정수로 입력해주세요.";
		}

		int id = Util.getInt(req, "id");

		articleService.increaseHit(id);
		
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");
		Article article = articleService.getForPrintArticle(id, loginedMemberId);

		req.setAttribute("article", article);

		return "article/modify.jsp";
	}

	// 게시물 수정 기능( modify.jsp에서 입력 후 수정 클릭시 오는 부분 )
	private String actionDoModify() {
		
		if (Util.empty(req, "id")) {
			return "html:id를 입력해주세요.";
		}

		if (Util.isNum(req, "id") == false) {
			return "html:id를 정수로 입력해주세요.";
		}

		int id = Util.getInt(req, "id");

		int loginedMemberId = (int) req.getAttribute("loginedMemberId");
		
		Map<String, Object> getCheckRsModifyAvailableRs = articleService.getCheckRsModifyAvailable(id, loginedMemberId);

		if (Util.isSuccess(getCheckRsModifyAvailableRs) == false) {
			return String.format("html:<script> alert('" + getCheckRsModifyAvailableRs.get("msg") + "'); history.back(); </script>");
		}
		
		int cateItemId = Util.getInt(req, "cateItemId");
		String title = Util.getString(req, "title");
		String body = Util.getString(req, "body");

		articleService.doModify(cateItemId, id, title, body);

		return String.format("html:<script> alert('" + id + "번 게시물이 수정되었습니다.'); location.replace('detail?id=%s'); </script>", id);
	}

	// 게시물 삭제 기능
	private String actionDoDelete() {

		if (Util.empty(req, "id")) {
			return String.format("html: id를 입력해주세요.");
		}

		if (Util.isNum(req, "id") == false) {
			return String.format("html: id를 정수로 입력해주세요.");
		}

		int id = Util.getInt(req, "id");
		
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		Map<String, Object> getCheckRsDeleteAvailableRs = articleService.getCheckRsDeleteAvailable(id, loginedMemberId);

		if (Util.isSuccess(getCheckRsDeleteAvailableRs) == false) {
			return "html:<script> alert('" + getCheckRsDeleteAvailableRs.get("msg") + "'); history.back(); </script>";
		}

		articleService.doDelete(id);

		return String.format("html:<script> alert('" + id + "번 게시물이 삭제되었습니다.'); location.replace('list'); </script>");
	}

	// 게시물 작성 입력 폼
	private String actionWrite() {

		return "article/write.jsp";
	}

	// 게시물 작성(카테고리, 제목, 내용 입력받음)
	private String actionDoWrite() {
		int memberLevel = Util.getInt(req, "memberLevel");
		int memberId = Util.getInt(req, "memberId");
		int cateItemId = Util.getInt(req, "cateItemId");
		String title = Util.getString(req, "title");
		String body = Util.getString(req, "body");
		
		System.out.println(memberLevel);
		
		if( cateItemId == 4 && memberLevel != 10 ) {
			return String.format("html:<script> alert('공지사항에는 관리자만 게시물 작성이 가능합니다.'); history.back(); </script>");
		}
		
		int id = articleService.doWrite(memberId, cateItemId, title, body);

		return String.format("html:<script> alert('" + id + "번 게시물이 생성되었습니다.'); location.replace('list'); </script>");
	}

	// 게시물 상세보기
	private String actionDetail() {

		if (Util.empty(req, "id")) {
			return String.format("html: id를 입력해주세요.");
		}

		if (Util.isNum(req, "id") == false) {
			return String.format("html: id를 정수로 입력해주세요.");
		}

		int id = Util.getInt(req, "id");

		articleService.increaseHit(id);

		int loginedMemberId = (int) req.getAttribute("loginedMemberId");
		Article article = articleService.getForPrintArticle(id, loginedMemberId);

		req.setAttribute("article", article);

		// 댓글 페이징 (한 페이지 당 5개의 게시물이 출력되도록 되어있음.)
		int page = 1;

		if (!Util.empty(req, "page") && Util.isNum(req, "page")) {
			page = Util.getInt(req, "page");
		}

		int itemsInAPage = 5;
		int totalCount = articleService.getForPrintListRepliesCount(id);
		int totalPage = (int) Math.ceil(totalCount / (double) itemsInAPage);

		req.setAttribute("totalCount", totalCount);
		req.setAttribute("totalPage", totalPage);
		req.setAttribute("page", page);
		
		// 댓글 리스트(댓글 작성자명 포함하여 가져오기, 페이징)
		List<ArticleReply> articleReplies = articleService.getForPrintArticleReplies(id, page, itemsInAPage, loginedMemberId);
		req.setAttribute("articleReplies", articleReplies);

		// 게시물 작성자 이름 출력 위한 articleNickName(상세 내역 상단)
		List<Article> articleNickName = articleService.getmemberName();
		req.setAttribute("articleNickName", articleNickName);
		
		// 댓글수 출력(특정 게시물)
		int articleReplyCount = articleService.getForPrintListRepliesCount(id);
		req.setAttribute("articleReplyCount", articleReplyCount);
		
		// 댓글 페이징 번호 제한
		int pageCount = 5;
		int startPage = ((page - 1) / pageCount) * pageCount + 1;
		int endPage = startPage + pageCount - 1;
		
		if( totalPage < page) {
			page = totalPage;
		}
		if ( endPage > totalPage) {
			endPage = totalPage;
		}
		
		req.setAttribute("pageCount", pageCount);
		req.setAttribute("startPage", startPage);
		req.setAttribute("endPage", endPage);

		return "article/detail.jsp";
	}

	// 게시물 리스트
	private String actionList() {
		// 게시판 리스팅 되는데 걸리는 시간 측정용
		//long startTime = System.nanoTime();
		
		int page = 1;

		if (!Util.empty(req, "page") && Util.isNum(req, "page")) {
			page = Util.getInt(req, "page");
		}

		int cateItemId = 0;

		if (!Util.empty(req, "cateItemId") && Util.isNum(req, "cateItemId")) {
			cateItemId = Util.getInt(req, "cateItemId");
		}

		String cateItemName = "전체";

		if (cateItemId != 0) {
			CateItem cateItem = articleService.getCateItem(cateItemId);
			cateItemName = cateItem.getName();
		}
		
		req.setAttribute("cateItemName", cateItemName);

		String searchKeywordType = "";

		if (!Util.empty(req, "searchKeywordType")) {
			searchKeywordType = Util.getString(req, "searchKeywordType");
		}

		String searchKeyword = "";

		if (!Util.empty(req, "searchKeyword")) {
			searchKeyword = Util.getString(req, "searchKeyword");
		}
		
		// 게시물 페이징 (한 페이지 당 10개의 게시물이 출력되도록 되어있음.)
		int itemsInAPage = 10;
		int totalCount = articleService.getForPrintListArticlesCount(cateItemId, searchKeywordType, searchKeyword);
		int totalPage = (int) Math.ceil(totalCount / (double) itemsInAPage); // 몇개의 페이지가 있을지 체크

		req.setAttribute("totalCount", totalCount);
		req.setAttribute("totalPage", totalPage);
		req.setAttribute("page", page);
		
		int loginedMemberId = (int) req.getAttribute("loginedMemberId");

		// 게시물 리스트(게시물 작성자명 포함하여 가져오기, 페이징)
		List<Article> articles = articleService.getForPrintListArticles(loginedMemberId, page, itemsInAPage, cateItemId,
				searchKeywordType, searchKeyword);
		req.setAttribute("articles", articles);
		
		// 댓글 가져오기(전체/게시물 리스트에 댓글 수 출력 위함)
		List<ArticleReply> articleReply = articleService.getForPrintListReplies();
		req.setAttribute("articleReply", articleReply);
		
		// 카테고리 이름 출력 위함
		List<CateItem> cateItems = articleService.getForPrintCateItems();
		req.setAttribute("cateItems", cateItems);
		
		// 게시물 하단 페이징 번호 제한
		int pageCount = 5;
		int startPage = ((page - 1) / pageCount) * pageCount + 1;
		int endPage = startPage + pageCount - 1;
		
		if( totalPage < page) {
			page = totalPage;
		}
		if ( endPage > totalPage) {
			endPage = totalPage;
		}
		
		req.setAttribute("pageCount", pageCount);
		req.setAttribute("startPage", startPage);
		req.setAttribute("endPage", endPage);
		
		// 게시판 리스팅 되는데 걸리는 시간 측정용
		//long endTime = System.nanoTime();
		//long estimatedTime = endTime - startTime;
		//nano seconds to seconds
		//double seconds = estimatedTime / 1000000000.0;

		return "article/list.jsp";
	}
	
	
}
