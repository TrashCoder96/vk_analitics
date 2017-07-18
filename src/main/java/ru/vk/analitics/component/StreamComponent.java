package ru.vk.analitics.component;

import ru.vk.analitics.handler.StreamHandler;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.objects.streaming.responses.GetServerUrlResponse;
import com.vk.api.sdk.streaming.clients.VkStreamingApiClient;
import com.vk.api.sdk.streaming.clients.actors.StreamingActor;
import com.vk.api.sdk.streaming.exceptions.StreamingApiException;
import com.vk.api.sdk.streaming.exceptions.StreamingClientException;
import com.vk.api.sdk.streaming.objects.StreamingRule;
import com.vk.api.sdk.streaming.objects.responses.StreamingGetRulesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;

/**
 * Created by itimofeev on 18.07.2017.
 */

@Component
public class StreamComponent {

	@Autowired
	private StreamingActor actor;

	@Autowired
	private VkStreamingApiClient streamingClient;

	@Autowired
	private StreamHandler streamHandler;

	@PostConstruct
	public void post() throws ExecutionException, InterruptedException, StreamingApiException, StreamingClientException {
		StreamingGetRulesResponse response = streamingClient.rules().get(actor).execute();
		if (response.getRules() != null) {
			for (StreamingRule rule : response.getRules()) {
				streamingClient.rules().delete(actor, rule.getTag()).execute();
			}
		}
		streamingClient.rules().add(actor, "1", "путин").execute();
		streamingClient.stream().get(actor, streamHandler).execute();
	}

}
