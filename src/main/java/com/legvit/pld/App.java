package com.legvit.pld;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	
	private static final String[] CONFIG_PATH = new String[] {"com/legvit/pld/springconfig/applicationContext.xml", 
															  "com/legvit/pld/springconfig/pld-batch-quartz.xml", 
															  "com/legvit/pld/springconfig/pld-quartz.xml",
															  "com/legvit/pld/springconfig/pld-dao.xml"};
	
    public static void main( String[] args ) {
        final ApplicationContext context = new ClassPathXmlApplicationContext(CONFIG_PATH);
    }
}
