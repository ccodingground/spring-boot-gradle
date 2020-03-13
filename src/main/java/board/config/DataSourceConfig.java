package board.config;


import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement// annotation기반 트랜잭션 활성화
public class DataSourceConfig {
	
	@Autowired
	private ApplicationContext applicationContext;
	// application.properties 파일에서 mybatis.configuration 시작하는 설정을 갖고온다.
	@Bean
	@ConfigurationProperties(prefix = "mybatis.configuration")
	public org.apache.ibatis.session.Configuration mybatisConfig(){
		return new org.apache.ibatis.session.Configuration();
	}
	
	//HikariConfig hikariConfig=new HikariConfig();
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	//'spring.datasource.hikari' 시작하는  properties 를 설정하여 HikariConfig bean을 생성
	
	@Bean
	public DataSource dataSource() {
		DataSource dataSource=new HikariDataSource(hikariConfig());
		System.out.println("DataSource : " +dataSource);
		return dataSource;
	}
	//HikariConfig Bean을이용해서HikariDataSource -> DataSource Bean생성
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
		//스프링 마이바티스에서는 SqlSessionFactory 생성하기 위해서 SqlSessionFactoryBean 객체을 이용
		SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
		//DataSource 를 설정
		sqlSessionFactoryBean.setDataSource(dataSource);
		
		//Mapper 를 설정
		sqlSessionFactoryBean.setMapperLocations(
				applicationContext.getResources("classpath:/mapper/**/sql-*.xml"));
		// classpath:/mapper/sql-board.xml
		// classpath:/mapper/**/sql-*.xml
		// classpath --> src/main/resources위치에 해당
		// /mapper/**/ -> /**/ : mapper 폴더 아래 모든 하위폴더
		// sql-*.xml : sql-시작하고 .xml 로 끝나는 모든 파일
		
		//sqlSessionFactoryBean.setConfiguration(mybatisConfig());
		//mybatis 설정추가
		
		return sqlSessionFactoryBean.getObject();
		
	}
	
	// 스프링 마이바티스에서는 SqlSessionFactory --> SqlSession 객체를 만들지 않고
	// SqlSessionTemplate Bean을 만들어 사용합니다.
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	//스프링이 제공하는 트랜잭션 매니져 등록
	@Bean
	public PlatformTransactionManager transactionManager() throws Exception{
		//DataSource dataSource=dataSource();
		//System.out.println(dataSource);
		return new DataSourceTransactionManager(dataSource());
	}
	
	//JPA설정 빈 등록
	@Bean
	@ConfigurationProperties(prefix = "spring.jpa")
	public Properties hibernateConfig() {
		return new Properties();
	}
	
}
