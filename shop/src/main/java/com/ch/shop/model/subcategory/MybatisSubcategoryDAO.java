package com.ch.shop.model.subcategory;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MybatisSubcategoryDAO implements SubCategoryDAO {

    @Autowired
    private SqlSessionTemplate sqlsessionTemplate;

    @Override
    public List selectByTopCategoryId(int topcategoryId) {
        return sqlsessionTemplate.selectList("SubCategory.selectByTopCategoryId", topcategoryId);

    }
}

