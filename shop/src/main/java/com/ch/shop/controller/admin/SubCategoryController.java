package com.ch.shop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

// 쇼핑몰의 하위 카테고리에 대한 요청을 처리하는 하위 컨트롤러
@Controller
@Slf4j
public class SubCategoryController {
	// 요청목록 처리
	// 주의 ) 클라이ㅏ언트가 비동기 요청을 시도할 경우, 서버는 절대로  HTML 문서를 원하는 것이 아니므로 ,
	// 데이터를 보내줘야 한다. 특히 개발 프로그래밍에서 표준적으로 많이 사용하는 형식이 바로 JSON 문자열이므로, 적극 사용!! 
	@GetMapping("/admin/subcategory/list")
	public void getList() {
		log.debug("하위 카테고리 목록 요청을 받음");
		
	}
}
