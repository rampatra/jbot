package me.ramswaroop.jbot.core.slack.models.ping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.ramswaroop.jbot.core.slack.models.ping.Ping.PingBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pong {

	private Integer id;
	private String type;
	private Long timestamp;
}
