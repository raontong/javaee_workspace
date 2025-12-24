package com.ch.shop.config.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/*이 클래스는 로직을 작성하기 위함이 아니라, 애플리케이션에서 사용할 빈(객체)들 및 그들간의 관계(weaving)을 명시하기 위한 설정 목적의 클래스이며
 * 쇼핑몰의 일반 유저들이 보게되는 애플리케이션쪽 빈들을 관리한다.*/

@Configuration // 단지 xml을 대신할 설정용 클래스에 불과
@EnableWebMvc 
// 필수!!!!!!!!!(스프링이 지원하는 MVC 프레임워크를 사용하기 위한 어노테이션)
// 일일이 빈으로 등록할 필요가 없는, 많이 알려진 빈들을 가리켜 스프링에서는 컴포넌트라 부른다.
// 또한, 이 컴포넌트들은 패키지 위치만 설정해놓으면 스프링이 알아서 찾아내서(검색) 인스턴스를 자동으로 만들어준다.
// 그렇다면 어떤게 유명한 컴포넌트들일까?
// MVC에서의 Controller는 @Controller를 붙임
// MVC에서의 DAO는 @Repository를 붙임
// MVC에서의 DAO는 @Service를 붙임
// MVC에서의 특정 분류가 딱히 없음에도 자동으로 올리고 싶다면 @Component
@ComponentScan(basePackages = {"com.ch.shop.controller.admin"})
public class AdminWebConfig extends WebMvcConfigurerAdapter{
	
	/* 아파치 파일 업로드 컴포넌트를 빈으로 등록 */
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		return resolver;
	}
}
