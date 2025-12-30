package com.ch.shop.controller.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.ch.shop.model.topcategory.TopCategoryService;

/*
	모든 컨트롤러 보다 앞서 실행되는 컨트롤러 정의
 	용도 : 쇼핑몰의 모든 컨트롤러에ㅐ서 <현재 TopCategory의 목록을 매번 가져오는 코드가 중복되고 있으므로, 이를 해결하기 위함
 	해결 : @ControllerAdvice 명시
 			안에 작성된 메서드는 다른 컨트롤러의 실행에 앞서서 실행되어 지는데, 주의할점은 이안에 정의한 모든 메서드가
 			동작하는 먼저 것이 아니라, 아래의 3가지 어노테이션에 대해서만 효과가 발생
 			1) @InitBinder : 컨트롤러로 전달되는 파라미터에 대해 개발자가 커스텀하고 싶을때 사용
★오늘할것★2) @ModelAttribute : Model 객체에 model.setAttribyte("topList", topList); 의 효과를 낼 수 있는 어노테이션
 			3) @ExceptionHandler
*/
@ ControllerAdvice // 다른 컨트롤러 보다 앞서서 동작하는 컨트롤러
public class GlobalController {
	
	@Autowired
	private TopCategoryService topCategoryService;
	
	
	// 아래의 메서드는 위 3가지 정해 놓은 어토테이션을 명시 하지 않았으므로, 먼저 동작하는 효과 X
	public void test() {
	}
	
	// 쇼핑몰의 상위 카테고리를 저장해놓기
	@ModelAttribute("topList") // model.addAttribute("topList", topList); 대신에
	public List getTopCategoryList() {
		 // List topList = topCategoryService.getList();
		 // model.addAttribute("topList", topList);
		 
		return topCategoryService.getList();
	}
	

}
