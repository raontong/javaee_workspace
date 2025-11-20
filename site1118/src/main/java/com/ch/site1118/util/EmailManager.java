package com.ch.site1118.util;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;

//  이메일을 발송해주는 객체 정의
// javaSE 기반에서 이미 메일을 발솔할수 있는 api가 지원됨.. jar 형태 의 라이브러리.. 
//activation-1.0.2.jar / javax.mail-1.5.5.jar
// 서블릿으로 정의하자
public class EmailManager extends HttpServlet {

	String host = "smtp.gmail.com"; // 사용하고자 하는 메일 서버 주소
	String user = "raontong@gmail.com"; // 메일 서버의 사용자 계정
	String password = "lmyw wihl ezqu vrhj"; // 앱비밀번호
	Properties props = new Properties(); // java.util.map의 자식 key-value 쌍을 갖는 데이터 형식

//메일 발송 메서드
	public void send(String to) {
		// props 객체에 필요한 모든 설정 정보의 쌍을 대입
		// 참고로 key값은 이미 정해진 것
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "465"); // 구글 smtp(보내는 메일서버)의 포트번호
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		// props.put("mail.smtp.ssl.trust","smtp.gmail.com");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2"); // TLS버전 강제

		// Session 생성 javax.mail
		Session session = Session.getDefaultInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		// 제목, 내용 등의 메일 작성
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(user)); // 메일발송자
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // 메일 받을 사람
			message.setSubject("회원가입 축하 드립니다.");
			message.setContent("회원 가입완료 ", to);
			message.setContent("<h1>감사합니다.</h1>회원가입 완료되었습니다.", "text/hemlk;charset=utf-8");

			Transport.send(message); // 메일발송

			System.out.println("이메일 발송 성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
