package me.ramswaroop.jbot.core.slack;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for different Event types in Slack RTM API.<br>
 * Method must have next signature:
 * <ul>
 * <li><code>void doXXX
 *     ({@link org.springframework.web.socket.WebSocketSession},
 *     {@link me.ramswaroop.jbot.core.slack.models.Event})</code> for Bot method</li>
 * <li><code>void doXXX
 *     ({@link org.springframework.web.socket.WebSocketSession},
 *     {@link me.ramswaroop.jbot.core.slack.models.Event},
 *     {@link java.util.regex.Matcher})</code> for Bot method</li>
 * <li><code>void doXXX
 *     ({@link Bot}, {@link org.springframework.web.socket.WebSocketSession},
 *     {@link me.ramswaroop.jbot.core.slack.models.Event})</code> for non Bot method</li>
 * <li><code>void doXXX
 *     ({@link Bot}, {@link org.springframework.web.socket.WebSocketSession},
 *     {@link me.ramswaroop.jbot.core.slack.models.Event},
 *     {@link java.util.regex.Matcher})</code> for non Bot method</li>
 * </ul>
 * <br>
 * Be careful: matcher may be null
 *
 * @author ramswaroop
 * @version 1.0.0, 12/06/2016.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {

    EventType[] events() default EventType.MESSAGE;

    String pattern() default "";

    String next() default "";
}
