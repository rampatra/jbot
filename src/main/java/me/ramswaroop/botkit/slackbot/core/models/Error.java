package me.ramswaroop.botkit.slackbot.core.models;

/**
 * Created by ramswaroop on 19/06/2016.
 */
public class Error {
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
