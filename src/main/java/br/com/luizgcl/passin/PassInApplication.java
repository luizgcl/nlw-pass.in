package br.com.luizgcl.passin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PassInApplication {

	public static void main(String[] args) {
		SpringApplication.run(PassInApplication.class, args);
	}

}
