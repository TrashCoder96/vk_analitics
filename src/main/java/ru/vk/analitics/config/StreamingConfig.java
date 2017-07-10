package ru.vk.analitics.config;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.ServiceClientCredentialsFlowResponse;
import com.vk.api.sdk.objects.streaming.responses.GetServerUrlResponse;
import com.vk.api.sdk.streaming.clients.VkStreamingApiClient;
import com.vk.api.sdk.streaming.clients.actors.StreamingActor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by itimofeev on 10.07.2017.
 */

@Configuration
public class StreamingConfig {

	@Value("${vk.secret}")
	private String secret;

	@Value("${vk.appId}")
	private Integer appId;

	@Bean
	public TransportClient transportClient() {
		return new HttpTransportClient();
	}

	@Bean
	public VkStreamingApiClient vkStreamingClient(TransportClient transportClient) {
		return new VkStreamingApiClient(transportClient);
	}

	@Bean
	public VkApiClient vk(TransportClient transportClient) {
		return new VkApiClient(transportClient);
	}

	@Bean
	public StreamingActor streamingActor(VkApiClient vk) throws ClientException, ApiException {
		ServiceClientCredentialsFlowResponse authResponse = vk.oauth()
				.serviceClientCredentialsFlow(appId, secret)
				.execute();
		ServiceActor actor = new ServiceActor(appId, secret, authResponse.getAccessToken());
		GetServerUrlResponse getServerUrlResponse = vk.streaming().getServerUrl(actor).execute();
		return new StreamingActor(getServerUrlResponse.getEndpoint(), getServerUrlResponse.getKey());
	}

}
