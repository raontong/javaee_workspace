package com.ch.mvcframework.emp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.ch.mvcframework.controller.Controller;
import com.ch.mvcframework.dto.Dept;
import com.ch.mvcframework.dto.Emp;
import com.ch.mvcframework.mybatis.MybatisConfig;
import com.ch.mvcframework.repository.DeptDAO;
import com.ch.mvcframework.repository.EmpDAO;

/*
사원 등록 요청을 처리하는 하위 컨트롤러
3단계 : 일시키기
4단계 : DML 이므로 4단계 생략
 */ 
public class RegistController implements Controller{
	/*
	DeptDAO와 EmpDAO 가 같은 트랜젹션으로 묶일려면, 각각의 DAO는 공통의 SqlSession 을 사용해야한다.
	따라서 MybatisConfig 으로 부터 SqlSession 을 하나 취득한 후 insert 문 호출시
	같은 주소값을 갖는 공유된 SqlSession 을 나눠주자
	*/
	MybatisConfig mybatisconfig=MybatisConfig.getInstance();
	DeptDAO deptDAO=new DeptDAO();
	EmpDAO empDAO=new EmpDAO();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 부서관련 정보 ==> Dept2 에 등록
		String deptno=request.getParameter("deptno");
		String dname=request.getParameter("dname");
		String loc=request.getParameter("loc");
		
		// 사원관련 정보 ==> Emp2 에 등록
		String empno=request.getParameter("empno");
		String ename=request.getParameter("ename");
		String sal=request.getParameter("sal");
		
		Dept dept=new Dept(); //  empt
		dept.setDeptno(Integer.parseInt(deptno));
		dept.setDname(dname);
		dept.setLoc(loc);
		
		Emp emp =new Emp(); // empt
		emp.setEmpno(Integer.parseInt(empno));
		emp.setEname(ename);
		emp.setSal(Integer.parseInt(sal));
		
		SqlSession sqlSession = mybatisconfig.getSqlSession();
		// mybatis 는 디폴트가 autocommit =false 로 되어 있으므로, 개발자가 별도로 
		// 트랜젝선 시작을 알릴필요없음
		try {
			deptDAO.insert(sqlSession, dept);
			empDAO.insert(sqlSession, emp);
			sqlSession.commit(); // 트랜젝션 확정
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback();// 둘중에 누가 잘못되었던 간에, 단 하나라도 문제가 발생하면 전체가 무효가 됨 
			
		} finally {
			mybatisconfig.release(sqlSession);
		}
		
	}

	@Override
	public String getViewName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isForward() {
		// TODO Auto-generated method stub
		return false;
	}

}
