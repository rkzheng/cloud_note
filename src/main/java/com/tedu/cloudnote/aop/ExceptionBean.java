package com.tedu.cloudnote.aop;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect


public class ExceptionBean {
	//指定异常通知切入点表达式
	@AfterThrowing(throwing="e",pointcut="within(com.tedu.cloudnote.controller..*)")
	public void execute(Exception e){
		//e就是目标方法抛出的异常对象
		try {
			FileWriter fw = 
					new FileWriter("/Users/zhengruikai/Documents/workspace/cloudnote/target/note_error.log",true);
			PrintWriter pw = new PrintWriter(fw);
			//
			SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			String time = sdf.format(new Date());
			pw.println("******************************************");
			pw.println("**发生时间:"+time);
			pw.println("**异常类型:"+e);
			pw.println("*****************异常详情******************");			
			
			e.printStackTrace(pw);
			pw.close();
			fw.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
