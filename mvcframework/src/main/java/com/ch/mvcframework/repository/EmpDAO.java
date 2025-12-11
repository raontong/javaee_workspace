package com.ch.mvcframework.repository;

import org.apache.ibatis.session.SqlSession;

import com.ch.mvcframework.dto.Emp;
import com.ch.mvcframework.mybatis.MybatisConfig;

public class EmpDAO {
	MybatisConfig mybatisConfig=MybatisConfig.getInstance();
	
	//1명등록
	public void insert(Emp emp) {
		SqlSession sqlSession = mybatisConfig.getSqlSession();
		int result=sqlSession.insert("Emp.insert", emp);
		
		System.out.println(result);
		
		sqlSession.commit(); // 트랜젝션 확정
		mybatisConfig.release(sqlSession);
		
	}
}
