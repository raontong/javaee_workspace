package com.ch.model1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/* 
전국100산 관광정보 오픈 api 의 응답 결과중 가장 안쪽에 들어있눈 item 의 정보를 담기위한 DTO 
Lombok 
*/
@Data
//json 문자열과 자바 객체간에 매핑시, 자바객체가 보유하지 않은 속성은 그냥 무시해
@JsonIgnoreProperties(ignoreUnknown = true) 
public class Item {
	private String placeNm;
	private Double lat;	// 위도
	private Double lot;	// 경도
	private String frtrlNm;
}



