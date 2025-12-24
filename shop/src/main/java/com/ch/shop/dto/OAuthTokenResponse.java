package com.ch.shop.dto;

import lombok.Data;

/*
이 객체의 정의 목적
프로바이더들에게, clientID, clientSecet, code 를 동봉해서 보내면, 정보가 잘못되지않는 한, Tokjen 을 보내주는데,
그냥 토큰만 달량 보내는게 아니라ㅏ, 각종 다른 정보와 함께 응답정보로 보내준다. 따라서 토큰을 포함하여 그 정보들도 함께 담아놓을
객체가 필요하다.(토큰을 포함할수 있는 바구니 역할)
*/
@Data
public class OAuthTokenResponse {
	private String access_token;
	private String token_type;
	private String refresh_token;
	private String expries_in;
	private String scope;
	private String id_token;
	
}
