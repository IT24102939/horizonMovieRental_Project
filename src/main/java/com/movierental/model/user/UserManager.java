package com.movierental.model.user;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * UserManager class handles all user-related operations
 */
public class UserManager {
    private static final String USER_FILE_NAME = "users.txt";
    private List<User> users;
    private ServletContext servletContext;
    private String dataFilePath;


    public UserManager() {
        this(null);
    }


    public UserManager(ServletContext servletContext) {
        this.servletContext = servletContext;
        users = new ArrayList<>();
        initializeFilePath();
        loadUsers();
    }


    private void initializeFilePath() {
        if (servletContext != null) {

            String webInfDataPath = "/WEB-INF/data";
            dataFilePath = servletContext.getRealPath(webInfDataPath) + File.separator + USER_FILE_NAME;


            File dataDir = new File(servletContext.getRealPath(webInfDataPath));
            if (!dataDir.exists()) {
                boolean created = dataDir.mkdirs();
                System.out.println("Created WEB-INF/data directory: " + dataDir.getAbsolutePath() + " - Success: " + created);
            }
        } else {

            String dataPath = "data";
            dataFilePath = dataPath + File.separator + USER_FILE_NAME;


            File dataDir = new File(dataPath);
            if (!dataDir.exists()) {
                boolean created = dataDir.mkdirs();
                System.out.println("Created fallback data directory: " + dataPath + " - Success: " + created);
            }
        }

        System.out.println("UserManager: Using data file path: " + dataFilePath);
    }


    private void loadUsers() {
        File file = new File(dataFilePath);


        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                boolean created = file.createNewFile();
                System.out.println("Created users file: " + dataFilePath + " - Success: " + created);
            } catch (IOException e) {
                System.err.println("Error creating users file: " + e.getMessage());
                e.printStackTrace();
            }
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                User user = null;
                if (line.startsWith("REGULAR,")) {
                    user = RegularUser.fromFileString(line);
                } else if (line.startsWith("PREMIUM,")) {
                    user = PremiumUser.fromFileString(line);
                } else {
                    user = User.fromFileString(line);
                }

                if (user != null) {
                    users.add(user);
                    System.out.println("Loaded user: " + user.getUsername());
                }
            }
            System.out.println("Total users loaded: " + users.size());
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private boolean saveUsers() {
        try {

            File file = new File(dataFilePath);
            if (!file.getParentFile().exists()) {
                boolean created = file.getParentFile().mkdirs();
                System.out.println("Created directory: " + file.getParentFile().getAbsolutePath() + " - Success: " + created);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFilePath))) {
                for (User user : users) {
                    writer.write(user.toFileString());
                    writer.newLine();
                }
            }
            System.out.println("Users saved successfully to: " + dataFilePath);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    public boolean addUser(User user) {
        try {
            // Check if username already exists
            System.out.println("Adding new user: " + user.getUsername());

            if (getUserByUsername(user.getUsername()) != null) {
                System.out.println("Username already exists: " + user.getUsername());
                return false;
            }


            if (user.getUserId() == null || user.getUserId().isEmpty()) {
                user.setUserId(UUID.randomUUID().toString());
            }

            users.add(user);
            boolean saved = saveUsers();
            System.out.println("User saved successfully: " + saved);
            return saved;
        } catch (Exception e) {
            System.err.println("Exception occurred when adding user:");
            e.printStackTrace();
            return false;
        }
    }


    public User getUserById(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }


    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }


    public boolean updateUser(User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(updatedUser.getUserId())) {
                users.set(i, updatedUser);
                return saveUsers();
            }
        }
        return false;
    }


    public boolean deleteUser(String userId) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(userId)) {
                users.remove(i);
                return saveUsers();
            }
        }
        return false;
    }


    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }


    public User authenticateUser(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null && user.authenticate(password)) {
            return user;
        }
        return null;
    }

    // Upgrade regular user to premium
    public boolean upgradeToPremium(String userId) {
        User user = getUserById(userId);
        if (user != null && user instanceof RegularUser) {
            PremiumUser premiumUser = new PremiumUser(
                    user.getUserId(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getFullName()
            );

            return updateUser(premiumUser);
        }
        return false;
    }

    // Set ServletContext (can be used to update the context after initialization)
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
        initializeFilePath();
        // Reload users with the new file path
        users.clear();
        loadUsers();
    }
}