package com.ch.notice.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ch.notice.domain.NoticeDTO;
import com.ch.notice.repository.NoticeDAO;


// 윈도우로 만드는 게시판
public class RegistForm extends JFrame{
	// 윈도우가 텍스트 박스를 가지고있다.
	JTextField title; // 제목입력 텍스트 박스
	JTextField writer; // 작성자 텍스트 박스
	JTextArea content; // 내용입력 박스
	JButton bt; // 등록버튼
	NoticeDAO dao; // 오직 table 에 대해 CRUD 만을 처리하는 객체를 보유한다!!
	
	// 클래스가 보유한 멤버변수가 객체형일 경우 has a 관계
	
	// 생성자의 목적은, 이ㅏ 객체의 인스턴스가 생성될대 초기화할 작업이 있을 경우
	// 초기화 작업을 지원하기 위함이다.
	public RegistForm() {
		title =new JTextField(30); // 텍스트박스의 디자인 길이
		writer =new JTextField(30);
		content =new JTextArea(15,30);
		bt = new JButton("등록");
		dao = new NoticeDAO();
		
		//  컴포넌트를 부탁하기 전에, 레이아웃을 결정짓자 css div 로 레이아웃 적용하는것과 비슷
		setLayout(new FlowLayout());
		
		this.add(title); // 윈도우인 나의 몸체에 title을 부착
		this.add(writer); 
		this.add(content);
		this.add(bt); // 윈도우인 나의 몸체에 버튼을 부착
				
		this.setSize(400,300);
		this.setVisible(true); // 디폴트가 안보이므로, 보이게..
		
		// 버튼에 클릭이벤트 연결하기
		// 내부 임명 클래스 
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("나눌럿어?");
				regist();
				
				
			}
		});
	}
	
	// 게시물 등록!!
	public void regist()  {
		NoticeDTO notice = new NoticeDTO(); // empty
		notice.setTitle(title.getText()); // DTO 에 제목 주입
		notice.setWriter(writer.getText()); // DTO 에 제목 주입
		notice.setContent(content.getText()); // DTO 에 제목 주입
		
		int result = dao.regist(notice); // db insert
		if(result<1) {
			JOptionPane.showMessageDialog(this,"실패");
		} else {
			JOptionPane.showMessageDialog(this,"성공");
			
		}
	}
	
	public static void main(String[] args) {
		RegistForm win = new RegistForm();
		
	}
}
