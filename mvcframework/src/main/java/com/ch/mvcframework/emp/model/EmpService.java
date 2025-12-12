package com.ch.mvcframework.emp.model;

import org.apache.ibatis.session.SqlSession;

import com.ch.mvcframework.dto.Emp;
import com.ch.mvcframework.exception.EmpException;
import com.ch.mvcframework.mybatis.MybatisConfig;
import com.ch.mvcframework.repository.DeptDAO;
import com.ch.mvcframework.repository.EmpDAO;

/*
애플리케이션의 영역 중 서비스를 정의한다
Service 의 정의 목적
1) 서비스가 없을 경우 DAOㄷ르에게 일을 시키는 업무가 Controller 에게 부담이 됨
    따라서 이 시점부터는 컨트롤러의 업무와 모델의 업무가 뒤섞여 버림
2) 컨트롤러를 DAO 들과 분리시키고,  ★★★ 트랜젝션 ★★★을 대신 처리할 객체가 필요함, 
											서비스가 DAO를 거두는
*/
public class EmpService { //서비스의 부장이 서비스의 사원에게 일시키는... 
	EmpDAO empDAO=new EmpDAO();
	
	/*
	DeptDAO와 EmpDAO 가 같은 트랜젹션으로 묶일려면, 각각의 DAO는 공통의 SqlSession 을 사용해야한다.
	따라서 MybatisConfig 으로 부터 SqlSession 을 하나 취득한 후 insert 문 호출시
	같은 주소값을 갖는 공유된 SqlSession 을 나눠주자
	*/
	MybatisConfig mybatisconfig=MybatisConfig.getInstance();
	DeptDAO deptDAO=new DeptDAO();
	
	// 한명의 사원이 입사하면, 부서와 사원을 동시에 등록하는 메서드!
	public void regist(Emp emp) throws EmpException {
		SqlSession sqlSession = mybatisconfig.getSqlSession();
		// mybatis 는 디폴트가 autocommit =false 로 되어 있으므로, 개발자가 별도로 
		// 트랜젝선 시작을 알릴필요없음
		try {
			deptDAO.insert(sqlSession, emp.getDept());
			empDAO.insert(sqlSession, emp);
			sqlSession.commit(); // 트랜젝션 확정
		} catch (Exception e) {
			e.printStackTrace(); // 개발자를 위해서.. 그래서 일반인을 알아야하는데.. Exception e 을 던져야함 thorw
			sqlSession.rollback();// 둘중에 누가 잘못되었던 간에, 단 하나라도 문제가 발생하면 전체가 무효가 됨 
			
			// 아래의 throw 코드에 의해 에러가 발생한다. 따라서 개발자는 두가지중 하나를 선택해야 한다.
			// 1) 예외를 여기서 잡을지(try~ catch)
			// 2) 예외를 여기서 잡지 않고, 외부의 메서드 호출자에ㅐ게 전달할지 위에~~(throws) 던진다
			throw new EmpException("사원등록 실패", e); // 전달할려고 개발자가 에러 일으킴.. 마지막에 쓰는이유 
																		  // 중간에 넣으면 에러나서 멈춤
			
		} finally {
			mybatisconfig.release(sqlSession);
		}

	}
}
