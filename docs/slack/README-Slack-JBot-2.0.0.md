# JBot [![Build Status](https://travis-ci.org/ramswaroop/jbot.svg?branch=master)](https://travis-ci.org/ramswaroop/jbot) [![Gitter](https://badges.gitter.im/ramswaroop/jbot.svg)](https://gitter.im/ramswaroop/jbot?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge) [![Bountysource](https://api.bountysource.com/badge/team?team_id=160604)](https://www.bountysource.com/teams/jbot) [![MIT license](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/ramswaroop/jbot/master/LICENSE)

LEGO for building bots.

__JBot__ is a java framework _(inspired by [Howdyai's Botkit](https://github.com/howdyai/botkit))_ to 
make Slack _(Facebook and Twitter coming soon)_ bots in minutes. It provides all
the boilerplate code needed so that you can __make your bot live__ right away.

## SlackBot

### Table of Contents
1. [Getting started](#getting-started)
2. [Why use JBot for Slack?](#why-use-jbot-for-slack)
3. [Basic Usage](#basic-usage)
4. [Building a Slack Integration with JBot](#building-a-slack-integration-with-jbot)
    * [Setting up your app](#setting-up-your-app)
    * [Receiving messages](#receiving-messages)
    * [Sending messages](#sending-messages)
    * [Conversations](#conversations)
    * [Usage](#usage)
    * [Deploy to the cloud](#deploy-to-the-cloud)
5. [Contributions](#contributions)
6. [Donations](#donations)
    

### Getting started

**Running your SlackBot is just 4 easy steps:**
  
1. Clone this project `$ git clone git@github.com:ramswaroop/jbot.git` and `$ cd jbot`.  
2. [Create a slack bot](https://my.slack.com/services/new/bot) and get your slack token.  
3. Paste the token in [application.properties](/src/main/resources/application.properties) file.  
4. Run the application by running `JBotApplication` in your IDE or via commandline `$ mvn spring-boot:run`.  

You can now start talking with your bot ;)

### Why use JBot for Slack?

* Provides you with __all the boilerplate code__ which handles underlying websocket connections and other complexities.  
* Supports a few __extra events__ in addition to all the [events supported by Slack RTM API](https://api.slack.com/events)
  which makes your work a lot more easier.
* __Receiving & sending messages__ is as easy as defining a `controller` method and calling `reply()`, you don't need to 
  manually parse any events nor manually encode any messages before sending.
* __Conversation feature__ of JBot makes talking to your bot a breeze.
* __Well tested__ with unit tests.
* And many other features which can't just be mentioned here.

### Basic Usage

The main function of a Bot is to receive and reply messages. With this kit, receiving messages is as easy as just
writing a simple controller and replying to it by calling the `reply()` method as shown below:

```java
@Controller(events = EventType.MESSAGE)
public void onReceiveDM(WebSocketSession session, Event event) {
    reply(session, event, new Message("Hi, I am a Slack Bot!"));
}
```

All the code for your bot goes in [SlackBot](/src/main/java/me/ramswaroop/jbot/slackbot/SlackBot.java) class which
extends [Bot](/src/main/java/me/ramswaroop/jbot/slackbot/core/Bot.java) from the core package. You can have as many
bots as you want, just make the class extend [Bot](/src/main/java/me/ramswaroop/jbot/slackbot/core/Bot.java) class
and it gets all the powers of a Slack Bot.

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

You need to first paste your tokens/urls in [application.properties](/src/main/resources/application.properties) file:
 
```.properties
slackBotToken=xoxb-50014434-slacktokenx29U9X1bQ
slashCommandToken=X73Fv3tokenx242CdpEq
slackIncomingWebhookUrl=https://hooks.slack.com/services/T02WEBHOOKURLV7oOYvPiHL7y6
```

The [core](src/main/java/me/ramswaroop/jbot/slackbot/core) package contains all of JBot code. You can create
packages outside `core` package and put your custom code there. To make a 
* __Slack Bot__ &rArr; Extend [Bot](/src/main/java/me/ramswaroop/jbot/slackbot/core/Bot.java) class.  
* __Slash Command Handler__ &rArr; Annotate your [class](/src/main/java/me/ramswaroop/jbot/slackbot/SlackSlashCommand.java)
  with Spring's `@Controller` and have a [method](/src/main/java/me/ramswaroop/jbot/slackbot/SlackSlashCommand.java#onReceiveSlashCommand)
  with the required `@RequestMapping` path receiving a set of request params as shown in the 
  [sample](/src/main/java/me/ramswaroop/jbot/slackbot/SlackSlashCommand.java).  
* __Slack Incoming Webhook__ &rArr; Just make a `POST` call with 
  [RichMessage](/src/main/java/me/ramswaroop/jbot/slackbot/core/models/RichMessage.java) whenever you want to update 
  your Slack users about something.
* __Slack Outgoing Webhook__ &rArr; Same as Slash Command Handler.

#### Receiving Messages

For __Bots__, you receive a message as [Event](/src/main/java/me/ramswaroop/jbot/slackbot/core/models/Event.java). For
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
        reply(session, event, new Message("Hi, I am " + slackService.getCurrentUser().getName()));
    }
}
```

What you're doing here is annotating a method with [@Controller](/src/main/java/me/ramswaroop/jbot/slackbot/core/Controller.java)
annotation and passing an array events to that annotation which you want to listen to. By default your controller will
listen to `EventType.MESSAGE` events if you do not specify any events explicitly. 

You can also add regular expressions to your [@Controller](/src/main/java/me/ramswaroop/jbot/slackbot/core/Controller.java)
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

You can __optionally__ have the `matcher` as a formal parameter in the method if you want to work on the values sent 
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

In __Incoming Webhooks__, your [application](/src/main/java/me/ramswaroop/jbot/slackbot/SlackWebhooks.java) `POST` 
data and do not receive any data apart from the acknowledgement for your sent data. You send data
as [RichMessage](/src/main/java/me/ramswaroop/jbot/slackbot/core/models/RichMessage.java) to Slack Webhook URL.

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

In __Bots__, you can use the `reply()` method defined in [Bot](/src/main/java/me/ramswaroop/jbot/slackbot/core/Bot.java)
class to send messages to Slack. You just need to set the text you want to send in 
[Message](/src/main/java/me/ramswaroop/jbot/slackbot/core/models/Message.java) and everything else will be taken care 
by JBot. But you can set other fields if you want such as `id` in the message.

Here is an example:
```java
@Controller(events = EventType.MESSAGE)
public void onReceiveMessage(WebSocketSession session, Event event) {
    reply(session, event, new Message("Hi, this is a message!"));
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
[RichMessage](/src/main/java/me/ramswaroop/jbot/slackbot/core/models/RichMessage.java). Just keep in mind to encode it
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
* [Event](/src/main/java/me/ramswaroop/jbot/slackbot/core/models/Event.java), 
[Message](/src/main/java/me/ramswaroop/jbot/slackbot/core/models/Message.java) and 
[RichMessage](/src/main/java/me/ramswaroop/jbot/slackbot/core/models/RichMessage.java) are generic models. Not all the 
time, all the attributes present in them will have values. In other words, [Slack sends different responses for different
events](https://api.slack.com/events/hello).
* You need a __channel id__ to send replies. Therefore, you can use `reply()` method for events which have a channel id
in them or else you have to explicitly set the channel id in the 
[Message](/src/main/java/me/ramswaroop/jbot/slackbot/core/models/Message.java) object.

#### Conversations

This is the most wonderful feature of jbot, with this you can literally talk to your bot and have a conversation. See 
below for an example as to how your bot sets up a meeting for your team by asking some simple questions one after the 
other.

![Conversation feature in JBot](http://i.imgur.com/nMchYK5.gif)

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
[next](/src/main/java/me/ramswaroop/jbot/slackbot/core/Controller.java#next) attribute of 
[Controller](/src/main/java/me/ramswaroop/jbot/slackbot/core/Controller.java) annotation.

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

To end the conversation, call `stopConversation(event)` inside your controller method.

**NOTE:**
* Only the first method in a conversation can define a `pattern`. `pattern` attribute in `Controller` annotation has no
effect for rest of the methods in a conversation.
* The first method in the conversation need not call `nextConversation(event)` but rest of the methods do need to.

#### Usage

You can directly clone this project and start coding your bot (just don't touch the 
[core](/src/main/java/me/ramswaroop/jbot/slackbot/core) package) or you can include it as a maven/gradle dependency.

**Maven**

```xml
<dependency>
    <groupId>me.ramswaroop.jbot</groupId>
    <artifactId>jbot</artifactId>
    <version>2.0.0</version>
</dependency>
```

**Gradle**

```groovy
dependencies {
    compile("me.ramswaroop.jbot:jbot:2.0.0")
}
```

#### Deploy to the Cloud

Bokit is Heroku ready. To deploy, you need to perform the below simple steps: 

1. Clone this project `$ git clone git@github.com:ramswaroop/jbot.git` and `$ cd jbot`.
2. Get your [slack bot token](https://my.slack.com/services/new/bot) or 
[slash command](https://my.slack.com/services/new/slash-commands) token or 
[incoming webhook](https://my.slack.com/services/new/incoming-webhook/) url.      
3. Paste the above tokens/urls in [application.properties](/src/main/resources/application.properties) file.    
4. [Download Toolbelt](https://toolbelt.heroku.com/) for your system.  
5. `$ heroku login` - Login to Heroku.  
6. `$ heroku create` - Create an app on Heroku.  
7. `$ git push heroku master` - Push your code to Heroku.  
8. `$ heroku ps:scale web=1` - Start your application.  

You can now start talking with your Bot, send commands to your Slash Command or play with Incoming Webhooks ;)

### Contributions

If you would like like to contribute, raise an issue on Github and I would be more than happy to discuss :)

### Donations

[Buy me a coffee](https://www.bountysource.com/teams/jbot) so that I stay awake whole night and complete JBot soon
enough :D
