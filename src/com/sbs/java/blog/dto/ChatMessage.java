package com.sbs.java.blog.dto;

import java.util.Map;

public class ChatMessage extends Dto {
	private String body;
	private int memberId;
	
	public ChatMessage(Map<String, Object> row) {
		super(row);
		this.body = (String) row.get("body");
		this.memberId = (int) row.get("memberId");
	}
	
	@Override
	public String toString() {
		return "ChatMessage [body=" + body + ", memberId=" + memberId + ", getId()=" + getId() + ", getRegDate()="
				+ getRegDate() + ", getExtra()=" + getExtra() + "]";
	}
	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	
}
