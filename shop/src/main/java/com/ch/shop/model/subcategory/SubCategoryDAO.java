package com.ch.shop.model.subcategory;

import java.util.List;

// 서비스가 느슨하게 보유할 SubCategory DAO 의 최상위 
public interface SubCategoryDAO {
	public  List selectByTopCategoryId(int topcategory_id);
}
