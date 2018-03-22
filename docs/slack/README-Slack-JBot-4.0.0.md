## Slack Bot

### Table of Contents
1. [Getting started](#getting-started)
2. [Basic Usage](#basic-usage)
3. [Building a Slack Integration with JBot](#building-a-slack-integration-with-jbot)
    * [Setting up your bot](#setting-up-your-bot)
    * [Receiving messages](#receiving-messages)
    * [Sending messages](#sending-messages)
    * [Conversations](#conversations)
4. [Usage](#usage)
5. [Deploy to the cloud](#deploy-to-the-cloud)
6. [Documentation History](#documentation-history)
    

### Getting started

**Running your SlackBot is just 4 easy steps:**
  
1. Clone this project `$ git clone git@github.com:ramswaroop/jbot.git`.  
2. [Create a slack bot](https://my.slack.com/services/new/bot) and get your slack token.  
3. Paste the token in [application.properties](../../jbot-example/src/main/resources/application.properties) file.  
4. Run the example application by running `JBotApplication` in your IDE or via commandline: 
    ```bash
    $ cd jbot
    $ mvn clean install
    $ cd jbot-example
    $ mvn spring-boot:run
    ```

You can now start talking with your bot ;)

### Basic Usage

The main function of a Bot is to receive and reply messages. With JBot, receiving messages is as easy as just
writing a simple `@Controller` and replying to it by calling the `reply()` method as shown below:

```java
@Controller(events = EventType.MESSAGE)
public void onReceiveDM(WebSocketSession session, Event event) {
    reply(session, event, "Hi, I am a Slack Bot!");
}
```

All the code for your bot goes in [SlackBot](../../jbot-example/src/main/java/example/jbot/slack/SlackBot.java) class which
extends [Bot](../../jbot/src/main/java/me/ramswaroop/jbot/core/slack/Bot.java) from the core package. You can have as many
bots as you want, just make the class extend [Bot](../../jbot/src/main/java/me/ramswaroop/jbot/core/slack/Bot.java) class
and it gets all the powers of a Slack Bot. Though it is recommended to have separate JBot instances for different bots.

### Building a [Slack Integration](https://api.slack.com/) with JBot

You can integrate your services into Slack by any of the following ways:

* [Bot users](https://api.slack.com/bot-users)
* [Slash Commands](http://api.slack.com/slash-commands)
* [Slack Webhooks](http://api.slack.com/incoming-webhooks)
* [Slack Apps](https://api.slack.com/slack-apps)

And JBot currently supports:

* Bot users via [Slack Real Time Messaging (RTM) API](http://api.slack.com/rtm)
* [Slack Slash Commands](https://my.slack.com/services/new/slash-commands)
* [Slack Webhooks](https://my.slack.com/services/new/incoming-webhook/)

[//]: # (short description about bot, slash command and webhooks)

__Bots__ interact with Slack through RTM API or technically via Web Sockets. __Slash Commands__ are nothing but `GET` 
and `POST` calls to your app. Finally, __Webhooks__ can be of two types, Incoming and Outgoing. __Incoming webhooks__
is where you `POST` data from outside (i.e, your app) to Slack and 
[__Outgoing webhooks__](https://api.slack.com/outgoing-webhooks) is where Slack `POST` data to an endpoint you specify.

#### Setting up your app

You need to first paste your tokens/urls in [application.properties](../../jbot-example/src/main/resources/application.properties) file:
 
```.properties
slackBotToken=xoxb-50014434-slacktokenx29U9X1bQ
slashCommandToken=X73Fv3tokenx242CdpEq
slackIncomingWebhookUrl=https://hooks.slack.com/services/T02WEBHOOKURLV7oOYvPiHL7y6
```

You can directly use [jbot-example](../../jbot-example) or use jbot as a dependency. To make a 
* __Slack Bot__ &rArr; Extend [Bot](../../jbot/src/main/java/me/ramswaroop/jbot/core/slack/Bot.java) class.  
* __Slash Command Handler__ &rArr; Annotate your [class](../../jbot-example/src/main/java/example/jbot/slack/SlackSlashCommand.java)
  with Spring's `@Controller` and have a [method](../../jbot-example/src/main/java/example/jbot/slack/SlackSlashCommand.java#onReceiveSlashCommand)
  with the required `@RequestMapping` path receiving a set of request params as shown in the 
  [sample](../../jbot-example/src/main/java/example/jbot/slack/SlackSlashCommand.java).  
* __Slack Incoming Webhook__ &rArr; Just make a `POST` call with 
  [RichMessage](../../jbot/src/main/java/me/ramswaroop/jbot/core/slack/models/RichMessage.java) whenever you want to update 
  your Slack users about something.
* __Slack Outgoing Webhook__ &rArr; Same as Slash Command Handler.


Since `JBot 4.0.0`, there is a new property which helps turn specific services on/off. You can set the property in
[application.properties](../../jbot-example/src/main/resources/application.properties) file:
```.properties
spring.profiles.active=slack,facebook
```
To use Jbot for Slack only, remove "facebook" from the profiles. Note: You must have `@Profile` defined in your Slack
bot classes. See [SlackBot](../../jbot-example/src/main/java/example/jbot/slack/SlackBot.java) in jbot-example.

#### Receiving Messages

For __Bots__, you receive a message as [Event](/jbot/src/main/java/me/ramswaroop/jbot/core/slack/models/Event.java). For
almost all actions Slack fires a relevant [event](https://api.slack.com/events) for it. Unfortunately, it does not fire
appropriate events when someone directly messages the bot (direct message) or mentions the bot on a channel 
(like `@bot`). It just fires an event of type `message` for all the messages (directly to bot and to channels where bot
is a member) sent. 

But guess what, you're at the right place now, JBot handles that for you. It supports three extra 
events `EventType.DIRECT_MESSAGE`, `EventType.DIRECT_MENTION` and `EventType.ACK` in addition to all the currently 
supported [Slack events](https://api.slack.com/events). The first two events are self-explanatory, the `EventType.ACK` 
event is nothing but an acknowledgement event which acknowledges the delivery of a previously sent message. 

To receive and parse slack bot events you just need to have this:
```java
@Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE})
public void onReceiveDM(WebSocketSession session, Event event) {
    if (event.getText().contains("hi")) {
        reply(session, event, "Hi, I am " + slackService.getCurrentUser().getName());
    }
}
```

What you're doing here is annotating a method with [@Controller](/jbot/src/main/java/me/ramswaroop/jbot/core/slack/Controller.java)
annotation and passing an array events to that annotation which you want to listen to. By default your controller will
listen to `EventType.MESSAGE` events if you do not specify any events explicitly. 

You can also add regular expressions to your [@Controller](/jbot/src/main/java/me/ramswaroop/jbot/core/slack/Controller.java)
annotation like:

```java
@Controller(events = EventType.MESSAGE, pattern = "^([a-z ]{2})(\\d+)([a-z ]{2})$")
public void onReceiveMessage(WebSocketSession session, Event event, Matcher matcher) {
    reply(session, event, new Message("First group: " + matcher.group(0) + "\n" +
            "Second group: " + matcher.group(1) + "\n" +
            "Third group: " + matcher.group(2) + "\n" +
            "Fourth group: " + matcher.group(3)));
}
```

You can __optionally__ have a `matcher` as a formal parameter in the method if you want to work on the values sent 
by the user. But do keep the order of parameters as shown above.

In __Slash Commands__, you receive a `GET` or `POST` request as below:

```.properties
token=gIkuvaNzQIHg97ATvDxqgjtO
team_id=T0001
team_domain=example
channel_id=C2147483705
channel_name=test
user_id=U2147483697
user_name=Steve
command=/weather
text=94070
response_url=https://hooks.slack.com/commands/1234/5678
```

If you have configured for `POST` requests, data will be sent to your URL with a `content-type` header set as 
`application/x-www-form-urlencoded`. If you've chosen to have your slash command's URL receive invocations as a `GET`
request, no explicit `content-type` header will be set.

__NOTE:__ The URL you provide must be a __HTTPS URL__ with a valid, verifiable __SSL certificate__.

In __Incoming Webhooks__, your [application](/jbot-example/src/main/java/example/jbot/slack/SlackWebhooks.java) `POST` 
data and do not receive any data apart from the acknowledgement for your sent data. You send data
as [RichMessage](/jbot/src/main/java/me/ramswaroop/jbot/core/slack/models/RichMessage.java) to Slack Webhook URL.

In __Outgoing Webhooks__, you receive a `POST` request from Slack like below:  

```.properties
token=mbxmjpceetMUz2hfecqM31KC
team_id=T0001
team_domain=example
channel_id=C2147483705
channel_name=test
timestamp=1355517523.000005
user_id=U2147483697
user_name=Steve
text=googlebot: What is the air-speed velocity of an unladen swallow?
trigger_word=googlebot:
```

Please note that the content of [message attachments](https://api.slack.com/docs/attachments) will not be included in 
the outgoing `POST` data in case of Outgoing Webhooks.

#### Sending Messages

In __Bots__, you can use the `reply()` method defined in [Bot](/jbot/src/main/java/me/ramswaroop/jbot/core/slack/Bot.java)
class to send messages to Slack. You just need to set the text you want to send in 
[Message](/jbot/src/main/java/me/ramswaroop/jbot/core/slack/models/Message.java) and everything else will be taken care 
by JBot. But you can set other fields if you want such as `id` in the message.

Here is an example:
```java
@Controller(events = EventType.MESSAGE)
public void onReceiveMessage(WebSocketSession session, Event event) {
    reply(session, event, "Hi, this is a message!");
}
```

Under the hood the message sent is nothing but a json like below:
```json
{
    "id": 1,
    "type": "message",
    "channel": "C024BE91L",
    "text": "Hi, this is a message!"
}
```

For __Slash Commands__ and __Incoming Webhooks__, you can send messages as 
[RichMessage](/jbot/src/main/java/me/ramswaroop/jbot/core/slack/models/RichMessage.java). Just keep in mind to encode it
before sending by just calling the `encodedMessage()` method. Below is an example:
```java
@RequestMapping(value = "/slash-command",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public RichMessage onReceiveSlashCommand(@RequestParam("token") String token,
                                         @RequestParam("team_id") String teamId,
                                         @RequestParam("team_domain") String teamDomain,
                                         @RequestParam("channel_id") String channelId,
                                         @RequestParam("channel_name") String channelName,
                                         @RequestParam("user_id") String userId,
                                         @RequestParam("user_name") String userName,
                                         @RequestParam("command") String command,
                                         @RequestParam("text") String text,
                                         @RequestParam("response_url") String responseUrl) {
    // validate token
    if (!token.equals(slackToken)) {
        return new RichMessage("Sorry! You're not lucky enough to use our slack command.");
    }        
    
    /** build response */
    RichMessage richMessage = new RichMessage("The is Slash Commander!");
    richMessage.setResponseType("in_channel");
    // set attachments
    Attachment[] attachments = new Attachment[1];
    attachments[0] = new Attachment();
    attachments[0].setText("I will perform all tasks for you.");
    richMessage.setAttachments(attachments);
    return richMessage.encodedMessage(); // don't forget to send the encoded message to Slack
}
```

__Points to Note:__ 
* [Event](/jbot/src/main/java/me/ramswaroop/jbot/core/slack/models/Event.java), 
[Message](/jbot/src/main/java/me/ramswaroop/jbot/core/slack/models/Message.java) and 
[RichMessage](/jbot/src/main/java/me/ramswaroop/jbot/core/slack/models/RichMessage.java) are generic models. Not all the 
time, all the attributes present in them will have values. In other words, [Slack sends different responses for different
events](https://api.slack.com/events/hello).
* You need a __channel id__ to send replies. Therefore, you can use `reply()` method for events which have a channel id
in them or else you have to explicitly set the channel id in the 
[Message](/jbot/src/main/java/me/ramswaroop/jbot/core/slack/models/Message.java) object.

#### Conversations

This is a differentiating feature of JBot, with this you can literally talk to your bot and have a conversation. See 
below for an example as to how your bot sets up a meeting for your team by asking some simple questions one after the 
other.

![Conversation feature of JBot](http://i.imgur.com/nMchYK5.gif)

```java

    /**
     * Conversation feature of JBot. This method is the starting point of the conversation (as it
     * calls {@link Bot#startConversation(Event, String)} within it. You can chain methods which will be invoked
     * one after the other leading to a conversation. You can chain methods with {@link Controller#next()} by
     * specifying the method name to chain with.
     *
     * @param session
     * @param event
     */
    @Controller(pattern = "(setup meeting)", next = "confirmTiming")
    public void setupMeeting(WebSocketSession session, Event event) {
        startConversation(event, "confirmTiming");   // start conversation
        reply(session, event, new Message("Cool! At what time (ex. 15:30) do you want me to set up the meeting?"));
    }
```

You can start a conversation by calling `startConversation(event, nextMethodName)` within your controller. You can pass 
the event and the name of the next controller method.

```java
    /**
     * This method is chained with {@link SlackBot#setupMeeting(WebSocketSession, Event)}.
     *
     * @param session
     * @param event
     */
    @Controller(next = "askTimeForMeeting")
    public void confirmTiming(WebSocketSession session, Event event) {
        reply(session, event, new Message("Your meeting is set at " + event.getText() +
                ". Would you like to repeat it tomorrow?"));
        nextConversation(event);    // jump to next question in conversation
    }
```

This is your next method in the conversation. After your desired work is done, do not forget to call `nextConversation(event)`
to jump to the next method. You can specify the next method to call in 
[next](/jbot/src/main/java/me/ramswaroop/jbot/core/slack/Controller.java#next) attribute of 
[Controller](/jbot/src/main/java/me/ramswaroop/jbot/core/slack/Controller.java) annotation.

```java
    /**
     * This method is chained with {@link SlackBot#confirmTiming(WebSocketSession, Event)}.
     *
     * @param session
     * @param event
     */
    @Controller(next = "askWhetherToRepeat")
    public void askTimeForMeeting(WebSocketSession session, Event event) {
        if (event.getText().contains("yes")) {
            reply(session, event, new Message("Okay. Would you like me to set a reminder for you?"));
            nextConversation(event);    // jump to next question in conversation  
        } else {
            reply(session, event, new Message("No problem. You can always schedule one with 'setup meeting' command."));
            stopConversation(event);    // stop conversation only if user says no
        }
    }

    /**
     * This method is chained with {@link SlackBot#askTimeForMeeting(WebSocketSession, Event)}.
     *
     * @param session
     * @param event
     */
    @Controller
    public void askWhetherToRepeat(WebSocketSession session, Event event) {
        if (event.getText().contains("yes")) {
            reply(session, event, new Message("Great! I will remind you tomorrow before the meeting."));
        } else {
            reply(session, event, new Message("Oh! my boss is smart enough to remind himself :)"));
        }
        stopConversation(event);    // stop conversation
    }
```

**NOTE:**
* Only the first method in a conversation can define a `pattern`. `pattern` attribute in `Controller` annotation has no
effect for rest of the methods in a conversation.
* The first method in the conversation need not call `nextConversation(event)` but rest of the methods do need to.
* `next` attribute in `@Controller` should have the name of the next method in the conversation that needs to be invoked.
* To end the conversation, call `stopConversation(event)` inside your controller method.

### Usage

You can directly clone this project and use [jbot-example](../../jbot-example) or you can include it as a maven/gradle 
dependency in your project.

**Maven**

```xml
<dependency>
    <groupId>me.ramswaroop.jbot</groupId>
    <artifactId>jbot</artifactId>
    <version>4.0.1</version>
</dependency>
```

**Gradle**

```groovy
dependencies {
    compile("me.ramswaroop.jbot:jbot:4.0.1")
}
```

__NOTE:__ When you include jbot as a dependency please make sure to include `me.ramswaroop.jbot` package for auto-scan.
For example, you can specify `scanBasePackages` in `@SpringBootApplication` or `@ComponentScan`. See 
[jbot-example](../../jbot-example/src/main/java/example/jbot/JBotApplication.java) to learn more.

### Deploy to the Cloud

JBot is Heroku ready. To deploy, you need to perform the below simple steps: 

1. Clone this project `$ git clone git@github.com:ramswaroop/jbot.git` and `$ cd jbot`.
2. Get your [slack bot token](https://my.slack.com/services/new/bot) or 
[slash command](https://my.slack.com/services/new/slash-commands) token or 
[incoming webhook](https://my.slack.com/services/new/incoming-webhook/) url.      
3. Paste the above tokens/urls in [application.properties](/jbot-example/src/main/resources/application.properties) file.    
4. [Download Toolbelt](https://toolbelt.heroku.com/) for your system.  
5. `$ heroku login` - Login to Heroku.  
6. `$ heroku create` - Create an app on Heroku.  
7. `$ git push heroku master` - Push your code to Heroku.  
8. `$ heroku ps:scale web=1` - Start your application.  

You can now start talking with your Bot, send commands to your Slash Command or play with Incoming Webhooks ;)

### Documentation History

* [README-Slack-JBot-4.0.0](./README-Slack-JBot-4.0.0.md) _(Current)_
* [README-Slack-JBot-3.0.2](./README-Slack-JBot-3.0.2.md)
* [README-Slack-JBot-2.0.0](./README-Slack-JBot-2.0.0.md)
* [README-Slack-BotKit-1.1.0](./README-Slack-BotKit-1.1.0.md)
