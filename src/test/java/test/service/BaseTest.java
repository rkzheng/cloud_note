package test.service;

import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BaseTest {
	ApplicationContext ac;
	
	@Before//在每次调用@Test之前执行
	public void init(){
		String[] conf = {
			"conf/spring-mvc.xml",
			"conf/spring-mybatis.xml"
		};
		ac = new ClassPathXmlApplicationContext(conf);
	}
	
}
