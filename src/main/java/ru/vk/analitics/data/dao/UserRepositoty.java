package ru.vk.analitics.data.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.vk.analitics.data.User;

/**
 * Created by ivan on 12.07.17.
 */

@Repository
public interface UserRepositoty extends MongoRepository<User, String> {

    User findByLogin(String login);

}
