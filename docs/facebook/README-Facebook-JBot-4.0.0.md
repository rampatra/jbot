## Facebook Bot

### Table of Contents
1. [Getting started](#getting-started)
2. [Basic Usage](#basic-usage)
3. [Building a Slack Integration with JBot](#building-a-slack-integration-with-jbot)
    * [Setting up your app](#setting-up-your-app)
    * [Receiving messages](#receiving-messages)
    * [Sending messages](#sending-messages)
    * [Conversations](#conversations)
    * [Usage](#usage)
    * [Deploy to the cloud](#deploy-to-the-cloud)
4. [Documentation History](#documentation-history)

### Getting started

**Similar to Slack, Facebook is simple too but has few extra steps:**

1. Clone this project `$ git clone git@github.com:ramswaroop/jbot.git`.
2. Create a [facebook app](https://developers.facebook.com/docs/apps/register#create-app) and a 
[page](https://www.facebook.com/pages/create).
3. Generate a Page Access Token for the page (inside app's messenger settings).
    [![generate_fb_token](../../extras/fb_generate_token_640.gif)](../../extras/fb_generate_token.gif)
4. Paste the token created above in [application.properties](/jbot-example/src/main/resources/application.properties) file.
5. Run the example application by running `JBotApplication` in your IDE or via commandline: 
    ```bash
    $ cd jbot-example
    $ mvn spring-boot:run
    ```
6. Setup webhook to receive messages and other events. 
    [![setup_webhook](../../extras/fb_setup_webhook_640.gif)](../../extras/fb_setup_webhook.gif)

    You need to have a public address to setup webhook. You may use [localtunnel.me](https://localtunnel.me) to generate a
public address if you're running locally on your machine.
    ![localtunnel_demo](../../extras/localtunnel_demo.gif)
7. Specify the address created above in "Callback Url" field under "Webooks" setting and give the verify token 
as `fb_token_for_jbot` and click "Verify and Save".

You can now start messaging your bot by going to the facebook page and clicking on the "Send message" button.


### Basic Usage

