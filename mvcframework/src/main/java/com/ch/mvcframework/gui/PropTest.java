package com.ch.mvcframework.gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropTest {
	public static void main(String[] args) {
		// Map 을 상속받은 객체
		// 이 객체는 단독적으로 파일을 접근할 능력은 없다.. 따라서 java.io 의 스트림객체들을 이용해야함
		/*
		자바의 스트림
		1) 방향에 따른 기준 
			입력 - 실행중인 프로그램으로 데이터가 들어오는 모습입력
			출력 - 실행중인 프로그램에서 데이터가 흘러나가는 모습 출력
			
		2) 데이터 처리 방법
			바이트 스트림
			문자 스트림
			버퍼 스트림
		 * */
		Properties props=new Properties(); // 일반, 추상, 인터페이스
		try {
			FileInputStream fis=new FileInputStream("C:/javaee_workspace/mvcframework/src/main/webapp/WEB-INF/servlet-mapping.txt");
				props.load(fis); // 이 시점 파일을 로드한 상태
				String value=props.getProperty("/movie.do");
				System.out.println(value);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
