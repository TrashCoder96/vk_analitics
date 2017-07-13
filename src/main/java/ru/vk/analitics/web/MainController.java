package ru.vk.analitics.web;

import com.vk.api.sdk.streaming.objects.StreamingRule;
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
import ru.vk.analitics.service.DataService;

import java.util.concurrent.ExecutionException;

/**
 * Created by itimofeev on 10.07.2017.
 */

@RestController
public class MainController {

    @Autowired
    private VkStreamingApiClient vkStreamingApiClient;

    @Autowired
    private StreamingActor streamingActor;

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private DataService dataService;

    @RequestMapping("/word")
    public void v(String word) throws StreamingApiException, StreamingClientException, ExecutionException, InterruptedException {
       dataService.run(word);
    }

}