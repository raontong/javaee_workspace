package com.ch.shop.dto;

import lombok.Data;

/*구글 뿐만 아니라, 네이버, 카카오 등 IDP 연동시 필요한 정보를 담는 객체 */
@Data
public class OAuthClient {
	private String provider; 			// google, naver, kakao  구분값
	private String clientId; 			// 개발자 콘솔에서 앱 등록시 발급 받은 클라이언트 ID
	private String clientSecret; 		// ★★비공개★★ 개발자 콘솔에서 앱등록시 발급 받은 클라이언트 비밀번호
	private String authorizeUrl; 	// 클라이언트가 sns 로그인 버튼 누를때 요청 대상 URL
	private String tokenUrl;		 	// 리소스 오너의 정보를 조회할떄 사용할 요청 주소
	private String Scope;				
	private String redirectUrlString; // ★★★ Provider 로 부터 콜백받을 주소
}
