package io.joo.plus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PlusApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlusApplication.class, args);
	}

}
