package com.ch.shop.dto;

import lombok.Data;
/*
{
    "id": 123456789,
    "connected_at": "2025-12-29T14:00:00Z",
    "kakao_account": {
        "profile_needs_agreement": false,
        "profile": {
            "nickname": "홍길동",
            "thumbnail_image_url": "http://k.kakaocdn.net/...jpg",
            "profile_image_url": "http://k.kakaocdn.net/...jpg",
            "is_default_image": false
        },
        "has_email": true,
        "email_needs_agreement": false,
        "is_email_valid": true,
        "is_email_verified": true,
        "email": "user@kakao.com",
        "has_gender": true,
        "gender_needs_agreement": false,
        "gender": "male"
    }
}
*/

//카카오가 보낸 복잡한 JSON을 그대로 받아냄
@Data
public class KakaoUserResponse { 

    private Long id; // json 가장바깥쪽 id
    private String connected_at;
    private KakaoAccount kakao_account;

    @Data
    public static class KakaoAccount {
        private String email; // private String email;
        private String gender;
        private String age_range;
        private String birthday;
        private Profile profile;
    }

    @Data
    public static class Profile {
        private String nickname;
        private String profile_image_url;
    }
}
