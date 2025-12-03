package com.ch.model1.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


// DAO 의 각 메서드 마다 커넥션 풀로 부터 Connection 을 얻어오는 코드를 중복 작성할 경우 유지보수성이 떨어짐..
// ex) JNDI 명칭이 바뀌거나, 연동할 db의 종류가 바뀌는 등, 외부의 어떤 변화원인에 의해 코드가 영향을 많이 받으면 안됨..
// 따라서, 앞으로는 커넥션 풀로부터  Connection 을 얻거나 반납하는 중복된 코드는 아래의 클래스로 처리하면 유지보수 성이 올라감
public class PoolManager {
	private static PoolManager instance; // instance 라느 ㄴ변수명은 강제 사항은 아니지만, 개발자들 사이에서는 싱글턴에 의해
														// 인스턴스를 얻어갈 수 있다는 약속 떄문에 많이 쓰는 선언.
	DataSource ds;

	private PoolManager() {
		try {
			InitialContext context = new InitialContext();
			ds = (DataSource)context.lookup("java:comp/env/jndi/mysql");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	public static PoolManager getInstance() {
		// 클래서 변수인 instance 변수에 아무것도 존재 하지 않을 때는 아ㅓ직 인스턴수가 없는 것이므로 
		// 그떄 한번만 직접 new 해준다
		// poolManager를 싱글턴으로 선언하면, 자바엔터프라이즈 개발에서 수많은 DAO 들이 PoolManager 를 매번 인스턴스 생성하는
		// 낭비를 방지할 수있다.
		if(instance==null) {
			instance=new PoolManager();
			
		}
		return instance;
	}
	// 외부의 DAO들이 직접 Connection 을 얻는 코드를 작성하게 하지 않으려면, 이 PoolManager 클래스에서 
	// DAO 대신 Connection 얻어와서 반환해주자
	public Connection getConnection() {
		Connection con=null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return con;
	}
	
	// 빌려간 커넥션을 반납!!
	public void freeConnection(Connection con) {
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} // 커넥션 풀에 사는 con 
		}
	}
	
	// 아래의 오버로딩된 메서드는 DML 수행후 반납할떄 사용하자
	public void freeConnection(Connection con, PreparedStatement pstmt) {
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} // 커넥션 풀에 사는 con 
		}
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} // 커넥션 풀에 사는 con 
		}
	}
	
	public void freeConnection(Connection con, PreparedStatement pstmt, ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} // 커넥션 풀에 사는 con 
		}
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} // 커넥션 풀에 사는 con 
		}
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} // 커넥션 풀에 사는 con 
		}
	}
}
