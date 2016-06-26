# BotKit [![Build Status](https://travis-ci.org/ramswaroop/botkit.svg?branch=master)](https://travis-ci.org/ramswaroop/botkit)
Building blocks for building bots.

__BotKit__ is a java spring-boot application _(inspired by [Howdyai's Botkit](https://github.com/howdyai/botkit))_ to 
make Slack _(Facebook and Twitter coming soon)_ bots in minutes. It provides all
the boilerplate code needed so that you can __make your bot live__ right away.

## SlackBot

### Getting started

**Running your SlackBot is just 4 easy steps:**
  
1. Clone this project `$ git clone git@github.com:ramswaroop/botkit.git` and `$ cd botkit`.  
2. [Create a slack bot](https://my.slack.com/services/new/bot) and get your slack token.  
3. Paste the token in [application.properties](/src/main/resources/application.properties) file.  
4. Run the application by running `BotKitApplication` in your IDE or via commandline `$ mvn spring-boot:run`.  

You can now start talking with your bot ;)

### Why use Botkit for Slack?

* Provides you with all the boilerplate code which handles underlying websocket connections and other complexities.  
* Fires events in addition to all the [events supported by Slack RTM API](https://api.slack.com/events)
  which makes your work even easier.  
* Receiving & sending messages is as easy as defining a `controller` method and calling `reply()`, you don't need to 
  manually parse any events nor manually encode any messages before sending.  
* Well tested with various unit tests.  

### Basic Usage

The main function of a Bot is to receive and reply messages. With this kit receiving messages is as easy as just
writing a simple controller and replying to it by calling the `reply()` method as shown below:

```java
@Controller(events = EventType.MESSAGE)
public void onReceiveDM(WebSocketSession session, Event event) {
    reply(session, event, new Message("Hi, I am a Slack Bot!"));
}
```

All the code for your bot goes in [SlackBot](/src/main/java/me/ramswaroop/botkit/slackbot/SlackBot.java) class which
extends [Bot](/src/main/java/me/ramswaroop/botkit/slackbot/core/Bot.java) from the core package. You can have as many
bots as you want, just make the class extend [Bot](/src/main/java/me/ramswaroop/botkit/slackbot/core/Bot.java) class
and it gets all the powers of a Slack Bot.

### Building a [Slack Integration](https://api.slack.com/) with Botkit

You can integrate your services into Slack by any of the following ways:

* [Bot users](https://api.slack.com/bot-users)
* [Slash Commands](http://api.slack.com/slash-commands)
* [Slack Webhooks](http://api.slack.com/incoming-webhooks)
* [Slack Apps](https://api.slack.com/slack-apps)

And Botkit currently supports:

* Bot users via [Slack Real Time Messaging (RTM) API](http://api.slack.com/rtm)
* [Slack Slash Commands](https://my.slack.com/services/new/slash-commands)
* [Slack Webhooks](https://my.slack.com/services/new/incoming-webhook/)

[//]: # (short description about bot, slash command and webhooks)

__Bots__ interact with Slack through RTM API or technically via Web Sockets. __Slash Commands__ are nothing but `GET` 
and `POST` calls to your app. Finally, __Webhooks__ can be of two types, Incoming and Outgoing. __Incoming webhooks__
is where you `POST` data from outside (i.e, your app) to Slack and 
[__Outgoing webhooks__](https://api.slack.com/outgoing-webhooks) is where Slack `POST` data to an endpoint you specify.

#### Setting up your Bot

You need to first paste your tokens/urls in [application.properties](/src/main/resources/application.properties) file:
 
```java
slackBotToken=xoxb-50014434-slacktokenx29U9X1bQ
slashCommandToken=X73Fv3tokenx242CdpEq
slackIncomingWebhookUrl=https://hooks.slack.com/services/T02WEBHOOKURLV7oOYvPiHL7y6
```

The [core](src/main/java/me/ramswaroop/botkit/slackbot/core) package contains all of Botkit code. You can create
packages outside `core` package and put your custom code there. To make a 
* __Slack Bot__ &rArr; Extend [Bot](/src/main/java/me/ramswaroop/botkit/slackbot/core/Bot.java) class.  
* __Slash Command Handler__ &rArr; Annotate your [class](/src/main/java/me/ramswaroop/botkit/slackbot/SlackSlashCommand.java)
  with Spring's `@Controller` and have a [method](/src/main/java/me/ramswaroop/botkit/slackbot/SlackSlashCommand.java#onReceiveSlashCommand)
  with the required `@RequestMapping` path receiving a set of request params as shown in the 
  [sample](/src/main/java/me/ramswaroop/botkit/slackbot/SlackSlashCommand.java).  
* __Slack Incoming Webhook__ &rArr; Just make a `POST` call with 
  [RichMessage](/src/main/java/me/ramswaroop/botkit/slackbot/core/models/RichMessage.java) whenever you want to update 
  your Slack users about something.
* __Slack Outgoing Webhook__ &rArr; Same as Slash Command Handler.

#### Receiving Messages

For __Bots__, you receive a message as [Event](/src/main/java/me/ramswaroop/botkit/slackbot/core/models/Event.java). For
almost all actions Slack fires a relevant [event](https://api.slack.com/events) for it. Unfortunately, it does not fire
appropriate events when someone directly messages the bot (direct message) or mentions the bot on a channel 
(like `@bot`). It just fires an event of type `message` for all the messages (directly to bot and to channels where bot
is a member) sent. But guess what, you're at the right place now, BotKit handles that for you. It fires two extra 
events `EventType.DIRECT_MESSAGE` and `EventType.DIRECT_MENTION` in addition to all the currently supported
[Slack events](https://api.slack.com/events).

To receive and parse slack bot events you just need to have this:

```java
@Controller(events = {EventType.DIRECT_MENTION, EventType.DIRECT_MESSAGE})
public void onReceiveDM(WebSocketSession session, Event event) {
    if (event.getText().contains("hi")) {
        reply(session, event, new Message("Hi, I am " + currentUser.getName()));
    }
}
```



#### Sending Messages



### Deploy to the Cloud

Bokit is Heroku ready, to deploy you need to perform the below simple steps: 

1. Clone this project `$ git clone git@github.com:ramswaroop/botkit.git` and `$ cd botkit`.
2. Get your [slack bot token](https://my.slack.com/services/new/bot) or 
[slash command](https://my.slack.com/services/new/slash-commands) token or 
[incoming webhook](https://my.slack.com/services/new/incoming-webhook/) url.      
3. Paste the above tokens/urls in [application.properties](/src/main/resources/application.properties) file.    
4. [Download Toolbelt](https://toolbelt.heroku.com/) for your system.  
5. `$ heroku login` - Login to Heroku.  
6. `$ heroku create` - Create an app on Heroku.  
7. `$ git push heroku master` - Push your code to Heroku.  
8. `$ heroku ps:scale web=1` - Start your application.  

You can now start talking with your Bot or send commands to your Slash Command or play with Incoming Webhooks ;)
