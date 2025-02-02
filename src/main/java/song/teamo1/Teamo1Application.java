package song.teamo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Teamo1Application {

	public static void main(String[] args) {
		SpringApplication.run(Teamo1Application.class, args);
	}

}
