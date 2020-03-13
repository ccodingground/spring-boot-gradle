package board.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

//HandlerInterceptorAdapter 상속받아서 처리
public class TestInterceptor extends HandlerInterceptorAdapter {

	//컨트롤러가 실행되기 전에 수행한다.
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("TestInterceptor preHandle 실행");
		return super.preHandle(request, response, handler);
	}
	
	//컨트롤러가 정상적으로 끝나고 수행한다.
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("TestInterceptor postHandle 실행");
	
		super.postHandle(request, response, handler, modelAndView);
	}
	//컨트롤러가 끝날때 오류발생해도 실행
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("TestInterceptor afterCompletion 실행");
		super.afterCompletion(request, response, handler, ex);
	}
	
	

	
}
