package com.ch.shop.model.subcategory;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MybatisSubcategoryDAO implements SubCategoryDAO {

    @Autowired
    private SqlSessionTemplate sessionTemplate;

    @Override
    public List selectByTopCategoryId(int topcategoryId) {
        return sessionTemplate.selectList("SubCategory.selectByTopCategoryId", topcategoryId);

    }
}

