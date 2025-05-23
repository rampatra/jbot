# JBot [![Build Status](https://travis-ci.org/rampatra/jbot.svg?branch=master)](https://travis-ci.org/rampatra/jbot) [![Codacy Badge](https://app.codacy.com/project/badge/Grade/aa94e04874154ff2bb3d42f2b600ac55)](https://www.codacy.com/gh/rampatra/jbot/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=rampatra/jbot&amp;utm_campaign=Badge_Grade) [![Javadocs](http://www.javadoc.io/badge/me.ramswaroop.jbot/jbot.svg?color=orange)](http://www.javadoc.io/doc/me.ramswaroop.jbot/jbot) [![Facebook](https://img.shields.io/badge/social-fb-blue.svg)](https://www.facebook.com/jbotframework/) [![Backers on Open Collective](https://opencollective.com/jbot/backers/badge.svg)](#backers) [![Sponsors on Open Collective](https://opencollective.com/jbot/sponsors/badge.svg)](#sponsors) [![MIT license](https://img.shields.io/badge/license-GPL_3.0-yellow.svg)](https://raw.githubusercontent.com/ramswaroop/jbot/master/LICENSE)

Make bots in Java.

__JBot__ is a java framework _(inspired by [Howdyai's Botkit](https://github.com/howdyai/botkit))_ to
make Slack and Facebook bots in minutes. It provides all the boilerplate code needed so that you
can make your bot live right away.

## Why use JBot?

* Provides you with __all the boilerplate code__ which handles underlying websocket connections and other complexities.
* Supports __extra events__ in addition to all the events supported by Slack/Facebook which makes your work a lot more easier.
* __Receiving & sending messages__ is as easy as defining a `@Controller` and calling `reply()`.
* __Conversation feature__ of JBot makes talking to your bot a breeze. This feature makes JBot different than rest of the Java frameworks out there.
* __Well tested__ with good coverage unit tests.
* And many other features which can't just be mentioned here.

**Not satisfied?** Read on...

* JBot got more than __400 stars__ in just 2 days after release.
* It is in the [Hacker News](https://news.ycombinator.com/item?id=12239667) 50 club.
* Chosen by [DZone daily picks](http://mailer.dzone.com/display.php?M=15184241&C=dcebb6887365120539df1fbf19a071ed&S=9043&L=658&N=4604).
* Last but not the least, it's listed on [Slack.com](https://api.slack.com/community)

**Still worried?** Open an [issue on Github](https://github.com/rampatra/jbot/issues) and we can discuss.

## JBot for Slack

**Running your SlackBot is just 4 easy steps:**
  
1. Clone this project `$ git clone https://github.com/rampatra/jbot.git`.  
2. [Create a slack bot](https://my.slack.com/services/new/bot) and get your slack token.  
3. Paste the token in [application.properties](/jbot-example/src/main/resources/application.properties) file.  
4. Run the example application by running `JBotApplication` in your IDE or via commandline: 
    ```bash
    $ cd jbot
    $ mvn clean install
    $ cd jbot-example
    $ mvn spring-boot:run
    ```

You can now start talking with your bot ;)

Read the detailed [Slack documentation](https://blog.rampatra.com/how-to-make-a-slack-bot-in-java) to learn more.

## JBot for Facebook

**Similar to Slack, Facebook is simple too but has a few extra steps:**

1. Clone this project `$ git clone git@github.com:rampatra/jbot.git`.
2. Create a [facebook app](https://developers.facebook.com/docs/apps/register#create-app) and a 
[page](https://www.facebook.com/pages/create).
3. Generate a Page Access Token for the page (inside app's messenger settings).
4. Paste the token created above in [application.properties](/jbot-example/src/main/resources/application.properties) file.
5. Run the example application by running `JBotApplication` in your IDE or via commandline: 
    ```bash
    $ cd jbot
    $ mvn clean install
    $ cd jbot-example
    $ mvn spring-boot:run
    ```
6. Setup webhook to receive messages and other events. You need to have a public address to setup webhook. You may use
[localtunnel.me](https://localtunnel.me) to generate a public address if you're running locally on your machine.
7. Specify the address created above in "Callback Url" field under "Webooks" setting and give the verify token 
as `fb_token_for_jbot` and click "Verify and Save".

You can now start messaging your bot by going to the facebook page and clicking on the "Send message" button. 

~_If you're too lazy to start now and just want to play around, you can try `jbot-example` by visiting 
[JBot facebook page](https://www.facebook.com/jbotframework/) and clicking on the "Send Message" button._~ -- The server is currently down

Read the detailed [Facebook documentation](https://blog.rampatra.com/how-to-make-facebook-bots-in-java) to learn more.

## Contributors

This project exists thanks to all the awesome people who contribute. If you want to flaunt your skills and make 
the project better, [you can start contributing](/CONTRIBUTING.md), and for any queries please just 
[raise an issue](https://github.com/rampatra/jbot/issues) and I would be more than happy to discuss :)

<a href="graphs/contributors"><img src="https://opencollective.com/jbot/contributors.svg?width=890" /></a>

## Sponsors

Support this project by becoming a sponsor. Your logo will show up here with a link to your website. [[Become a sponsor](https://x.com/rampatra_)]

<table border="0">
  <tr>
    <td>
      <a href="https://presentifyapp.com/" target="_blank"><img src="https://presentifyapp.com/assets/img/presentify/app-icon-512.png" width="150" height="150"></a>
    </td>
    <td>
      <a href="https://facescreenapp.com/" target="_blank"><img src="https://github.com/user-attachments/assets/b251b413-ccc4-48f1-a316-c2c2a71f959e" width="150" height="150"></a>
    </td>
    <td>
        <a href="https://todobarapp.com/" target="_blank"><img src="https://todobarapp.com/assets/img/todobar/app-icon-512.png" width="150" height="150"></a>
    </td>
    <td>
      <a href="https://simplefillapp.com/" target="_blank"><img src="https://github.com/user-attachments/assets/6c575d9c-b65b-4ce7-a468-30f74cfedf18" width="150" height="150"></a>
    </td>
  </tr>
 </table>

---

_P.S. For any queries or concerns, you can reach out to me on [Twitter](https://twitter.com/rampatra_). I'll try my best to help 🙏._
