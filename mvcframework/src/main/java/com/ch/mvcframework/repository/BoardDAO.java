package com.ch.mvcframework.repository;

import org.apache.ibatis.session.SqlSession;

import com.ch.mvcframework.dto.Board;
import com.ch.mvcframework.mybatis.MybatisConfig;

// Model
public class BoardDAO {
	MybatisConfig mybatisConfig=MybatisConfig.getInstance();
	// 글 한건 등록
	public int insert(Board board) {
		int result=0;
		SqlSession sqlSession=mybatisConfig.getSqlSession();
		result=sqlSession.insert("Board.insert", board);
		// sqpSession 은 디폴트로 autocommit 속성이 false 로 되어있음
		// 즉 commit 하지 않으면 insert 가 db 에 확정되지 않음
		sqlSession.commit();
		return result;
	}
}
