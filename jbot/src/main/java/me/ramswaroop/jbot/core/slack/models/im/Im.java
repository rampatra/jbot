package me.ramswaroop.jbot.core.slack.models.im;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Im {

	private String id;
	private String created;
	private Boolean is_im;
	private Boolean is_org_shared;
	private String user;
	private Boolean is_user_deleted;
}
