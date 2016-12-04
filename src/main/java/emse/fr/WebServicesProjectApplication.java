package emse.fr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"emse.fr","emse.fr.controller","emse.fr.model","emse.fr.hello"})


public class WebServicesProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebServicesProjectApplication.class, args);
	}
}

