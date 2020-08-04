package com.sbs.java.blog.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.ArticleReply;
import com.sbs.java.blog.dto.CateItem;
import com.sbs.java.blog.dto.ChatMessage;
import com.sbs.java.blog.dto.Member;
import com.sbs.java.blog.dto.Message;
import com.sbs.java.blog.util.DBUtil;
import com.sbs.java.blog.util.SecSql;

public class ArticleDao extends Dao {

	private Connection dbConn;

	public ArticleDao(Connection dbConn) {
		this.dbConn = dbConn;

	}

	// 게시물 리스트( INNER JOIN으로 작성자명까지 함께 가져오기)
	public List<Article> getForPrintListArticles(int page, int itemsInAPage, int cateItemId, String searchKeywordType,
			String searchKeyword) {

		SecSql secSql = new SecSql();

		int limitFrom = (page - 1) * itemsInAPage;

		secSql.append("SELECT A.*, M.nickname AS extra__writer");
		secSql.append("FROM article AS A");
		secSql.append("INNER JOIN member AS M");
		secSql.append("ON A.memberId = M.id");
		secSql.append("WHERE A.displayStatus = 1");
		if (cateItemId != 0) {
			secSql.append("AND A.cateItemId = ?", cateItemId);
		}
		if (searchKeywordType.equals("title") && searchKeyword.length() > 0) {
			secSql.append("AND A.title LIKE CONCAT('%', ?, '%')", searchKeyword);
		}
		secSql.append("ORDER BY A.id DESC ");
		secSql.append("LIMIT ?, ? ", limitFrom, itemsInAPage);

		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, secSql);
		List<Article> articles = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}

		return articles;
	}

	// 게시물 상세보기(특정 게시물)
	public Article getForPrintArticle(int id) {

		SecSql secSql = new SecSql();

		secSql.append("SELECT A.*, M.nickname AS extra__writer ");
		secSql.append("FROM article AS A");
		secSql.append("INNER JOIN member AS M");
		secSql.append("ON A.memberId = M.id");
		secSql.append("WHERE 1 ");
		secSql.append("AND A.id = ? ", id);
		secSql.append("AND A.displayStatus = 1 ");

		return new Article(DBUtil.selectRow(dbConn, secSql));
	}

	// 카테고리별 게시물 수 출력
	public int getForPrintListArticlesCount(int cateItemId, String searchKeywordType, String searchKeyword) {

		SecSql secSql = new SecSql();

		secSql.append("SELECT COUNT(*) AS cnt ");
		secSql.append("FROM article ");
		secSql.append("WHERE displayStatus = 1 ");

		if (cateItemId != 0) {
			secSql.append("AND cateItemId = ? ", cateItemId);
		}

		if (searchKeywordType.equals("title") && searchKeyword.length() > 0) {
			secSql.append("AND title LIKE CONCAT('%', ?, '%')", searchKeyword);
		}

		int count = DBUtil.selectRowIntValue(dbConn, secSql);
		return count;
	}

	// 카테고리 전체 리스트 가져오기.
	public List<CateItem> getForPrintCateItems() {

		SecSql secSql = new SecSql();

		secSql.append("SELECT * ");
		secSql.append("FROM cateItem ");
		secSql.append("WHERE 1 ");
		secSql.append("ORDER BY id ASC ");

		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, secSql);
		List<CateItem> cateItems = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			cateItems.add(new CateItem(row));
		}

		return cateItems;
	}

	// 특정 카테고리 가져오기
	public CateItem getCateItem(int cateItemId) {

		SecSql secSql = new SecSql();

		secSql.append("SELECT * ");
		secSql.append("FROM cateItem ");
		secSql.append("WHERE 1 ");
		secSql.append("AND id = ? ", cateItemId);

		return new CateItem(DBUtil.selectRow(dbConn, secSql));
	}

	// 게시물 작성
	public int doWrite(int memberId, int cateItemId, String title, String body) {

		SecSql secSql = new SecSql();

		secSql.append("INSERT INTO article");
		secSql.append("SET regDate = NOW()");
		secSql.append(", updateDate = NOW()");
		secSql.append(", title = ? ", title);
		secSql.append(", body = ? ", body);
		secSql.append(", memberId = ? ", memberId);
		secSql.append(", displayStatus = '1'");
		secSql.append(", hit = '0'");
		secSql.append(", cateItemId = ?", cateItemId);

		return DBUtil.insert(dbConn, secSql);
	}

	// 게시물 삭제
	public void doDelete(int id) {

		SecSql secSql = new SecSql();

		secSql.append("DELETE FROM ");
		secSql.append("article WHERE id = ?", id);

		DBUtil.delete(dbConn, secSql);
	}

	// 게시물 수정
	public void doModify(int cateItemId, int id, String title, String body) {

		SecSql secSql = new SecSql();

		secSql.append("UPDATE article ");
		secSql.append("SET title = ?", title);
		secSql.append(", `body` = ?", body);
		secSql.append(", updateDate = NOW()");
		secSql.append(", cateItemId = ?", cateItemId);
		secSql.append(" WHERE id = ?", id);

		DBUtil.update(dbConn, secSql);
	}

	// 조회수
	public int increaseHit(int id) {
		SecSql sql = SecSql.from("UPDATE article");
		sql.append("SET hit = hit + 1");
		sql.append("WHERE id = ?", id);

		return DBUtil.update(dbConn, sql);
	}

	// 댓글 작성
	public int writeArticleReply(int articleId, int memberId, String body) {
		
		SecSql secSql = new SecSql();

		secSql.append("INSERT INTO articleReply");
		secSql.append("SET regDate = NOW()");
		secSql.append(", updateDate = NOW()");
		secSql.append(", body = ? ", body);
		secSql.append(", articleId = ? ", articleId);
		secSql.append(", displayStatus = '1'");
		secSql.append(", memberId = ?", memberId);

		return DBUtil.insert(dbConn, secSql);
	}

	// 댓글 삭제
	public void doReplyDelete(int id) {
		
		SecSql secSql = new SecSql();

		secSql.append("DELETE FROM ");
		secSql.append("articleReply WHERE id = ?", id);

		DBUtil.delete(dbConn, secSql);	
	}

	// 댓글 수정
	public void doReplyModify(int id, String body, int articleId) {

		SecSql secSql = new SecSql();

		secSql.append("UPDATE articleReply ");
		secSql.append("SET `body` = ?", body);
		secSql.append(", updateDate = NOW()");
		secSql.append(", articleId = ?", articleId);
		secSql.append(" WHERE id = ?", id);

		DBUtil.update(dbConn, secSql);
	}

	// 맴버 리스트 가져오기
	public List<Member> getForPrintListMembers() {
		
		SecSql secSql = new SecSql();

		secSql.append("SELECT id, nickName ");
		secSql.append("FROM `member` ");
		
		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, secSql);
		List<Member> members = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			members.add(new Member(row));
		}

		return members;
	}

	// 전체 댓글 수 알아오기
	public int getForPrintListRepliesCount(int id) {
		
		SecSql secSql = new SecSql();

		secSql.append("SELECT COUNT(*) AS cnt ");
		secSql.append("FROM `articleReply` ");
		secSql.append("WHERE articleId = ? ", id);

		int count = DBUtil.selectRowIntValue(dbConn, secSql);
		return count;
	}

	// 게시물 작성자 닉네임 가져오기( inner join 통해 테이블 합쳐서 게시물에 일치하는 것 비교하여 가져오기)
	public List<Article> getmemberName() {
		
		SecSql secSql = new SecSql();

		secSql.append("SELECT A.*, M.nickname AS extra__writer ");
		secSql.append("FROM article AS A ");
		secSql.append("INNER JOIN `member` AS M ");
		secSql.append("ON A.memberId = M.id ");
		secSql.append("WHERE A.displayStatus = 1 ");
		secSql.append("ORDER BY A.id DESC");
		
		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, secSql);
		List<Article> articles = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}
		
		return articles;
	}
	
	// 댓글 가져오기, 댓글 페이징
	public List<ArticleReply> getForPrintArticleReplies(int articleId, int page, int itemsInAPage, int actorId) {
		
		SecSql secSql = new SecSql();
		
		int limitFrom = (page - 1) * itemsInAPage;
		
		secSql.append("SELECT AR.*, M.nickname AS extra__writer");
		secSql.append("FROM articleReply AS AR");
		secSql.append("INNER JOIN member AS M");
		secSql.append("ON AR.memberId = M.id");
		secSql.append("WHERE AR.displayStatus = 1");
		secSql.append("AND AR.articleId = ?", articleId);
		secSql.append("ORDER BY AR.id DESC ");
		secSql.append("LIMIT ?, ? ", limitFrom, itemsInAPage);
		
		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, secSql);
		List<ArticleReply> articleReply = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			articleReply.add(new ArticleReply(row));
		}

		return articleReply;
	}
	
	// 댓글 가져오기(전체/게시물 리스트에 댓글 수 출력 위함)
	public List<ArticleReply> getForPrintListReplies() {
		
		SecSql secSql = new SecSql();

		secSql.append("SELECT * ");
		secSql.append("FROM articleReply ");
		secSql.append("WHERE displayStatus = 1");
		secSql.append("ORDER BY id DESC ");

		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, secSql);
		List<ArticleReply> replies = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			replies.add(new ArticleReply(row));
		}

		return replies;
	}
	
	// 특정 댓글 가져오기
	public ArticleReply getArticleReply(int id) {
		SecSql sql = new SecSql();

		sql.append("SELECT *");
		sql.append("FROM articleReply");
		sql.append("WHERE id = ?", id);
		
		Map<String, Object> row = DBUtil.selectRow(dbConn, sql);
		
		if ( row.isEmpty() ) {
			return null;
		}
		
		return new ArticleReply(row);
	}

	// 채팅 저장
	public void doChat(String memberId, String body) {
		
		SecSql secSql = new SecSql();

		secSql.append("INSERT INTO chat");
		secSql.append("SET regDate = NOW()");
		secSql.append(", `body` = ? ", body);
		secSql.append(", memberId = ? ", memberId);
		secSql.append(", displayStatus = 1 ");

		DBUtil.insert(dbConn, secSql);	
	}

	// 채팅 가져오기(작성자 포함)
	public List<ChatMessage> getChatNickName() {
		
		SecSql secSql = new SecSql();

		secSql.append("SELECT C.*, M.nickname AS extra__writer ");
		secSql.append("FROM chat AS C ");
		secSql.append("INNER JOIN `member` AS M ");
		secSql.append("ON C.memberId = M.id ");
		secSql.append("ORDER BY C.id ASC");
		secSql.append("LIMIT 100");
		
		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, secSql);
		List<ChatMessage> chat = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			chat.add(new ChatMessage(row));
		}
		
		return chat;
	}

	// 쪽지 작성
	public void writeMessage(String memberId, String nickName, String title, String body) {
		
		SecSql secSql = new SecSql();

		secSql.append("INSERT INTO message");
		secSql.append("SET regDate = NOW()");
		secSql.append(", updateDate = NOW()");
		secSql.append(", delDate = NOW()");
		secSql.append(", title = ? ", title);
		secSql.append(", body = ? ", body);
		secSql.append(", memberId = ? ", memberId);
		secSql.append(", nickname = ? ", nickName);
		secSql.append(", displayStatus = '1'");

		DBUtil.insert(dbConn, secSql);
	}

	// 보낸 쪽지 리스트 가져오기
	public List<Message> getSendMessage(int loginedMemberId) {
		
		SecSql secSql = new SecSql();

		secSql.append("SELECT * ");
		secSql.append("FROM message ");
		secSql.append("WHERE displayStatus = 1");
		secSql.append("AND delStatus = 0");
		secSql.append("AND memberId = ?", loginedMemberId);
		secSql.append("ORDER BY id DESC ");
		
		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, secSql);
		List<Message> messages = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			messages.add(new Message(row));
		}
		
		return messages;
	}

	// 받은 쪽지 리스트 가져오기
	public List<Message> getreceiveMessage() {
		
		SecSql secSql = new SecSql();
		
		secSql.append("SELECT G.*, M.nickname AS extra__writer ");
		secSql.append("FROM message AS G ");
		secSql.append("INNER JOIN `member` AS M ");
		secSql.append("ON G.memberId = M.id ");
		secSql.append("ORDER BY G.id DESC");
		
		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, secSql);
		List<Message> messages = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			messages.add(new Message(row));
		}
		
		return messages;
	}

}
