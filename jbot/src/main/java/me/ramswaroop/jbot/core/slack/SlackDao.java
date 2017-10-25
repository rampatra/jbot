package me.ramswaroop.jbot.core.slack;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import me.ramswaroop.jbot.core.slack.models.RTM;
import me.ramswaroop.jbot.core.slack.models.User;
import me.ramswaroop.jbot.core.slack.models.im.ImListResponse;
import me.ramswaroop.jbot.core.slack.models.rtm.RtmConnectResponse;
import me.ramswaroop.jbot.core.slack.models.users.UsersListResponse;

/**
 * @author ramswaroop
 * @version 14/08/2016
 */
@Repository
public class SlackDao {

    private static final Logger logger = LoggerFactory.getLogger(SlackDao.class);
    /**
     * Endpoint for RTM.start()
     */
    @Value("${rtmUrl}")
    private String rtmUrl;
    /**
     * RTM object constructed from <a href="https://api.slack.com/methods/rtm.start">RTM.start()</a>.
     */
    private RTM rtm = new RTM();
    /**
     * Rest template to make http calls.
     */
    private RestTemplate restTemplate = new RestTemplate();

    public RTM startRTM(String slackToken) {

        RtmConnectResponse rtmConnectResponse = callRtmConnect(slackToken);
        ImListResponse imListResponse = callImList(slackToken);
        UsersListResponse usersListResponse =  callUsersList(slackToken);
        
    	List<String> dmChannels = imListResponse.getIms().stream().map(im -> im.getId()).collect(Collectors.toList());
    	List<User> users = usersListResponse.getMembers();
    	
    	// set up the RTM parameters
        rtm.setWebSocketUrl(rtmConnectResponse.getUrl());
        User botUser = new User();
        botUser.setId(rtmConnectResponse.getSelf().getId());
        botUser.setTeamId(rtmConnectResponse.getTeam().getId());
        botUser.setName(rtmConnectResponse.getSelf().getName());
        rtm.setUser(botUser);
        rtm.setDmChannels(dmChannels);
        rtm.setUsers(users);

        return rtm;
    }

    
	private RtmConnectResponse callRtmConnect(String slackToken) {
		String url = "https://slack.com/api/rtm.connect";
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
			map.add("token", slackToken);

			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
			ResponseEntity<RtmConnectResponse> response = restTemplate.postForEntity(url, request, RtmConnectResponse.class);
			
			if (response.getBody() != null && response.getBody().getOk() == true) {
				logger.info("Successfully called {}", url);
				return response.getBody();
			} else {
				logger.error("Could not call {}, response was {}", url, response);
				return null;
			}
		} catch (Exception e) {
			logger.error("Could not call {}", url, e);
			return null;
		}
	}
	
	private ImListResponse callImList(String slackToken) {
		String url = "https://slack.com/api/im.list";
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
			map.add("token", slackToken);

			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
			ResponseEntity<ImListResponse> response = restTemplate.postForEntity(url, request, ImListResponse.class);
			
			if (response.getBody() != null && response.getBody().getOk() == true) {
				logger.info("Successfully called {}", url);
				return response.getBody();
			} else {
				logger.error("Could not call {}, response was {}", url, response);
				return null;
			}
		} catch (Exception e) {
			logger.error("Could not call {}", url, e);
			return null;
		}
	}
	
	private UsersListResponse callUsersList(String slackToken) {
		String url = "https://slack.com/api/users.list";
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
			map.add("token", slackToken);

			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
			ResponseEntity<UsersListResponse> response = restTemplate.postForEntity(url, request, UsersListResponse.class);
			
			if (response.getBody() != null && response.getBody().getOk() == true) {
				logger.info("Successfully called {}", url);
				return response.getBody();
			} else {
				logger.error("Could not call {}, response was {}", url, response);
				return null;
			}
		} catch (Exception e) {
			logger.error("Could not call {}", url, e);
			return null;
		}
	}
}
