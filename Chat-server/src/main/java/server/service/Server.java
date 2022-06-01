package server.service;

import Client.ChatController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static server.service.constants.MessageConstants.REGEX;
import static server.service.enums.Command.*;

public class Server {

    private static final int PORT = 8189;
    private List<Handler> handlers;
    private UserService userService;

    public Server(UserService userService) {
        this.userService = userService;
        this.handlers = new ArrayList<>();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server start!");
            userService.start();
            while (true) {
                System.out.println("Waiting for connection......");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                Handler handler = new Handler(socket, this);
                handler.handle();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            shutdown();
        }
    }

    public void broadcast(String from, String message) {
        String msg = BROADCAST_MESSAGE.getCommand() + REGEX + String.format("[%s]: %s", from, message);
        for (Handler handler : handlers) {
            handler.send(msg);
        }
    }

    public void privetMsg(String from, String massage, String recipient) {
        String msg = PRIVATE_MESSAGE.getCommand() + REGEX + String.format("[%s]: %s", from, massage);
        for (Handler handler : handlers) {
            if (handler.getUser().equals(recipient) || handler.getUser().equals(from)) handler.send(msg);
        }
    }


    public UserService getUserService() {
        return userService;
    }

    public synchronized boolean isUserAlreadyOnline(String nick) {
        for (Handler handler : handlers) {
            if (handler.getUser().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void addHandler(Handler handler) {
        this.handlers.add(handler);
        sendContacts();
    }

    public synchronized void removeHandler(Handler handler) {
        this.handlers.remove(handler);
        sendContacts();
    }

    private void shutdown() {
        userService.stop();
    }

    private void sendContacts() {
        String contacts = handlers.stream()
                .map(Handler::getUser)
                .collect(Collectors.joining(REGEX));
        String msg = LIST_USERS.getCommand() + REGEX + contacts;

        for (Handler handler : handlers) {
            handler.send(msg);
        }
    }
}
