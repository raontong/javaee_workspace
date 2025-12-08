package com.ch.model1.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ch.model1.dto.News;
import com.ch.model1.util.PoolManager;

// (데이터베이스와 직접통신)News 테이블에 대한 CRUD 만을 수행하는 DAO 
// 데이터를 어떻게 저장/조회할까
public class NewsDAO {
	PoolManager poolManager=PoolManager.getInstance();
	
	// 게시물 한건 넣기
	public int insert(News news){
		Connection con= null;
		PreparedStatement pstmt=null;
		int result=0;
		
		con=poolManager.getConnection(); // 접속객체 대여
		String sql="insert into news(title, writer, content) values(?,?,?)";
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, news.getTitle());
			pstmt.setString(2, news.getWriter());
			pstmt.setString(3, news.getContent());
			result=pstmt.executeUpdate(); // 쿼리실행

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			poolManager.freeConnection(con, pstmt);
		}
		return result;
	}
	
	// 모든 목록 레코드 가져오기
	public List selectAll() {
		Connection con= null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		List list=new ArrayList();;
		
		con=poolManager.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT n.news_id as news_id, title, writer, regdate, hit, COUNT(c.comment_id) AS cnt");
		sb.append(" FROM news n LEFT OUTER JOIN comment c");
		sb.append(" ON n.news_id = c.news_id");
		sb.append(" GROUP BY n.news_id, title, writer, regdate, hit");
		sb.append(" ORDER BY n.news_id desc");
		System.out.println(sb.toString());
		
		try {
			pstmt=con.prepareStatement(sb.toString());
			rs=pstmt.executeQuery();
			
			// 
			while(rs.next()) {
				News news = new News(); // 레코드 있는 만큼 new???  이해못함..
				news.setNews_id(rs.getInt("news_id"));
				news.setTitle(rs.getString("title"));
				news.setWriter(rs.getString("writer"));
				//news.setContent(rs.getString("content"));
				news.setRegdate(rs.getString("regdate"));
				news.setHit(rs.getInt("hit"));
				news.setCnt(rs.getInt("cnt"));
				
				list.add(news);
				
						
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			poolManager.freeConnection(con,pstmt,rs);
		}
		return list;
	}
	
	// 한건 레코드 가져오기
	public News select(int news_id) { 
		Connection con= null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		News dto=null; // 한 건이므로 뉴스 1.개를 반환
		
		con=poolManager.getConnection();
				
		String sql ="select * from news where news_id=?"; // 내림차순 
		
		try {
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,news_id);
			
			rs=pstmt.executeQuery();
			
			
			// 한건이라 if , 한건 
			if(rs.next()) {
				dto = new News(); // 레코드 있는 만큼 new???  이해못함..
				dto.setNews_id(rs.getInt("news_id"));
				dto.setTitle(rs.getString("title"));
				dto.setWriter(rs.getString("writer"));
				dto.setContent(rs.getString("content"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setHit(rs.getInt("hit"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			poolManager.freeConnection(con,pstmt,rs);
		}
		return dto;
	}
}
