package me.ramswaroop.botkit.slackbot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ramswaroop
 * @version 20/06/2016
 */
@Controller
public class SlackSlashCommand {

    @RequestMapping("/slash-command")
    public void onReceiveSlashCommand(HttpServletRequest request, HttpServletResponse response) {
        System.out.print("");
    }
}
