# JBot [![Build Status](https://travis-ci.org/ramswaroop/jbot.svg?branch=master)](https://travis-ci.org/ramswaroop/jbot) [![Javadocs](http://www.javadoc.io/badge/me.ramswaroop.jbot/jbot.svg?color=orange)](http://www.javadoc.io/doc/me.ramswaroop.jbot/jbot) [![Facebook](https://img.shields.io/badge/social-fb-blue.svg)](https://www.facebook.com/jbotframework/) [![Gitter](https://badges.gitter.im/ramswaroop/jbot.svg)](https://gitter.im/ramswaroop/jbot?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge) [![Backers on Open Collective](https://opencollective.com/jbot/backers/badge.svg)](#backers) [![Sponsors on Open Collective](https://opencollective.com/jbot/sponsors/badge.svg)](#sponsors) [![MIT license](https://img.shields.io/badge/license-GPL_3.0-yellow.svg)](https://raw.githubusercontent.com/ramswaroop/jbot/master/LICENSE)

Make bots in Java.

__JBot__ is a java framework _(inspired by [Howdyai's Botkit](https://github.com/howdyai/botkit))_ to
make Slack, Facebook bots _(Twitter coming soon)_ in minutes. It provides all
the boilerplate code needed so that you can make your bot live right away.

## Why use JBot?

* Provides you with __all the boilerplate code__ which handles underlying websocket connections and other complexities.
* Supports a few __extra events__ in addition to all the [events supported by Slack RTM API](https://api.slack.com/events)
  which makes your work a lot more easier.
* __Receiving & sending messages__ is as easy as defining a `@Controller` and calling `reply()`.
* __Conversation feature__ of JBot makes talking to your bot a breeze. This feature makes JBot different than rest of the Java frameworks out there.
* __Well tested__ with good coverage unit tests.
* And many other features which can't just be mentioned here.

**Not satisfied?** Read on...

* JBot got more than __400 stars__ in just 2 days after release.
* It is in the [Hacker News](https://news.ycombinator.com/item?id=12239667) 50 club.
* Chosen by [DZone daily picks](http://mailer.dzone.com/display.php?M=15184241&C=dcebb6887365120539df1fbf19a071ed&S=9043&L=658&N=4604).
* Last but not the least, it's listed on [Slack.com](https://api.slack.com/community)

**Still not satisfied?** Open an [issue on Github](https://github.com/ramswaroop/jbot/issues) and we can chat.

## JBot for Slack

**Running your SlackBot is just 4 easy steps:**
  
1. Clone this project `$ git clone git@github.com:ramswaroop/jbot.git`.  
2. [Create a slack bot](https://my.slack.com/services/new/bot) and get your slack token.  
3. Paste the token in [application.properties](/jbot-example/src/main/resources/application.properties) file.  
4. Run the example application by running `JBotApplication` in your IDE or via commandline: 
    ```bash
    $ cd jbot-example
    $ mvn spring-boot:run
    ```

You can now start talking with your bot ;)

Read the detailed [Slack documentation](/docs/slack/README-Slack-JBot-4.0.0.md) to learn more.

## JBot for Facebook

**Similar to Slack, Facebook is simple too but has few extra steps:**

1. Clone this project `$ git clone git@github.com:ramswaroop/jbot.git`.
2. Create a [facebook app](https://developers.facebook.com/docs/apps/register#create-app) and a 
[page](https://www.facebook.com/pages/create).
3. Generate a Page Access Token for the page (inside app's messenger settings).
4. Paste the token created above in [application.properties](/jbot-example/src/main/resources/application.properties) file.
5. Run the example application by running `JBotApplication` in your IDE or via commandline: 
    ```bash
    $ cd jbot-example
    $ mvn spring-boot:run
    ```
6. Setup webhook to receive messages and other events. You need to have a public address to setup webhook. You may use
[localtunnel.me](https://localtunnel.me) to generate a public address if you're running locally on your machine.
7. Specify the address created above in "Callback Url" field under "Webooks" setting and give the verify token 
as `fb_token_for_jbot` and click "Verify and Save".

You can now start messaging your bot by going to the facebook page and clicking on the "Send message" button.

Read the detailed [Facebook documentation](/docs/facebook/README-Facebook-JBot-4.0.0.md) to learn more.

## Contributors

This project exists thanks to all the awesome people who contribute. If you want to flaunt your skills or make 
the project even better, [you can start contributing](/CONTRIBUTING.md) and for any queries please just 
[raise an issue](https://github.com/ramswaroop/jbot/issues) and I would be more than happy to discuss :)

<a href="graphs/contributors"><img src="https://opencollective.com/jbot/contributors.svg?width=890" /></a>

## Backers

Thank you to all our backers! 🙏 [[Become a backer](https://opencollective.com/jbot#backers)]

<a href="https://opencollective.com/jbot#backers" target="_blank"><img src="https://opencollective.com/jbot/backers.svg?width=890"></a>

## Sponsors

Support this project by becoming a sponsor. Your logo will show up here with a link to your website. [[Become a sponsor](https://opencollective.com/jbot#sponsors)]

<a href="https://opencollective.com/jbot#sponsors" target="_blank"><img src="https://opencollective.com/jbot/sponsors.svg?width=890"></a>

<!--
<a href="https://opencollective.com/jbot/sponsor/0/website" target="_blank"><img src="https://opencollective.com/jbot/sponsor/0/avatar.svg"></a>
<a href="https://opencollective.com/jbot/sponsor/1/website" target="_blank"><img src="https://opencollective.com/jbot/sponsor/1/avatar.svg"></a>
-->