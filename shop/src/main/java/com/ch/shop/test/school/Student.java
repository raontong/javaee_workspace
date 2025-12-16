package com.ch.shop.test.school;


/*2) AOP로 학교에서 공부하는 학생을 표현하기 */
public class Student {
	
	// 생성자 주입
	public Student() {
	}
	public void gotoSchool() {
		// 하나의 관점(감시하고 있다가 bell > ding )으로 의인화, 삭제  
		// bell.ding();
		System.out.println("등교해요");
	}
	public void study() {
		System.out.println("공부해요");
	}
	public void rest() {
		System.out.println("쉬는 시간을 가져요");
	}
	public void haveLunch() {
		System.out.println("점심을 먹어요");
	}
	public void goHome() {
		System.out.println("귀가해요");
	}

}
