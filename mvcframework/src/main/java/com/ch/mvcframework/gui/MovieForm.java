package com.ch.mvcframework.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

/* 
영화와 관련된 판단을 해주는 공통 로직을 javaSE의 GUI 프로그래밍에서도 사용할 수 잇는지 테스트해보자
테스트 성공한다면, 공통 로직의 작성을 성공한 것이고, 재사용 가능성을 극대화 한 것임
*/
public class MovieForm extends JFrame{ // MovieForm 윈도우야 extends
	
	JButton bt; // 윈도우가 버튼을 가지고잇다	
	JComboBox box; // 
	String[] movies= {"귀멸의칼날","미션임파서블","에이리언","주토피아"};
	
	public MovieForm() {
		bt=new JButton("피드백요청");
		box= new JComboBox();
		
		for(String movie : movies) { // jdk1.5부터 지원되는 improved for (컬렉션 프레임웍과 친함)
			box.addItem(movies);
			
		}
		
		
		
		// 부착전에 HTML 처럼 레이아웃을 먼저 설정
		setLayout(new FlowLayout());  	//  FlowLayout 이란 수평, 수직의 직선으로 컴포넌트를 배치함, 
														// 윈도우 창에 따라 내용물들이 흘러다님
		
		this.add(box); 	// 콤보박스를 우니도우에 부착
		this.add(bt); 		// 버튼을 윈도우에 부착
		
		//  버튼에 리스너 연결
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("나 눌렀어?");
			}
		});
		
		
		setSize(300,200);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] arg) {
		// MovieForm 이 곧 윈도우 이므로, MovieForm을
		// new 하면 윈도우창이 메모리에 생성됨..(why? is a 관계이므로)
		MovieForm movieForm = new MovieForm();
		
		
		
	}
	
	
}
