package com.ch.mvcframework.repository;

import org.apache.ibatis.session.SqlSession;

import com.ch.mvcframework.dto.Emp;
import com.ch.mvcframework.exception.EmpException;
import com.ch.mvcframework.mybatis.MybatisConfig;

public class EmpDAO {
	
	MybatisConfig mybatisConfig=MybatisConfig.getInstance();
	//1명등록
	// throws 가 명시된 메서드를 호출한 사람은 throws 에 명시된 예외를 처리할 것을 떠안게 됨.
	public void insert(SqlSession sqlSession, Emp emp) throws Exception {
		// 강요된 예외(try~ catch) - 개발자가 예외처리를 하지 않으면 빨간줄 가면서, 컴파일 불가능..강제함
		// 런타임 예외
		try {
			sqlSession.insert("Emp.insert", emp);
		} catch(Exception e) {
			e.printStackTrace(); // 에러의 정보를 개발자나, 시스템관리자가 알 수있도록 로그로 남기는
			// throw 는 예외를 일으키는 코드!! 이기때문에 개발자는 다음의 2가지 중 하나를 선택해야한다.
			// 1) try~ catch 로잡기
			// 2) 여기서 발생한 예외를 이 메서드 호출자에게 책임 전가 
			//     throws Exception 
			throw new EmpException("사원등록 실패", e);
		}
	}
}
