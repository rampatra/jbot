# BotKit
Building blocks for building bots.

__BotKit__ is a java spring-boot application _(inspired by [Howdyai's Botkit](https://github.com/howdyai/botkit))_ to 
make Slack _(Facebook and Twitter coming soon)_ bots in minutes. It provides all
the boilerplate code needed so that you can __make your bot live__ right away.

## SlackBot

### Getting started

**Running your SlackBot is just 4 easy steps:**
  
1. Clone this project `git clone git@github.com:ramswaroop/botkit.git`.  
2. [Create a slack bot](https://my.slack.com/services/new/bot) and get your slack token.  
3. Paste the token in [application.properties](/src/main/resources/application.properties) file.  
4. Run the application by running `BotKitApplication` in your IDE or via commandline `$ mvn spring-boot:run`.  

You can now start talking with your bot ;)

Botkit currently supports:

* [Slack Real Time Messaging (RTM) API](http://api.slack.com/rtm)
* [Slack Slash Commands](http://api.slack.com/slash-commands)
* [Slack Webhooks](http://api.slack.com/incoming-webhooks)

### Basic Usage

The main function of a Bot is to receive and reply messages. With this kit receiving messages is as easy as just
writing a simple controller and replying to it by calling the `reply()` method as shown below:

```java
@Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE})
public void onReceiveDM(WebSocketSession session, Event event) {
    reply(session, event, new Message("Hi, I am a Slack Bot!"));
}
```

All the code for your bot goes in [SlackBot](/src/main/java/me/ramswaroop/botkit/slackbot/SlackBot.java) class which
extends [Bot](/src/main/java/me/ramswaroop/botkit/slackbot/core/Bot.java) from the core package. You can have as many
bots as you want, just make the class extend [Bot](/src/main/java/me/ramswaroop/botkit/slackbot/core/Bot.java) class
and it gets all the powers of a Slack Bot.




