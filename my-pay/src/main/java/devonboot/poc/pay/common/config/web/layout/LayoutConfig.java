package devonboot.poc.pay.common.config.web.layout;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LayoutConfig {

    // thymeleaf layout
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}
