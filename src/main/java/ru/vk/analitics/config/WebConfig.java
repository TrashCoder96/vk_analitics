package ru.vk.analitics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vk.analitics.component.StreamComponent;
import ru.vk.analitics.handler.StreamHandler;
import ru.vk.analitics.service.DataService;
import ru.vk.analitics.service.impl.DataServiceImpl;

/**
 * Created by ivan on 12.07.17.
 */

@Configuration
public class WebConfig {

    @Bean
    public DataService dataService() {
        return new DataServiceImpl();
    }

    @Bean
    public StreamComponent streamComponent() {
        return new StreamComponent();
    }

    @Bean
    public StreamHandler streamHandler() {
        return new StreamHandler();
    }

}
