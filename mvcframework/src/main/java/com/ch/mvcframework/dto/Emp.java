package com.ch.mvcframework.dto;

import lombok.Data;

@Data
public class Emp {
	private int empno;
	private String ename;
	private int sal;
	
	// 한명의 사원의 특정 부서에 소속이 되어야한다
	private Dept dept;
	
}
