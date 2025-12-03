package com.ch.notice.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ch.notice.domain.NoticeDTO;


/* 이 클래스의 목적은?
 * javaEE  기반의 애플리케이션 , javaSe 
 * 따라서 유지보수성을 고려하여 여러 플랫폼에서 재사용할 수 있는 객체를 정의
 * 특히 로직 객체중 오직 데이터베이스 연동을 전담하는 역할을 하는 객체를 가리켜 애플리케이션 설계 분야에서는
 * DAO (Data Access Object) - DB 에 테이블이 만일 5개라면  DAO 로 1:1 대응하여 5개를 만들어야 한다.
 * 특히 데이터베이서의 테이블에 데이터를 처리하는 업무를 가리커 Create(insert) R(select) U(update) D(delete)
 * 따라서 매개 변수를 각각 낱개로 전달하는 것이 아니라, 객체안에 모두 넣어서, 객체 자체를 전달...
 * DTO (Data Transfer Object) - 오직 데이터만을 보유한 전달 객체를 의미 따라서, 로직은 없다!!(Dummy Object)
 * 
*/
public class NoticeDAO {
	
	// 게시물 등록!!
	public int regist(NoticeDTO notice) {
		int result=0; // insert 후 성공인지 실패인지를 판단할수 있는 
		Connection con=null; // 지역변수는 컴파일러가 자동으로 초기화 하지 않기 때문에 반드시 초기화해야 사용할 수 있다.
		PreparedStatement pstmt = null;
				
		//드라이버 로드
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 로드 성공");
			
			//접속
			try {
				con=DriverManager.getConnection("jdbc:mysql://localhost:3306/java","servlet","1234");
				System.out.println("드라이버 로드 성공");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//쿼리실행
			String sql="insert into notice(title, writer, content) values(?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, notice.getTitle());
			pstmt.setString(2, notice.getWriter());
			pstmt.setString(3, notice.getContent());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null) {
					pstmt.close();
				}
				if(con!=null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
