package com.ch.model1.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ch.model1.dto.Member2;
import com.ch.model1.util.PoolManager;

/* 이클래스는 오직 데이터베이스 관련된 로직만 담당하는 DAO 클래스임*/
public class Member2DAO {
	PoolManager pool=PoolManager.getInstance();
	
	// insert - 언제나 레코드 1건만 넣는다
	public int insert(Member2 member2) { // 자바분야에서는 파라미터는 DTO로 처리한다.
		Connection con =pool.getConnection(); // 커넥션 풀로부터 하나 대여
		PreparedStatement pstmt =null;
		int result=0; // DML 수행후 그결과를 받아놓을 변수
		
		String sql="insert into member2(id, name, email) values(?,?,?)";
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, member2.getId());
			pstmt.setString(2, member2.getName());
			pstmt.setString(3, member2.getEmail());
			
			result=pstmt.executeUpdate(); // 쿼리실행
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return result;
		
	}

	// 모든 레코드 가져ㅏ오기
	public List selectAll() {
		Connection con =pool.getConnection(); // 커넥션풀로 부터 대여
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		List<Member2> list=new ArrayList();
		
		String sql="select * from member2 order by member2_id asc";
		try {
			pstmt=con.prepareStatement(sql);
			rs= pstmt.executeQuery(); // select문 수행 후 결과받기
			
			// rs에 들어있는 레코드 수 만큼, DTO 를 생성하여 레코드를 안에 넣자!!
			// 그리고 채워진 DTO를 다시 java.util.List에 밀어넣자!!
			
			while(rs.next()) {
				Member2 member2= new Member2();
				member2.setId(rs.getString("id"));
				member2.setName(rs.getString("name"));
				member2.setEmail(rs.getString("email"));
				list.add(member2); // 리스트에 추가;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
			
		}
		return list;
	}
	
}
