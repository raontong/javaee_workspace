package com.ch.model1.singleton;

import java.awt.Dialog;

public class UseDog {
	public static void main(String[] args) {
		// 강아지 클래스가 현재 생성자를 막아놓았으므로, 
		// new를 통한 인스턴스 생성을 접근할 방법은 없다.
		// 따라서 강아지가 제공하는 static 메서드, 즉 클래스 메서드를
		// 통해 강아지 인스턴스를 얻어오자
		
		Dog d1= Dog.getInstance();
		// 1개만 생성되야하는데 주소값이 똑같아야한다
		System.out.println("d1="+d1);
		d1.bark();
		
		Dog d2= Dog.getInstance();
		// 1개만 생성되야하는데 주소값이 똑같아야한다
		System.out.println("d2="+d2);
		
		
	}
}
