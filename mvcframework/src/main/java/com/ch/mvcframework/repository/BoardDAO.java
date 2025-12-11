package com.ch.mvcframework.repository;

import java.util.List;

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
		// config.xml 에서 alias 함..<typeAlias type="com.ch.mvcframework.dto.Board" alias="Board"/>
		result=sqlSession.insert("Board.insert", board);
		// sqpSession 은 디폴트로 autocommit 속성이 false 로 되어있음
		// 즉 commit 하지 않으면 insert 가 db 에 확정되지 않음
		sqlSession.commit(); // ★ DML만을 대상으로 함 ★ 
		mybatisConfig.release(sqlSession); // 반납
		return result;
	}
	
	// 모든글 가져오기
	public List selectAll() {
		List list=null;
		SqlSession sqlSession =mybatisConfig.getSqlSession();  
		list=sqlSession.selectList("Board.selectAll");
		// ★ DML만을 대상으로 함 ★  commit 하지 않는다
		mybatisConfig.release(sqlSession);
		return list;
	}
	
	// 레코드 1건 가져오기
	public Board select(int board_id) {
		Board board=null;
		SqlSession sqlSession=mybatisConfig.getSqlSession();
		board=sqlSession.selectOne("Board.select", board_id); 
		mybatisConfig.release(sqlSession);
		return board;
	}
	
	// 1건 삭제
	public int delete(int board_id) {
		int result =0;
		// SqlSession = Connection + PreparedStatememt
		SqlSession sqlSession=mybatisConfig.getSqlSession();
		sqlSession.delete("Board.delete", board_id);
		sqlSession.commit(); // 트랜잭션 확정
		mybatisConfig.release(sqlSession);
		return result;
	}
	
	// 1건 수정
	public int update(Board board) {
		int result = 0;
		SqlSession sqlSession = mybatisConfig.getSqlSession();
		sqlSession.update("Board.update", board);
		//DML 은  commit대상임
		sqlSession.commit();
		mybatisConfig.release(sqlSession);
		return result;
	}
	
}









