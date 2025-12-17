package com.ch.shop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ch.shop.model.topcategory.TopCategoryService;

/*쇼핑몰의 관리자에서 상품과 관련된 요청을 처리하는 하위 컨트롤러*/
@Controller
public class ProductController {
	
	// 서비스 보유(느슨하게 보유)
	@Autowired
	private TopCategoryService topCategoryService;
	
	// 상품 등록폼 요청 처리
	@GetMapping("/admin/product/registform")
	public String getRegistForm(Model model) {
		// 3단계: 상품페이지에 출력할 상위카테고리 가져오기
		List topList = topCategoryService.getList();
		
		
		// 4단계: 결과저장(request 직접 해야 하지만, 스프링에서는 Model객체를 이용하면 간접적으로 저장이됨)
		// jsp까지 topList를 살려서 가야하므로, 포워딩 처리해야 함.. 스프링 개발자가 redirect를 명시하지 않으면 디폴트가 포워딩
		model.addAttribute("topList", topList);	// request.setAttribute("topList", topList); 와 동일
		
		return "admin/product/regist";
	}
}