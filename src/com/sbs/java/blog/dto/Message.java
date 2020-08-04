package com.sbs.java.blog.dto;

import java.util.Map;

public class Message extends Dto {
	private int memberId;
	private String nickName;
	private String title;
	private String body;
	private String delDate;

	public Message(Map<String, Object> row) {
		super(row);
		this.memberId = (int) row.get("memberId");
		this.nickName = (String) row.get("nickname");
		this.title = (String) row.get("title");
		this.body = (String) row.get("body");
		this.delDate = (String) row.get("delDate");	
	}
	
	@Override
	public String toString() {
		return "Message [memberId=" + memberId + ", nickName=" + nickName + ", title=" + title + ", body=" + body
				+ ", delDate=" + delDate + ", getId()=" + getId() + ", getRegDate()=" + getRegDate()
				+ ", getUpdateDate()=" + getUpdateDate() + ", getExtra()=" + getExtra() + "]";
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDelDate() {
		return delDate;
	}

	public void setDelDate(String delDate) {
		this.delDate = delDate;
	}
	
}
