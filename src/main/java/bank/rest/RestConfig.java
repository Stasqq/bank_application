package bank.rest;

import bank.rest.controllers.config.RestControllersConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import bank.rest.security.WebSecurityConfig;

@Configuration
@Import({
        WebSecurityConfig.class,
        RestControllersConfig.class
})
public class RestConfig {
}
