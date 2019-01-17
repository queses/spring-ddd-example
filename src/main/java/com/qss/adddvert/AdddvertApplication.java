package com.qss.adddvert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

//@ImportResource({"classpath:META-INF/dataSource.xml"})
@SpringBootApplication
public class AdddvertApplication {
	public static void main(String[] args) {
		SpringApplication.run(AdddvertApplication.class, args);
	}
}

