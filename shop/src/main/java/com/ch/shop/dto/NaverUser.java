package com.ch.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/*
{
	"id:~~
}
 
*/
@Data
public class NaverUser {
	
	private String id; 
	private String email;
	private String name;
	private String nickname;
	private String profile_image;
	private String gender; 
	private String age; 
	private String birthday;
}
