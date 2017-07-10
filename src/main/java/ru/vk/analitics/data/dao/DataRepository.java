package ru.vk.analitics.data.dao;

import ru.vk.analitics.data.Data;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by itimofeev on 10.07.2017.
 */

@Repository
public interface DataRepository extends MongoRepository<Data, String> {
}
