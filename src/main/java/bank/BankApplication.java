package bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import bank.config.AppConfig;

@SpringBootApplication
@Import({AppConfig.class})
public class BankApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}

}
