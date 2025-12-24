package com.ch.shop.config.spring;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ch.shop.dto.OAuthClient;

/*
 이 클래스는 로직을 작성하기 위함이 아니라, 애플리케이션에서 사용할 빈(객체)들 및 그들간의 관계(weaving)를 명시하기 위한
 설정 목적의 클래스이며, 쇼핑몰의 일반 유저들이 보게 되는 애플리케이션쪽 빈들을 관리한다 
*/
@Configuration //단지 xml 을 대신한 설정용 클래스에 불과해!!
@EnableWebMvc //필수 설정( 스프링이 지원하는 MVC 프레임워크를 사용하기 위한 어노테이션) 

//일일이 빈으로 등록할 필요가 없는 많이 알려진 빈들을 가리켜
//스프링에서는 컴포넌트라  부른다. 또한 이 컴포넌트들은 
//패키지 위치만 설정해놓으면 스프링이 알아서 찾아내서 (검색)
//인스턴스를 자동으로 만들어준다
//MVC에서의 Controller는 @Controller 를 붙임 
//MVC에서의 DAO 는 @Repository 를 붙임 
//MVC에서의 DAO 는 @Service 를 붙임 
//MVC에서의 특정 분류가 딱히 없음에도 자동으로 올리고 싶다면 @Component
@ComponentScan(basePackages = {"com.ch.shop.controller.shop"})
public class ShopWebConfig extends WebMvcConfigurerAdapter{
	 
	//수동으로 context.xml등에 명시된 외부 자원을 JNDI 방식으로 읽어들일 수 있는 스프링의 객체
	@Bean
	public JndiTemplate jndiTemplate() {
		return new JndiTemplate();
	}
	
	/*----------------------------------------------------------
	Google
	-----------------------------------------------------------*/
	@Bean
	public String googleClientId(JndiTemplate jndiTemplate) throws Exception{
		return (String)jndiTemplate.lookup("java:comp/env/google/client/id"); // java:comp
	}
	
	@Bean
	public String googleClientSecret(JndiTemplate jndiTemplate) throws Exception{
		return (String)jndiTemplate.lookup("java:comp/env/google/client/secret");
	}
	
	/*
	Oauth 로그인시 사용되는 환경 변수 (요청주소, 콜백주소.. 등등) 는 객체로 담아서 관리하면 유지가히 좋다
	우리의 경우 여러 브로바이더를 연동할 것이므로, OAuthClient 객체를 여러개 메모리에 보관해놓자
	*/
	@Bean
	public Map<String, OAuthClient> oauthClients(
			@Qualifier("googleClientId") String googleClientId,
			@Qualifier("googleClientSecret") String googleClientSecret
			) {
				
		// 구글, 네이버, 카카오를 각각 OAuthClient 인스턴스 담은 후, 다시 Map에 모아두자
		Map<String, OAuthClient> map=new HashMap<>();
			
		// 구글 등록
		OAuthClient google=new OAuthClient();
		google.setProvider("google");
		google.setClientId(googleClientId);
		google.setClientSecret(googleClientSecret);
		google.setAuthorizeUrl("https://accounts.google.com/o/oauth2/v2/auth");  //  google  api  문서에
		google.setTokenUrl("https://oauth2.googleapis.com/token");
		google.setScope("openid email profile"); // 사용자에 대한 접근 범위
		google.setRedirectUrlString("http://localhost:8888/login/callback/google");
		
		map.put("google", google);
		
		// 네이버 등록
		
		return map;
		}
		
	
}





