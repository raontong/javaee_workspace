package com.ch.shop.dto;

import lombok.Data;

// KakaoUserResponse 객체에서 필요한 것만 뽑아서 예쁘게 정리함
@Data
public class KakaoUser {

    private String provider;        // "kakao"라고 고정해서 저장 (나중에 구글/네이버와 구분용)
    private String socialId;        // 카카오의 Long 타입 ID를 String으로 변환해서 저장
    private String email;
    private String nickname;
    private String profileImage;
    
    private String gender;
    private String ageRange;
    private String birthday;
}
