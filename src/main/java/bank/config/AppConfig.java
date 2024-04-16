package bank.config;

import bank.entity.config.EntityConfig;
import bank.rest.RestConfig;
import bank.services.ServicesConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import bank.repository.config.RepositoryConfig;

@Configuration
@Import({
        EntityConfig.class,
        RepositoryConfig.class,
        ServicesConfig.class,
        RestConfig.class
})
public class AppConfig {
}
