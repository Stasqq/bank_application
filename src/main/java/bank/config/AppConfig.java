package bank.config;

import bank.datamodel.config.EntityConfig;
import bank.rest.RestConfig;
import bank.services.config.ServicesConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import bank.repository.config.RepositoryConfig;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@Import({
        EntityConfig.class,
        RepositoryConfig.class,
        ServicesConfig.class,
        RestConfig.class
})
@EnableAsync
public class AppConfig {
}
