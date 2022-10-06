package org.aaron.savage.hiking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class HikingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HikingApplication.class, args);
	}

}
