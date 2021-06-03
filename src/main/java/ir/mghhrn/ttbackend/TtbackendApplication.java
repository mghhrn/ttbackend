package ir.mghhrn.ttbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class TtbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TtbackendApplication.class, args);
	}

}
