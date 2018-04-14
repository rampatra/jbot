package me.ramswaroop.jbot.core.slack;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Maciej Walkowiak
 * @version 14/04/2018
 */
@Configuration
@EnableConfigurationProperties(SlackProperties.class)
public class SlackConfiguration {
}
