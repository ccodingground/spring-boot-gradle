package board.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect//java에서 AOP설정
public class AroundAspect{
	//advice(pointcut)
	@Around("execution(* board..*Controller.*(..) ) or execution(* board.service.*Impl.*(..) )")
	public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable{
		String name=joinPoint.getSignature().getDeclaringTypeName();//qualified name
		String type="";
		if(name.contains("service")) {
			type="ServiceImpl \t: "+name+ "/"+ joinPoint.getSignature().getName();//method name
		}else if(name.contains("controller")) {
			type="Controller \t: "+name+ "/"+ joinPoint.getSignature().getName();//method name
		}
		//System.out.println(type+"실행전 처리");
		
		Object result=joinPoint.proceed();
		
		//System.out.println(type+"실행후 처리");
		return result;
	}
}
