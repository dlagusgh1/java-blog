package com.sbs.java.blog.service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sbs.java.blog.dao.ArticleDao;
import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.ArticleReply;
import com.sbs.java.blog.dto.CateItem;
import com.sbs.java.blog.dto.ChatMessage;
import com.sbs.java.blog.dto.Member;
import com.sbs.java.blog.dto.Message;
import com.sbs.java.blog.util.Util;

public class ArticleService extends Service {

	private ArticleDao articleDao;

	public ArticleService(Connection dbConn) {
		articleDao = new ArticleDao(dbConn);
	}

	// 카테고리 별 게시물 리스트 가져오기
	public List<Article> getForPrintListArticles(int actorId, int page, int itemsInAPage, int cateItemId, String searchKeywordType,
			String searchKeyword) {
		List<Article> articles = articleDao.getForPrintListArticles(page, itemsInAPage, cateItemId, searchKeywordType, searchKeyword);
	
		// DB에서 가져온 내역 필터링 진행
		for ( Article article : articles ) {
			updateArticleExtraDataForForPrint(article, actorId);
		}
		
		return articles;
	}
	
	// 게시물 수 확인(페이징 및 검색에 활용)
	public int getForPrintListArticlesCount(int cateItemId, String searchKeywordType, String searchKeyword) {
		return articleDao.getForPrintListArticlesCount(cateItemId, searchKeywordType, searchKeyword);
	}
	
	// 댓글 가져오기(게시물 리스트에 댓글 수 출력 위해)
	public List<ArticleReply> getForPrintListReplies() {
		return articleDao.getForPrintListReplies();
	}
	
	// 카테고리 리스트 가져오기
	public List<CateItem> getForPrintCateItems() {
		return articleDao.getForPrintCateItems();
	}
	
	// cateItemId으로 각 카테고리 가져오기
	public CateItem getCateItem(int cateItemId) {
		return articleDao.getCateItem(cateItemId);
	}
	
	// 게시물 필터링(삭제/수정 가능한지 여부 확인 등)
	private void updateArticleExtraDataForForPrint(Article article, int actorId) {
		
		// 게시물 삭제관련
		boolean deleteAvailable = Util.isSuccess(getCheckRsDeleteAvailable(article, actorId));
		article.getExtra().put("deleteAvailable", deleteAvailable);
		
		
		// 게시물 수정관련 
		boolean modifyAvailable = Util.isSuccess(getCheckRsModifyAvailable(article, actorId));
		article.getExtra().put("modifyAvailable", modifyAvailable);
	}
	
	// 게시물 작성
	public int doWrite(int memberId, int cateItemId, String title, String body) {
		return articleDao.doWrite(memberId, cateItemId, title, body);
	}
	
	// 게시물 수정
	public void doModify(int cateItemId, int id, String title, String body) {
		articleDao.doModify(cateItemId, id, title, body);
		
	}
	
	// 게시물 삭제
	public void doDelete(int id) {
		articleDao.doDelete(id);;
	}
	
	// 조회수 카운터
	public void increaseHit(int id) {
		articleDao.increaseHit(id);
	}
	
	// 게시물 수정 가능여부 체크
	private Map<String, Object> getCheckRsModifyAvailable(Article article, int actorId) {
		return getCheckRsDeleteAvailable(article, actorId);
	}
	
	// 게시물 수정 가능여부 체크
	public Map<String, Object> getCheckRsModifyAvailable(int id, int actorId) {
		return getCheckRsDeleteAvailable(id, actorId);
	}
	
	// 게시물 삭제 가능여부 체크
	public Map<String, Object> getCheckRsDeleteAvailable(int id, int actorId) {
		Article article = articleDao.getForPrintArticle(id);
		
		return getCheckRsDeleteAvailable(article, actorId);
	}
	
	private Map<String, Object> getCheckRsDeleteAvailable(Article article, int actorId) {
		Map<String, Object> rs = new HashMap<>();		
		if ( article == null ) {
			// F-1 : 실패 1
			rs.put("resultCode", "F-1");
			rs.put("msg", "존재하지 않는 게시물 입니다.");		
			return rs;
		}		
		if ( article.getMemberId() != actorId ) {
			// F-2 : 실패 2
			rs.put("resultCode", "F-2");
			rs.put("msg", "권한이 없습니다.");			
			return rs;
		}		
		rs.put("resultCode", "S-1");
		rs.put("msg", "작업이 가능합니다.");		
		return rs;
	}
	

	// 특정 게시물 가져오기(상세보기)
	public Article getForPrintArticle(int id, int actorId) {
		Article article = articleDao.getForPrintArticle(id);
		
		updateArticleExtraDataForForPrint(article, actorId);
		
		return article;
	}
	
	// 댓글 작성
	public int writeArticleReply(int articleId, int memberId, String body) {
		return articleDao.writeArticleReply(articleId, memberId, body);		
	}
	
	// 댓글 삭제
	public void deleteArticleReply(int id) {
		articleDao.doReplyDelete(id);	
	}

	// 댓글 수정
	public void doReplyModify(int id, String body, int articleId) {
		articleDao.doReplyModify(id, body, articleId);
		
	}
	
	// 댓글 수정, 삭제여부 확인
	private void updateArticleReplyExtraDataForPrint(ArticleReply articleReply, int actorId) {
		boolean deleteAvailable = Util.isSuccess(getReplyCheckRsDeleteAvailable(articleReply, actorId));
		articleReply.getExtra().put("deleteAvailable", deleteAvailable);

		boolean modifyAvailable = Util.isSuccess(getReplyCheckRsModifyAvailable(articleReply, actorId));
		articleReply.getExtra().put("modifyAvailable", modifyAvailable);
	}

	private Map<String, Object> getReplyCheckRsModifyAvailable(ArticleReply articleReply, int actorId) {
		return getReplyCheckRsDeleteAvailable(articleReply, actorId);
	}
	
	// 댓글 수정 전 체크
	public Map<String, Object> getReplyCheckRsModifyAvailable(int id, int actorId) {
		ArticleReply articleReply = getArticleReply(id);
		
		return getReplyCheckRsModifyAvailable(articleReply, actorId);
	}
	
	// 댓글 삭제 전 체크
	public Map<String, Object> getReplyCheckRsDeleteAvailable(int id, int actorId) {
		ArticleReply articleReply = this.getArticleReply(id);
		
		return getReplyCheckRsDeleteAvailable(articleReply, actorId);
	}

	// 댓글 가져오기(수정, 삭제 전 확인
	public ArticleReply getArticleReply(int id) {
		return articleDao.getArticleReply(id);
	}
	
	private Map<String, Object> getReplyCheckRsDeleteAvailable(ArticleReply articleReply, int actorId) {
		Map<String, Object> rs = new HashMap<>();

		if (articleReply == null) {
			rs.put("resultCode", "F-1");
			rs.put("msg", "존재하지 않는 댓글 입니다.");

			return rs;
		}

		if (articleReply.getMemberId() != actorId) {
			rs.put("resultCode", "F-2");
			rs.put("msg", "권한이 없습니다.");

			return rs;
		}

		rs.put("resultCode", "S-1");
		rs.put("msg", "작업이 가능합니다.");

		return rs;
	}
	
	// 댓글 가져오기, 댓글 페이징
	public List<ArticleReply> getForPrintArticleReplies(int id, int page, int itemsInAPage, int actorId) {
		List<ArticleReply> articleReplies = articleDao.getForPrintArticleReplies(id, page, itemsInAPage, actorId);

		for (ArticleReply articleReply : articleReplies) {
			updateArticleReplyExtraDataForPrint(articleReply, actorId);
		}

		return articleReplies;
	}
	
	// 전체 댓글 수 알아오기
	public int getForPrintListRepliesCount(int id) {
		return articleDao.getForPrintListRepliesCount(id);
	}

	// member 리스트 가져오기
	public List<Member> getForPrintListMembers() {
		return articleDao.getForPrintListMembers();
	}

	// 게시물 작성자 닉네임 가져오기
	public List<Article> getmemberName() {
		return articleDao.getmemberName();
	}
	
	// 채팅 작성
	public void doChat(String memberId, String body) {
		articleDao.doChat(memberId, body);
	}

	// 채팅 가져오기(작성자 포함)
	public List<ChatMessage> getChatNickName() {
		return articleDao.getChatNickName();
	}
	
	// 쪽지 작성
	public void writeMessage(String memberId, String nickName, String title, String body) {
		articleDao.writeMessage(memberId, nickName, title, body);
	}

	// 보낸 쪽지 리스트(페이징 포함)
	public List<Message> getSendMessage(int loginedMemberId, int page, int itemsInAPage) {
		return articleDao.getSendMessage(loginedMemberId, page, itemsInAPage);
	}

	// 받은 쪽지 리스트(페이징 포함)
	public List<Message> getreceiveMessage(String loginedMemberNickName, int page, int itemsInAPage) {
		return articleDao.getreceiveMessage(loginedMemberNickName, page, itemsInAPage);
	}
	
	// 받은 쪽지 수 확인
	public int getForPrintListRMessageCount(String nickName) {
		return articleDao.getForPrintListRMessageCount(nickName);
	}

	// 보낸 쪽지 수 확인
	public int getForPrintListSMessageCount(int loginedMemberId) {
		return articleDao.getForPrintListSMessageCount(loginedMemberId);
	}
	
}
