package com.ch.shop.dto;

import java.util.List;

import lombok.Data;

/*우리 쇼핑몰의 최상위 카테고리 테이블에 대한 DTO*/
@Data
public class TopCategory {
	private int topcategory_id;
	private String topname;
	
	// 자식들도 챙기자
	private List<SubCategory> subList; 
}
