package com.sbs.java.blog.dto;

import java.util.Map;

import lombok.Data;

@Data
public class Member extends Dto {
	private String loginId;
	private String loginPw;
	private String name;
	private String nickName;
	private String email;
	private int level; 
	private String myImg;	// 개인 프로필 이미지
	private String myIntro;	// 개인 소개

	public Member(Map<String, Object> row) {
		super(row);

		this.loginId = (String) row.get("loginId");
		this.loginPw = (String) row.get("loginPw");
		this.name = (String) row.get("name");
		this.nickName = (String) row.get("nickname");
		this.email = (String) row.get("email");
		this.level = (int) row.get("level");
		this.myImg = String.valueOf(row.get("myImg"));
		this.myIntro = (String) row.get("myIntro");
	}
}
