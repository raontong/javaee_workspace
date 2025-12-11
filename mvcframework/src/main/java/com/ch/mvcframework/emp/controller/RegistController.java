package com.ch.mvcframework.emp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.mvcframework.controller.Controller;
import com.ch.mvcframework.dto.Dept;
import com.ch.mvcframework.dto.Emp;
import com.ch.mvcframework.repository.DeptDAO;
import com.ch.mvcframework.repository.EmpDAO;

/*
사원 등록 요청을 처리하는 하위 컨트롤러
3단계 : 일시키기
4단계 : DML 이므로 4단계 생략
 */ 
public class RegistController implements Controller{
	DeptDAO deptDAO=new DeptDAO();
	EmpDAO empDAO=new EmpDAO();
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 부서관련 정보 ==> Dept2 에 등록
		String dptno=request.getParameter("deptno");
		String dname=request.getParameter("dname");
		String loc=request.getParameter("loc");
		
		// 사원관련 정보 ==> Emp2 에 등록
		String empno=request.getParameter("empno");
		String ename=request.getParameter("ename");
		String sal=request.getParameter("sal");
		
		Emp emp =new Emp(); // empt
		emp.setEmpno(Integer.parseInt(empno));
		emp.setEname(ename);
		emp.setSal(Integer.parseInt(sal));
		empDAO.insert(emp);
		
		Dept dept=new Dept(); //  empt
		dept.setDeptno(Integer.parseInt(dptno));
		dept.setDname(dname);
		dept.setLoc(loc);
		deptDAO.insert(dept);
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
