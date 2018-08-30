package me.ramswaroop.jbot.core.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ramswaroop
 * @version 29/03/2017
 */
public abstract class BaseBot {

    private static final Logger logger = LoggerFactory.getLogger(BaseBot.class);
    /**
     * A Map of all methods annotated with {@link Controller} where key is the {@link EventType#name()} and
     * value is a list of {@link MethodWrapper}. NOTE: It does not contain methods which are part of any
     * conversation.
     */
    protected final Map<String, List<MethodWrapper>> eventToMethodsMap = new HashMap<>();
    /**
     * A Map of all methods annotated with {@link Controller} where key is the {@link Method#getName()} and
     * value is the respective {@link MethodWrapper}.
     */
    private final Map<String, MethodWrapper> methodNameMap = new HashMap<>();
    /**
     * A List of names of the methods which are part of any conversation.
     */
    private final List<String> conversationMethodNames = new ArrayList<>();
    /**
     * A List of Queues with each Queue holding all methods for a particular conversation. Methods
     * can be chained into a conversation by {@link Controller#next()}.
     */
    protected final Map<String, Queue<MethodWrapper>> conversationQueueMap = new HashMap<>();

    /**
     * Construct a map of all the controller methods to handle RTM Events.
     */
    public BaseBot() {
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Controller.class)) {
                Controller controller = method.getAnnotation(Controller.class);
                EventType[] eventTypes = controller.events();
                String pattern = controller.pattern();
                int patternFlags = controller.patternFlags();
                String next = controller.next();

                if (!StringUtils.isEmpty(next)) {
                    conversationMethodNames.add(next);
                }

                MethodWrapper methodWrapper = new MethodWrapper();
                methodWrapper.setMethod(method);
                methodWrapper.setPattern(pattern);
                methodWrapper.setPatternFlags(patternFlags);
                methodWrapper.setNext(next);

                if (!conversationMethodNames.contains(method.getName())) {
                    for (EventType eventType : eventTypes) {
                        List<MethodWrapper> methodWrappers = eventToMethodsMap.get(eventType.name());

                        if (methodWrappers == null) {
                            methodWrappers = new ArrayList<>();
                        }

                        methodWrappers.add(methodWrapper);
                        eventToMethodsMap.put(eventType.name(), methodWrappers);
                    }
                }
                methodNameMap.put(method.getName(), methodWrapper);
            }
        }
    }

    protected void startConversation(String id, String methodName) {
        if (!StringUtils.isEmpty(id)) {
            Queue<MethodWrapper> queue = formConversationQueue(new LinkedList<>(), methodName);
            conversationQueueMap.put(id, queue);
        }
    }

    protected void nextConversation(String id) {
        Queue<MethodWrapper> queue = conversationQueueMap.get(id);
        if (queue != null) queue.poll();
    }

    protected void stopConversation(String id) {
        conversationQueueMap.remove(id);
    }

    protected boolean isConversationOn(String id) {
        return conversationQueueMap.get(id) != null;
    }

    /**
     * Search for a method whose {@link Controller#pattern()} match with the {@code Event} text or payload
     * received from Slack/Facebook and also filter out the methods in {@code methodWrappers} whose
     * {@link Controller#pattern()} do not match.
     *
     * @param text is the message from the user
     * @param methodWrappers
     * @return the MethodWrapper whose method pattern match with that of the slack message received, {@code null} if no
     * such method is found.
     */
    protected MethodWrapper getMethodWithMatchingPatternAndFilterUnmatchedMethods(String text, List<MethodWrapper> methodWrappers) {
        if (methodWrappers != null) {
            Iterator<MethodWrapper> listIterator = methodWrappers.listIterator();

            while (listIterator.hasNext()) {
                MethodWrapper methodWrapper = listIterator.next();
                String pattern = methodWrapper.getPattern();
                int patternFlags = methodWrapper.getPatternFlags();

                if (!StringUtils.isEmpty(pattern)) {
                    if (StringUtils.isEmpty(text)) {
                        listIterator.remove();
                        continue;
                    }
                    Pattern p = Pattern.compile(pattern, patternFlags);
                    Matcher m = p.matcher(text);
                    if (m.find()) {
                        methodWrapper.setMatcher(m);
                        return methodWrapper;
                    } else {
                        listIterator.remove();  // remove methods from the original list whose pattern do not match
                    }
                }
            }
        }
        return null;
    }

    /**
     * Form a Queue with all the methods responsible for a particular conversation.
     *
     * @param queue
     * @param methodName
     * @return
     */
    private Queue<MethodWrapper> formConversationQueue(Queue<MethodWrapper> queue, String methodName) {
        MethodWrapper methodWrapper = methodNameMap.get(methodName);
        queue.add(methodWrapper);
        if (StringUtils.isEmpty(methodName)) {
            return queue;
        } else {
            return formConversationQueue(queue, methodWrapper.getNext());
        }
    }

    /**
     * Wrapper class for methods annotated with {@link Controller}.
     */
    public class MethodWrapper {
        private Method method;
        private String pattern;
        private int patternFlags;
        private Matcher matcher;
        private String next;

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }

        public int getPatternFlags() {
            return patternFlags;
        }

        public void setPatternFlags(int patternFlags) {
            this.patternFlags = patternFlags;
        }

        public Matcher getMatcher() {
            return matcher;
        }

        public void setMatcher(Matcher matcher) {
            this.matcher = matcher;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MethodWrapper that = (MethodWrapper) o;

            if (!method.equals(that.method)) return false;
            if (pattern != null ? !pattern.equals(that.pattern) : that.pattern != null) return false;
            if (patternFlags != that.patternFlags) return false;
            if (matcher != null ? !matcher.equals(that.matcher) : that.matcher != null) return false;
            return next != null ? next.equals(that.next) : that.next == null;

        }

        @Override
        public int hashCode() {
            return Objects.hash(method, pattern, patternFlags, matcher, next);
        }
    }
}
