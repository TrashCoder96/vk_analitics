package ru.vk.analitics.web;

import ru.vk.analitics.data.Data;
import ru.vk.analitics.data.dao.DataRepository;
import com.vk.api.sdk.streaming.clients.StreamingEventHandler;
import com.vk.api.sdk.streaming.clients.VkStreamingApiClient;
import com.vk.api.sdk.streaming.clients.actors.StreamingActor;
import com.vk.api.sdk.streaming.exceptions.StreamingApiException;
import com.vk.api.sdk.streaming.exceptions.StreamingClientException;
import com.vk.api.sdk.streaming.objects.StreamingCallbackMessage;
import com.vk.api.sdk.streaming.objects.responses.StreamingGetRulesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * Created by itimofeev on 10.07.2017.
 */

@RestController
public class IntController {

	@Autowired
	private VkStreamingApiClient vkStreamingApiClient;

	@Autowired
	private StreamingActor streamingActor;

	@Autowired
	private DataRepository dataRepository;

	@RequestMapping("/word")
	public void v(String word) throws StreamingApiException, StreamingClientException, ExecutionException, InterruptedException {
		String tag = "3";
		String value = word;
		StreamingGetRulesResponse response = vkStreamingApiClient.rules().get(streamingActor).execute();
		if (!(response.getRules() == null)) {
			vkStreamingApiClient.rules().delete(streamingActor, tag).execute();
		}
		vkStreamingApiClient.rules().add(streamingActor, tag, value).execute();
		vkStreamingApiClient.stream().get(streamingActor, new StreamingEventHandler() {
			@Override
			public void handle(StreamingCallbackMessage message) {
				Data data = new Data();
				data.setId(String.valueOf(message.getEvent().getCreationTime().hashCode()));
				data.setLink(message.getEvent().getEventUrl());
				data.setType(message.getEvent().getEventType().getValue());
				dataRepository.save(data);
				System.out.println(message);
			}
		}).execute();
	}

}
