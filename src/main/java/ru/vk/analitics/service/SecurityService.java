package ru.vk.analitics.service;

/**
 * Created by ivan on 12.07.17.
 */
public interface SecurityService {

    void login(String login, String password);

    void logout();

}
