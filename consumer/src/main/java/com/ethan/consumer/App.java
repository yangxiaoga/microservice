package com.ethan.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.ethan.provider.service.Service;

public class App {
    public static void main( String[] args ){
    	@SuppressWarnings("resource")
		ClassPathXmlApplicationContext context = 
    			 new ClassPathXmlApplicationContext(
    		     new String[] {"consumer.xml"});
         context.start();
         Service demoService = (Service)context.getBean("demoService"); // 获取远程服务代理
         String hello = demoService.sayHello("world"); // 执行远程方法
         System.out.println( hello ); // 显示调用结果
    }
}
