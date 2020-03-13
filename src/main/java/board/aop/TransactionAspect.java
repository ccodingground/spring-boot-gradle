package board.aop;

import java.util.Collections;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;
//외부라이브러리 적용시 사용하면 효과적이다.
@Configuration
public class TransactionAspect {
	private static final String AOP_TRANSACTION_METHOD_NAME=
			"*";
	private static final String AOP_TRANSACTION_EXPRISSION=
			"execution(* board..*Impl.select*(..) )";
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Bean
	public TransactionInterceptor transactionAdvice() {
		RuleBasedTransactionAttribute transactionAttribute=
				new RuleBasedTransactionAttribute();
		
		//트랜잭션 이름 설정
		transactionAttribute.setName(AOP_TRANSACTION_METHOD_NAME);
		//트랜잭션에서 롤백을 하는 룰 적용
		//예외가 발생하면 트랜잭션 적용하도록 ...
		transactionAttribute.setRollbackRules(
				Collections.singletonList(new RollbackRuleAttribute(Exception.class)));

		
		MatchAlwaysTransactionAttributeSource source=
				new MatchAlwaysTransactionAttributeSource();	
		source.setTransactionAttribute(transactionAttribute);
		
		return new TransactionInterceptor(transactionManager, source);
	}
	
	@Bean
	public Advisor transactionAdviceAdvisor() {
		AspectJExpressionPointcut pointcut=
				new AspectJExpressionPointcut();
		pointcut.setExpression(AOP_TRANSACTION_EXPRISSION);
		
		return new DefaultPointcutAdvisor(pointcut, transactionAdvice());
	}
	
}
