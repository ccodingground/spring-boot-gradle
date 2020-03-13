package board.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice//예외처리 클래스 이다. 2.0부터는 어노테이션 사용가능 별도설정 불필요
public class ExceptionHandler {
	
	//모든 예외처리 설정...
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception exception) {
		ModelAndView mv=new ModelAndView();
		mv.setViewName("/error/error_default");
		mv.addObject("exception", exception);
		System.out.println("예외발생_처리..");
		return mv;
	}
}
