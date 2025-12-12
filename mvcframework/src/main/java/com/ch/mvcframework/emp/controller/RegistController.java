package com.ch.mvcframework.emp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.mvcframework.controller.Controller;
import com.ch.mvcframework.dto.Dept;
import com.ch.mvcframework.dto.Emp;
import com.ch.mvcframework.emp.model.EmpService;
import com.ch.mvcframework.repository.EmpDAO;

/*
사원 등록 요청을 처리하는 하위 컨트롤러
3단계 : 일시키기
4단계 : DML 이므로 4단계 생략

컨트롤러는 일만 시키자!!
 */ 
public class RegistController implements Controller{
	private EmpService empService=new EmpService();
	private String viewName;
	
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
		// Emp 가 Dept를 jas a 관계로 보유하고 있으므로
		// 낱개로 전달하지 말고, 모아서 전달하자
		emp.setDept(dept);
		
		// 모델영역에 일시키기(주의 구체ㅐ적으로 직접 일하지 말자=일직접하는 순간 모델이 됨..)
		// 코드가 혼재되므로, 모델 영역을 분리시킬 수 없으므로 재사용성이 떨어짐
		
		// 아래의 regist() 메서드에는 호출자에게 예외를 떠넘기는(전가) throws 가 처리되어 있음에도 불구하고
		// 컴파일 에러가 나지 않은 이유는? 여기서 예외가 개발자에게 처리를 강요하지 ㅇ낳는 RuntimeException 이기 때문이다..
		// 하지만 개발자는 강요하지 않는다고 하여 예외 처리를 하지 않으면, 프로그램을 올바르게 실행 될수 없을 것이다..
		try {
			empService.regist(emp); // 성공 실패 에러 페이지를 만들어서 일반에게 보여준다 
			viewName="/emp/regist/result"; // 사원등록 처리시의 뷰 매핑
		} catch(Exception e) {
			viewName="/emp/error";
			e.printStackTrace();
		}
	}

	@Override
	public String getViewName() {
		// TODO Auto-generated method stub
		return viewName; // 성공? 실패? 결정되어잇지 않아서 변수 viewName!!
	}

	@Override
	public boolean isForward() {
		// TODO Auto-generated method stub
		return false;
	}

}
