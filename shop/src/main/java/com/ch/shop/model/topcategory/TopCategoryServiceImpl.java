package com.ch.shop.model.topcategory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // ComponentSan 의 대상이 되고, 스프링이 인스턴스를 자동으로 올려
public class TopCategoryServiceImpl implements TopCategoryService {
	
	@Autowired
	private TopCategoryDAO topCategoryDAO;
		
	@Override
	public List getList() {
		
		return topCategoryDAO.selectAll();
	}

}
