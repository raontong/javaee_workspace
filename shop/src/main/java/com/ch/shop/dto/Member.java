package com.ch.shop.dto;

import lombok.Data;

@Data
public class Member {
	private int member_id;
	private String home_id; //sns 회원인 경우 null
	private String home_pass; //sns 회원인 경우 null
	private String provider_userid; //homepage 회원인 경우 null
	private String name; //프로바이더 측에서는 실명을 주지 않는다 , 즉 닉네임 수준 
	private String email; //프로바이더에 따라 이메일을 기본으로 제공하지 않을수도 있다(카카오)
	private String regdate;
	private String updated;//회원정보 테이블의 데이터를 수정 시 자동으로 날짜 업데이터 처리됨 	
	private Provider provider;
	
}
