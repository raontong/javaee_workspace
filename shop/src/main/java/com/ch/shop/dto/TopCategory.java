package com.ch.shop.dto;

import lombok.Data;

/*우리 쇼핑몰의 최상위 카테고리 테이블에 대한 DTO*/ 
@Data
public class TopCategory {
	private int topcategort_id;
	private String name;
	
}
