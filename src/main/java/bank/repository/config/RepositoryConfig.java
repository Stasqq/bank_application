package bank.repository.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("bank.repository")
@ComponentScan("bank.repository")
public class RepositoryConfig {
}
