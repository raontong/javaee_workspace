package com.ch.mvcframework.repository;

import org.apache.ibatis.session.SqlSession;

import com.ch.mvcframework.dto.Board;
import com.ch.mvcframework.mybatis.MybatisConfig;

public class BoardDAO {
	MybatisConfig mybatisConfig=MybatisConfig.getInstance();
	// 글 한건 등록
	public int insert(Board board) {
		int result=0;
		
		SqlSession sqlSession=mybatisConfig.getSqlSession();
		sqlSession.insert("Board.insert", board);
		return result;
	}
}
