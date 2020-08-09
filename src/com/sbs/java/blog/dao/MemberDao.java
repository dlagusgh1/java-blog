package com.sbs.java.blog.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mysql.cj.jdbc.Blob;
import com.sbs.java.blog.dto.Article;
import com.sbs.java.blog.dto.ArticleReply;
import com.sbs.java.blog.dto.Member;
import com.sbs.java.blog.util.DBUtil;
import com.sbs.java.blog.util.SecSql;

public class MemberDao extends Dao {

	private Connection dbConn;

	public MemberDao(Connection dbConn) {
		this.dbConn = dbConn;

	}

	// 회원가입
	public void join(int memberId, String loginId, String name, String nickName, String loginPw, String email) {

		SecSql secSql = new SecSql();

		secSql.append("INSERT INTO `member` ");
		secSql.append("SET regDate = NOW()");
		secSql.append(", updateDate = NOW()");
		secSql.append(", loginId = ?", loginId);
		secSql.append(", name = ?", name);
		secSql.append(", nickname = ?", nickName);
		secSql.append(", loginPw = ?", loginPw);
		secSql.append(", email = ?", email);

		DBUtil.insert(dbConn, secSql);
	}

	// 로그인
	public Member doLogin(String loginId, String loginPw) {

		SecSql secSql = new SecSql();

		secSql.append("SELECT * ");
		secSql.append("FROM `member` ");
		secSql.append("WHERE 1 ");
		secSql.append("AND loginId = ? ", loginId);
		secSql.append("AND loginPw = ? ", loginPw);
		
		return new Member(DBUtil.selectRow(dbConn, secSql));
	}

	public int loginedFact(String loginId, String loginPw) {
		
		SecSql secSql = new SecSql();
		
		secSql.append("SELECT COUNT(*)");
		secSql.append("FROM `member` ");
		secSql.append("WHERE loginId = ? ", loginId);
		secSql.append("AND loginPw = ? ", loginPw);
		
		return DBUtil.selectRowIntValue(dbConn, secSql);
	}

	// 회원가입 시 ID 중복유무 체크
	public boolean isJoinableLoginId(String loginId) {
		
		SecSql secSql = SecSql.from("SELECT COUNT(*) AS cnt");
		secSql.append("FROM `member`");
		secSql.append("WHERE loginId = ?", loginId);

		return DBUtil.selectRowIntValue(dbConn, secSql) == 0;
	}

	// 회원가입 시 닉네임 중복유무 체크
	public boolean isJoinableNickname(String nickName) {
		
		SecSql secSql = SecSql.from("SELECT COUNT(*) AS cnt");
		secSql.append("FROM `member`");
		secSql.append("WHERE nickname = ?", nickName);

		return DBUtil.selectRowIntValue(dbConn, secSql) == 0;
	}

	// 회원가입 시 이메일 중복유무 체크
	public boolean isJoinableEmail(String email) {
		
		SecSql secSql = SecSql.from("SELECT COUNT(*) AS cnt");
		secSql.append("FROM `member`");
		secSql.append("WHERE email = ?", email);

		return DBUtil.selectRowIntValue(dbConn, secSql) == 0;
	}

	// 회원 번호로 회원 정보 가져오기
	public Member getMemberById(int loginedMemberId) {
		SecSql secSql = SecSql.from("SELECT *");
		secSql.append("FROM `member`");
		secSql.append("WHERE id = ?", loginedMemberId);

		return new Member(DBUtil.selectRow(dbConn, secSql));
	}

	// 로그인 대상이 작성한 게시물 수 출력
	public int getArticleCount(int memberId) {
		
		SecSql secSql = SecSql.from("SELECT COUNT(*) AS cnt ");
		secSql.append("FROM article ");
		secSql.append("WHERE memberId = ?", memberId);
		
		int count = DBUtil.selectRowIntValue(dbConn, secSql);
		
		return count;
	}

	// 로그인 대상이 작성한 댓글 수 출력
	public int getReplyCount(int memberId) {

		SecSql secSql = SecSql.from("SELECT COUNT(*) AS cnt ");	
		secSql.append("FROM articleReply ");
		secSql.append("WHERE memberId = ?", memberId);
		
		int count = DBUtil.selectRowIntValue(dbConn, secSql);
		
		return count;
	}

	// 로그인 대상이 작성한 게시물 리스트 출력
	public List<Article> getForPrintListMyArticles(int memberId, int page, int itemsInAPage) {
		
		SecSql secSql = new SecSql();
		
		int limitFrom = (page - 1) * itemsInAPage;

		secSql.append("SELECT * ");
		secSql.append("FROM article ");
		secSql.append("WHERE memberId = ? ", memberId);
		secSql.append("ORDER BY id DESC");
		secSql.append("LIMIT ?, ? ", limitFrom, itemsInAPage);

		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, secSql);
		List<Article> articles = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			articles.add(new Article(row));
		}

		return articles;
	}

	// 로그인 대상이 작성한 댓글 리스트 출력
	public List<ArticleReply> getForPrintListMyReplies(int memberId, int page, int itemsInAPage) {
		
		SecSql secSql = new SecSql();
		
		int limitFrom = (page - 1) * itemsInAPage;

		secSql.append("SELECT * ");
		secSql.append("FROM articleReply ");
		secSql.append("WHERE memberId = ? ", memberId);
		secSql.append("ORDER BY id DESC");
		secSql.append("LIMIT ?, ? ", limitFrom, itemsInAPage);

		List<Map<String, Object>> rows = DBUtil.selectRows(dbConn, secSql);
		List<ArticleReply> replies = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			replies.add(new ArticleReply(row));
		}

		return replies;
	}

	// 전체 맴버 수 출력
	public int getTotalMemberCount() {
		
		SecSql secSql = SecSql.from("SELECT COUNT(*) AS cnt ");	
		secSql.append("FROM `member` ");
		
		int count = DBUtil.selectRowIntValue(dbConn, secSql);
		
		return count;
	}

	// 전체 게시물 수 출력
	public int getTotalArticleCount() {
		
		SecSql secSql = SecSql.from("SELECT COUNT(*) AS cnt ");	
		secSql.append("FROM article ");
		
		int count = DBUtil.selectRowIntValue(dbConn, secSql);
		
		return count;
	}

	// 전체 댓글 수 출력
	public int getTotalReplyCount() {
		
		SecSql secSql = SecSql.from("SELECT COUNT(*) AS cnt ");	
		secSql.append("FROM articleReply ");
		
		int count = DBUtil.selectRowIntValue(dbConn, secSql);
		
		return count;
	}

	// 회원정보 아이디 찾기 전 체크
	public int memberFact(String name, String email) {
		
		SecSql secSql = new SecSql();
		
		secSql.append("SELECT COUNT(*)");
		secSql.append("FROM `member` ");
		secSql.append("WHERE name = ? ", name);
		secSql.append("AND email = ? ", email);
		
		return DBUtil.selectRowIntValue(dbConn, secSql);
	}

	// 회원정보 비밀번호 찾기 전 체크
	public int memberRealFact(String loginId, String name, String email) {
		
		SecSql secSql = new SecSql();
		
		secSql.append("SELECT COUNT(*)");
		secSql.append("FROM `member` ");
		secSql.append("WHERE loginId = ? ", loginId);
		secSql.append("AND name = ? ", name);
		secSql.append("AND email = ? ", email);
		
		return DBUtil.selectRowIntValue(dbConn, secSql);
	}
	
	// 회원 아이디 찾기
	public Member getMemberByNameAndEmail(String name, String email) {
		
		SecSql secSql = SecSql.from("SELECT * ");	
		secSql.append("FROM `member` ");
		secSql.append("WHERE NAME = ?", name);
		secSql.append("AND email = ?", email);
		
		Map<String, Object> row = DBUtil.selectRow(dbConn, secSql);

		if (row.isEmpty()) {
			return null;
		}

		return new Member(row);
	}

	// 회원 비밀번호 찾기
	public Member getDoFindMemberPw(String loginId, String name, String email) {
		
		SecSql secSql = SecSql.from("SELECT * ");	
		
		secSql.append("FROM `member` ");
		secSql.append("WHERE loginId = ?", loginId);
		secSql.append("AND name = ?", name);
		secSql.append("AND email = ?", email);
		
		return new Member(DBUtil.selectRow(dbConn, secSql));
	}

	// 회원 탈퇴
	public void doMemberDelete(String loginId, String loginPw) {
			
		SecSql secSql = SecSql.from("DELETE FROM `member` ");
		
		secSql.append("WHERE loginId = ?", loginId);
		secSql.append("AND loginPw = ?", loginPw);
		
		DBUtil.delete(dbConn, secSql);
	}
	
	// 회원정보 수정(비밀번호) 기능
	public void modify(int actorId, String loginPw) {
		
		SecSql secSql = SecSql.from("UPDATE `member`");
		
		secSql.append("SET updateDate = NOW()");
		secSql.append(", loginPw = ?", loginPw);
		secSql.append("WHERE id = ?", actorId);
		
		DBUtil.update(dbConn, secSql);
	}

	public Member getMember(String loginId, String loginPw) {
		
		SecSql secSql = SecSql.from("SELECT *");
		secSql.append("FROM `member`");
		secSql.append("WHERE loginId = ?", loginId);
		secSql.append("AND loginPw = ?", loginPw);
		
		return new Member(DBUtil.selectRow(dbConn, secSql));
	}
	
	// 로그인 대상 아이디로 대상정보 가져오기
	public Member getMemberByLoginId(String loginId) {
		SecSql sql = SecSql.from("SELECT *");
		sql.append("FROM `member`");
		sql.append("WHERE loginId = ?", loginId);

		Map<String, Object> row = DBUtil.selectRow(dbConn, sql);

		if (row.isEmpty()) {
			return null;
		}

		return new Member(row);
	}

	// 프로필 이미지 저장
	public void getModifyMemberImg(String memberLoginId, String memberImg) {
		
		SecSql secSql = SecSql.from("UPDATE `member`");
		
		secSql.append("SET myImg = ?", memberImg);
		secSql.append("WHERE loginId = ?", memberLoginId);
		
		DBUtil.update(dbConn, secSql);	
	}

}
