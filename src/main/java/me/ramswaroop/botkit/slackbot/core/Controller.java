package me.ramswaroop.botkit.slackbot.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ramswaroop on 12/06/2016.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
    EventType[] events() default EventType.MESSAGE;
}
