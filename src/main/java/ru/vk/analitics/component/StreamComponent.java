package ru.vk.analitics.component;

import com.vk.api.sdk.streaming.clients.VkStreamingApiClient;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.AsyncHttpClientConfig;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import ru.vk.analitics.handler.StreamHandler;
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
		AsyncHttpClientConfig config = new DefaultAsyncHttpClientConfig.Builder().setWebSocketMaxFrameSize(1048576).build();
		AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient(config);
		streamingClient.setAsyncHttpClient(asyncHttpClient);
		StreamingGetRulesResponse response = streamingClient.rules().get(actor).execute();
		if (response.getRules() != null) {
			for (StreamingRule rule : response.getRules()) {
				streamingClient.rules().delete(actor, rule.getTag()).execute();
			}
		}
		streamingClient.rules().add(actor, "23", "путин").execute();
		streamingClient.rules().add(actor, "13", "трамп").execute();
		streamingClient.stream().get(actor, streamHandler).execute();
	}

}
