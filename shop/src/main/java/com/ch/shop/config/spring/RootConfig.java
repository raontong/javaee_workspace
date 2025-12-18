package com.ch.shop.config.spring;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiTemplate;
import org.springframework.transaction.PlatformTransactionManager;

// 이 클래스는 로직 작성용이 아니라, 전통적으로 사용해왔던 스프링의 빈을 등록하는 용도의 xml을 대신하기 위한 자바 클래스이다.
// 특히, 이 클래스에 등록될 빈들은 비즈니스 로직을 처리하는 모델 영역의 빈들이므로, 서블릿 수준의 스프링컨테이너가 사용해서는 안되며
//모든 서블릿이 접근할 수 있는 객체인 SErvletContext  수준에서의 스프링컨테이너가 이 클래스를 읽어들여 빈들의 인스턴스를 관리해야한다.
@Configuration // xml을 대신할거야
@ComponentScan(basePackages={"com.ch.shop.model"})
public class RootConfig {
	   /*
	    스프링이 MVC 프레임워크중 컨트롤러 영역만을 지원하는 것이 아니라, 데이터 베이스 관련 제어도 지원하므로,
	    지금까지 순수하게 사용해왔던 mybatis를 스프링이 지원하는 mybatis로 전환해본다.
	    스프링이 지원하는 데이터 연동 기술을 사용하려면, spring jdbc 라이브러리를 추가해야 한다.
	    spring jdbc 4330
	    */
	   /*--------------------------------------------------------------
	    * 1) 개발자가 사용하고 싶은 데이터 소스를 결정 - 톰캣이 지원하는 JNDI를 사용할 예정
	    *------------------------------------------------------------- */
		@Bean
		public DataSource dataSource() throws NamingException{
			JndiTemplate jndi = new JndiTemplate();
			return jndi.lookup("java:comp/env/jndi/mysql", DataSource.class);
		}
		
	   /*--------------------------------------------------------------
	    * 2) 트랜잭션 매니저 등록
	    * - 스프링은 개발자가 사용하는 기술이 JDBC, Mybatis, Hibernate, JPA 이건 상관없이 일관된 방법으로 트랜잭션을 처리할 수 있는 방법을 제공해주는데
	    * 개발자는 자신이 사용하는 기술에 따라 적절한 트랜잭션 매니저를 등록해야 한다.
	    * 예) JDBC 사용 시 - DataSourceTransactionManager를 빈으로 등록해야 함.
	    * 예) Hivernate 사용 시 - HibernateTransactionManager를 빈으로 등록해야 함.
	    * 예) Mybatis 사용 시 - DataSourceTransactionManager를 빈으로 등록해야 함.
	    *       특히 mybatis의 경우 jdbc와 동일한 datasourceTransactionManager를 사용하는 이유는?
	    * 사실 mybatis는 내부적으로 jdbc를 사용하기 때문
	    * 그리고 이 모든 트랜잭션 매니저의 최상단 객체가 바로 PlatformTransactionManager다.
	    *------------------------------------------------------------- */
		@Bean
		public PlatformTransactionManager transactionManager(DataSource dataSource) {
			return new DataSourceTransactionManager(dataSource);
		}
		
	   /*-----------------------------------------------------------------
	    * 3) SqlSession을 관리하는 mybatis의 SqlSessionFactory를 빈으로 등록
	    *-------------------------------------------------------------- */
	   @Bean
		public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
		   // 순수 mybatis 프레임워크 자체에서 지원하는 객체가 아니라, mybatis-spring에서 지원하는 객체인
		   // SqlSessionFactoryBean (끝에 Bean) 을 이용하여 설정 xml 파일을 로드한다.
		   SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		   // 패키지에 포함된 파일의 유형이 클래스가 아닌 경우 더이상 패키지로 표현하지 말고 일반 디렉토리로 취급해야 한다. 그래서 . 대신 /으로 작성!
		   sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("com/ch/shop/config/mybatis/config.xml"));
			
		   sqlSessionFactoryBean.setDataSource(dataSource);
			
		   return sqlSessionFactoryBean.getObject();
	   }
	   
	   /*--------------------------------------------------------------
	    4)SqlSessionTemplate @Bean으로 등록 <- SqlSessionFactoryBean 이 먼저 필요
	    mybatis 사용 시 쿼리문 수행을 위해서는 SqlSession 을 이용했으나, mybatis-spring에서는 SqlSessionTemplate 객체를 사용해야 함 
	   ---------------------------------------------------------------*/
		@Bean
		public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception{
			return new SqlSessionTemplate(sqlSessionFactory);
		}
}
