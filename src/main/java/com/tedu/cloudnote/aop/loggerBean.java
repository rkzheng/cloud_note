package com.tedu.cloudnote.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class loggerBean {
	@Before("within(com.tedu.cloudnote.controller..*)")
	public void loggerController(){
		System.out.println("进入Controller组件扫描");
	}
}
