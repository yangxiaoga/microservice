package com.ethan.provider;

import java.io.IOException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class App {
    public static void main( String[] args ){
        
    	@SuppressWarnings("resource")
		ClassPathXmlApplicationContext context = 
    			new ClassPathXmlApplicationContext(
    			new String[] {"provider.xml"}
    	);
        context.start();
        try {
			System.in.read(); // 阻塞，按任意键退出
		} catch (IOException e) {
			e.printStackTrace();
		} 
    }
}
