package com.ch.mybatisapp.repository;

import org.apache.ibatis.session.SqlSession;

import com.ch.mybatisapp.dto.Board;
import com.ch.mybatisapp.mybatis.MybatisConfig;

public class BoardDAO {
	MybatisConfig mybatisConfig = MybatisConfig.getInstance(); //SqlSessionFactory가 들어있는 싱글턴 객체
	
	// 글쓰기
	public int insert(Board board) {
		int result=0;
		// 상투적 JDBC code 사용하지 말자!!
		SqlSession sqlSession= mybatisConfig.getSqlSession();
		result = sqlSession.insert("com.ch.mybatisapp.dto.Board.insert",board);
		// DML은 트렌젝션을 확정지어야한다.
		
		sqlSession.commit();
		mybatisConfig.release(sqlSession);
		return result;
	}
}
