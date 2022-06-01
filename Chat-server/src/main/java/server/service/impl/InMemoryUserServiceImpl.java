package server.service.impl;

import server.service.UserService;
import server.service.error.WrongCredentialsException;
import server.service.model.User;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUserServiceImpl implements UserService {

    private List<User> users;

    public InMemoryUserServiceImpl() {
        this.users = new ArrayList<>();
    }

    @Override
    public void start() {
        users.addAll(List.of(
                new User("log1", "pass1", "nick1"),
                new User("log2", "pass2", "nick2"),
                new User("log3", "pass3", "nick3"),
                new User("log4", "pass4", "nick4"),
                new User("log5", "pass5", "nick5")
        ));
        System.out.println("User service started");
    }

    @Override
    public void stop() {
        System.out.println("User service stopped");
    }

    @Override
    public String authenticate(String login, String password) {
        System.out.println("Auth log: " + login + " pass: " + password);
        for (User user : users) {
            if (login.equals(user.getLogin()) && password.equals(user.getPassword())) {
//            if (Objects.equals(login, user.getLogin()) && Objects.equals(password, user.getPassword())) {
                return user.getNick();
            }
        }

        throw new WrongCredentialsException("Wrong login or password");
    }

    @Override
    public String changeNick(String login, String newNick) {
        return null; //@TODO
    }

    @Override
    public User createUser(String login, String password, String nick) {
        return null; //TODO
    }

    @Override
    public void deleteUser(String login, String password) {
        //@TODO
    }

    @Override
    public void changePassword(String login, String oldPassword) {

    }

    public void changePassword(String login, String oldPassword, String newPassword) {
        //@TODO
    }
}