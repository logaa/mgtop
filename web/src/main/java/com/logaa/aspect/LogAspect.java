package com.logaa.aspect;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.logaa.domain.es.RequestLog;
import com.logaa.repository.es.RequestLogRepository;
import com.logaa.util.IpUtils;
import com.logaa.util.date.TimestampUtils;

@Aspect
@Component
public class LogAspect {

	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
	
	@Autowired RequestLogRepository requestLogRepository;

	//@Pointcut("execution(* com.logaa.rest..*(..)) || execution(* com.logaa.controller..*(..))") // 两个..代表所有子目录，最后括号里的两个..代表所有参数
	@Pointcut("execution(* com.logaa.rest..*(..))")
	public void logPointCut() {}

	@Before("logPointCut()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {}

	@Around("logPointCut()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		String ip = IpUtils.getIpAddr(request);
		String url = request.getRequestURL().toString();
		String httpMethod = request.getMethod();
		String classMethod = pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName();
		String args = Arrays.toString(pjp.getArgs());
		logger.info("REQUEST_IP: " + ip);
		logger.info("REQUEST_URL: " + url);
		logger.info("HTTP_METHOD: " + httpMethod);
		logger.info("CLASS_METHOD: " + classMethod);
		logger.info("ARGS : " + args);
		long startTime = System.currentTimeMillis();
		Object ob = pjp.proceed();
		long proceedTime = System.currentTimeMillis() - startTime;
		logger.info("PROCEED_TIME: " + proceedTime);
		if(url != null && url.contains("/request-log/")) return ob; // 排除例外
		requestLogRepository.save(new RequestLog(TimestampUtils.getTimestamp(), ip, url, 
				httpMethod, classMethod, args, proceedTime, ob.toString(), null));
		return ob;
	}
	
	@AfterThrowing(throwing = "e", pointcut = "logPointCut()")
    public void afterThrowing(JoinPoint jp, Throwable e){
		logger.info("EXCEPTION: " + e.toString());
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		String ip = IpUtils.getIpAddr(request);
		String url = request.getRequestURL().toString();
		String httpMethod = request.getMethod();
		String classMethod = jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName();
		String args = Arrays.toString(jp.getArgs());
		if(url != null && url.contains("/request-log/")) return;  // 排除例外
		requestLogRepository.save(new RequestLog(TimestampUtils.getTimestamp(), ip, url, 
				httpMethod, classMethod, args, null, null, e.toString()));
	}
	
	@AfterReturning(returning = "ret", pointcut = "logPointCut()")
	public void doAfterReturning(Object ret) throws Throwable {		
		logger.info("RETURNING: " + ret);
	}
	
}
