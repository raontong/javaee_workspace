package com.ch.shop.test.school;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/*
 * 아래의 클래스는, 우리 애플리케이션에서 공통적이고도 전반적으로 사용되는 로직을
 * 특정 객체 안에 DI 로 처리하는 것이 아니라, 아에 독립적으로 하나의 관점으로 만들어,
 * 이 관점이 관여될 시점에 공통로직을 자동을 호출할 수 있는 기술인  AOP 를 구현하기 위함이다.
 * */ 
@Aspect
public class BellAspect {
	private Bell bell;
	
	public BellAspect(Bell bell) {
		this.bell=bell;
	}
	
	// 이 관점 객체가, 공톨로직인 Bell 의 ding 을 어느위치에서, 어느 시점에 적용할지를 설명하는 메서드를 작성
	// AOP 는 스프링 자체의 기술이 아니라, 예전부터 자바기반의 기술중 AspectJ 라는 시굴이 있었고, 스프링은 단지 이 AspectJ 를
	// 사용하는 것 뿐임.. 따라서 별도의 의존성이므로, 의존성 라이브러리에 추가해야한다.
	// 아래의 @Before() 어노테이션 내부에 작섣ㅇ하는 표현식 패턴은 스프링 자체의 문법이 아닌 AspectJ의 문법이므로, 형석을 따라주자
	
	/* 시점 (위치= 반환형 > 반환형 모든메서드야.*())*/
	@Before("execution(* com.ch.shop.test.school.Student.*(..))")  // 안되도 공부하는..
	public void ringBefore() {
		bell.ding();
	}
	
	@After("execution(* com.ch.shop.test.school.Student.*(..))")  // 안되도 공부하는..
	public void ringAfter() {
		bell.ding();
	}

}
