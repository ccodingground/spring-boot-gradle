package board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import board.interceptor.TestInterceptor;

//WebMvcConfigurerAdapter 클래스 상속 대신 WebMvcConfigurer implements해서 사용
@Configuration
public class WebConfiguration implements WebMvcConfigurer{
	/**
     * HiddenHttpMethodFilter  
     */
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter(){
        return new HiddenHttpMethodFilter();
    }
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new TestInterceptor())
		  .excludePathPatterns("/board/open*")//제외할 요청 주소 패턴 등록
		  .addPathPatterns("/board/insert*");//적용할 요청 주소 패턴 등록 //패턴 등록이나 제외 없으면 모든 패턴
	}
	
	//아파치 Common-Fileupload 사용시
	//CommonsMultipartResolver를 이용해서 MultipartResolver구현
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver=
				new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding("utf-8");//파일의 인코딩 설정
		commonsMultipartResolver.setMaxUploadSizePerFile(5*1024*1024);
		//파일크기제한 byte 단위이고 현재 5Mb
		return commonsMultipartResolver;
	}
	

}
