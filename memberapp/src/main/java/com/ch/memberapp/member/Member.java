package com.ch.memberapp.member;

/* 현실의 한명의 회원을 표현한 객체 - 이러한 용도으 ㅣ객체를 가리켜 설계 분야에서는 DTO 라 한다.
 * DTO란? Data Transfer Object 의 약자임
 * 주용도? 로직을 작성하기 위함이 아니라, js처럼 단순히 정보를 저장해놓기 위한 용도로 쓰임
 * 즉, 데이터만을 보유시키기 위해 정의 하는 용도를 가리킴...
 * 
 * 자바에서 아래와 같이 클래스를 정의 하면서 멤버변수를 그대로 노출시키지 않는다.!!
 * EnCasulation(은닉화)-
 * 객체안에 데이터를 보호하고 그 데이터를 제어하는 방법에 대해서는 메서드를 통해 객체를 제어하는 클래스 정의 기법
 * public	< protected 				< default 		< private
 * 				(같은 패키지, 상속관계)		(같은 패키지)		(아무도 접근 못함)
*/
public class Member {
	private int member_id;
	private String id;
	private String pwd;
	private String name;
	private String regdate;
	
	
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	
	

}
