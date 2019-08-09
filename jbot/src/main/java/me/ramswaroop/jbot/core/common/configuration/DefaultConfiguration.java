package me.ramswaroop.jbot.core.common.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DefaultConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultConfiguration.class);

    /**
     * @return a new rest template object without any additional configuration if there are no other RestTemplate beans,
     * otherwise user-defined bean will be used
     */
    @ConditionalOnMissingBean
    @Bean
    public RestTemplate restTemplate() {
        LOGGER.info("There are no any rest template beans defined, creating default rest template...");

        return new RestTemplate();
    }

}
