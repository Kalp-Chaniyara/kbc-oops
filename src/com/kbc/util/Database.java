package com.kbc.util;

import com.kbc.model.User;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private static final Map<String, User> users = new HashMap<>();

    public static boolean registerUser(String username, String password, String email) {
        if (users.containsKey(username)) {
            return false;
        }
        users.put(username, new User(username, password, email));
        return true;
    }

    public static User authenticateUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public static boolean userExists(String username) {
        return users.containsKey(username);
    }
} 