package ru.vk.analitics.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by itimofeev on 10.07.2017.
 */

@Configuration
@EnableMongoRepositories("ru.vk.analitics.data")
public class MongoConfig extends AbstractMongoConfiguration {

	@Value("${spring.mongodb.host}")
	private String mongoHost;

	@Value("${spring.mongodb.port}")
	private String mongoPort;

	@Value("${spring.mongodb.database}")
	private String mongoDB;

	@Value("${spring.mongodb.login}")
	private String mongoDBLogin;

	@Value("${spring.mongodb.password}")
	private String mongoDBPassword;

	@Override
	protected String getDatabaseName() {
		return mongoDB;
	}

	@Override
	public Mongo mongo() throws Exception {
		return new MongoClient(mongoHost + ":" + mongoPort);
	}

}
