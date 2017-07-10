package ru.vk.analitics;

import com.vk.api.sdk.streaming.clients.StreamingEventHandler;
import com.vk.api.sdk.streaming.clients.VkStreamingApiClient;
import com.vk.api.sdk.streaming.clients.actors.StreamingActor;
import com.vk.api.sdk.streaming.exceptions.StreamingApiException;
import com.vk.api.sdk.streaming.exceptions.StreamingClientException;
import com.vk.api.sdk.streaming.objects.StreamingCallbackMessage;
import com.vk.api.sdk.streaming.objects.responses.StreamingGetRulesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class VkAnaliticsApplication {

	@Value("${vk.val}")
	private String val;

	@Autowired
	private VkStreamingApiClient vkStreamingApiClient;

	@Autowired
	private StreamingActor streamingActor;

	@PostConstruct
	private void post() throws StreamingApiException, StreamingClientException, ExecutionException, InterruptedException {
		String tag = "1";
		String value = val;
		StreamingGetRulesResponse response = vkStreamingApiClient.rules().get(streamingActor).execute();
		if (!(response.getRules() == null)) {
			vkStreamingApiClient.rules().delete(streamingActor, tag).execute();
		}
		vkStreamingApiClient.rules().add(streamingActor, tag, value).execute();
		vkStreamingApiClient.stream().get(streamingActor, new StreamingEventHandler() {

			@Override
			public void handle(StreamingCallbackMessage message) {
				System.out.println(message);
			}

		}).execute();
	}

	public static void main(String[] args) {
		SpringApplication.run(VkAnaliticsApplication.class, args);
	}
}
