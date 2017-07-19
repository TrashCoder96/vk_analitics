package ru.vk.analitics.handler;

import ru.vk.analitics.data.Data;
import ru.vk.analitics.data.dao.DataRepository;
import com.vk.api.sdk.streaming.clients.StreamingEventHandler;
import com.vk.api.sdk.streaming.objects.StreamingCallbackMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Created by itimofeev on 18.07.2017.
 */

@Component
public class StreamHandler extends StreamingEventHandler {

	@Autowired
	private DataRepository dataRepository;

	@Override
	@Transactional
	public void handle(StreamingCallbackMessage message) {
		Data data = new Data();
		data.setId(UUID.randomUUID().toString());
		data.setLink(message.getEvent().getEventUrl());
		data.setText(message.getEvent().getText());
		data.setType(message.getEvent().getEventType().getValue());
		data.getTags().addAll(message.getEvent().getTags());
		dataRepository.save(data);
	}
}
