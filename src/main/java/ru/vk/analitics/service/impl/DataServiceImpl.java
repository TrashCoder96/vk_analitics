package ru.vk.analitics.service.impl;

import com.vk.api.sdk.streaming.clients.StreamingEventHandler;
import com.vk.api.sdk.streaming.clients.VkStreamingApiClient;
import com.vk.api.sdk.streaming.clients.actors.StreamingActor;
import com.vk.api.sdk.streaming.exceptions.StreamingApiException;
import com.vk.api.sdk.streaming.exceptions.StreamingClientException;
import com.vk.api.sdk.streaming.objects.StreamingCallbackMessage;
import com.vk.api.sdk.streaming.objects.StreamingRule;
import com.vk.api.sdk.streaming.objects.responses.StreamingGetRulesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vk.analitics.data.Data;
import ru.vk.analitics.data.dao.DataRepository;
import ru.vk.analitics.service.DataService;

import java.util.concurrent.ExecutionException;

/**
 * Created by ivan on 12.07.17.
 */

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private VkStreamingApiClient vkStreamingApiClient;

    @Autowired
    private StreamingActor streamingActor;

    @Autowired
    private DataRepository dataRepository;

    @Override
    public void run(String value) {
        String tag = "3";
        try {
            StreamingGetRulesResponse response = vkStreamingApiClient.rules().get(streamingActor).execute();
            if (response.getRules() != null && response.getRules().size() > 0) {
                for (StreamingRule rule: response.getRules()) {
                    vkStreamingApiClient.rules().delete(streamingActor, rule.getTag()).execute();
                }
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
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

}
