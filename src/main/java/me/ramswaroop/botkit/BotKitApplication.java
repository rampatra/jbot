package me.ramswaroop.botkit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BotKitApplication {

    private static Logger logger = LoggerFactory.getLogger(BotKitApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BotKitApplication.class, args);
        logger.info("Bot started!");
    }

}
