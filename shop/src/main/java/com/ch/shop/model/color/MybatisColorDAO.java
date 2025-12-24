package com.ch.shop.model.color;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository // @@Repository 를 표시해 놓으면, 스프링이 자동 스캔에 의해 탐색한 후 인스턴스를 자도응로 생성해주고, 빈 컨테이너로 관리
@Slf4j
public class MybatisColorDAO implements ColorDAO{
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	@Override
	public List selectAll() {
		
		return sqlSessionTemplate.selectList("Color.selectAll");
	}
	
}
