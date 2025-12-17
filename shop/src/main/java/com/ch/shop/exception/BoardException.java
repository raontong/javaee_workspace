package com.ch.shop.exception;


/*아래의 클래스는 자바의 RuntimeException 을 상속받아, 개발자만의 예외 객체로 커스텀하기 위함*/
public class BoardException extends RuntimeException {
	
	// 자바에서 부모의 생성자는 물려 받지 못한다. 
	// 이유? 생성자는 해당 객체만의 초기화 작업에 사용되므로, 만일 부모의 생성자 마저도 물려받게 되면, 내가 부모가 되어버리는 개념
	public BoardException(String msg) {
		super(msg); // 에러 메시지를 담을 수있는 부모의 생성자 호출
	}
	
	public BoardException(String msg, Throwable e) {
		super(msg, e); // 에러 메시지와 원인을 담을 수 있는 부모의 생성자 호출
	}
	
	public BoardException(Throwable e) {
		super(e); // 어러 원인을 담을 수 있는 부모의 생성자 호출
	}
}

