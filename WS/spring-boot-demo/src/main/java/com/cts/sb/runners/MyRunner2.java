package com.cts.sb.runners;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyRunner2 implements CommandLineRunner {
	
	@Autowired
	@Qualifier("today")
	private LocalDate date1;
	
	@Autowired
	@Qualifier("yesterday")
	private LocalDate date2;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("This is the second spring runner");
		System.out.println(date1);
		System.out.println(date2);
	}

}
