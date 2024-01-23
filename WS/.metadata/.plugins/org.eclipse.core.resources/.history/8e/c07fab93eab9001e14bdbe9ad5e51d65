package com.cts.sb.runners;

import java.time.LocalDate;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.cts.sb.services.GreetService;

@Component
public class WelcomeRunner implements CommandLineRunner {

	@Value("${app.title:MySpringBootApp}")
	private String appTitle;
	
	@Autowired
	private Scanner scan;
	
	@Autowired
	private LocalDate date;
	
	@Autowired
	@Qualifier("greetServiceSimpleImpl")
	private GreetService greetService1;
	
	@Autowired
	@Qualifier("greetServiceTimeBasedImpl")
	private GreetService greetService2;

	@Override
	public void run(String... args) throws Exception {

		System.out.println(appTitle);
		System.out.println("Hello World! I am Spring Boot @.x!");
		
		System.out.println("Today: "+date);
		
		System.out.println("Whats your name? ");
		String userName = scan.nextLine();
		
		System.out.println(greetService1.greet(userName));
		System.out.println(greetService2.greet(userName));
	}

}