package me.ramswaroop.jbot.core.slack.models.users;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.ramswaroop.jbot.core.slack.models.User;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersListResponse {

	private Boolean ok;
	private String error;
	private List<User> members;

}
