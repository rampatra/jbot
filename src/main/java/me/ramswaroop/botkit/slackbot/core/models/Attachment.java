package me.ramswaroop.botkit.slackbot.core.models;

/**
 * Created by ramswaroop on 12/06/2016.
 */
public class Attachment {
    private String fallback;
    private String color;
    private String pretext;
    private String authorName;
    private String authorLink;
    private String authorIcon;
    private String title;
    private String titleLink;
    private String text;
    private Field[] fields;
    private String imageUrl;
    private String thumbUrl;
    private String footer;
    private String footerIcon;
    private String ts;
}

class Field {
    private String title;
    private String value;
    private boolean shortEnough;
}
