/*Authenticates user name and password against list of users*/
package com.twu.biblioteca;

import java.util.ArrayList;

public class LoginMenuOption implements MainMenuOption {

    private ConsoleInput consoleInput;
    private ConsoleOutput consoleOutput;
    private ArrayList<User> listOfUsers;
    private User currentUser;

    public LoginMenuOption(ConsoleInput consoleInput, ConsoleOutput consoleOutput, ArrayList<User> listOfUsers, User currentUser) {
        this.consoleInput = consoleInput;
        this.consoleOutput = consoleOutput;
        this.listOfUsers = listOfUsers;
        this.currentUser = currentUser;
    }

    @Override
    public User executeOptionOperation() {
        currentUser = new User(username(), password(), ROLE.GUEST_USER, "NO NAME", "NO EMAIL ADDRESS", 0);
        if(isAuthenticatedUser(currentUser))
            consoleOutput.display("Login Successful");
        else
            consoleOutput.display("Login Unsuccessful");

        return currentUser;
    }

    private String username() {
        consoleOutput.display("Enter username:");
        return consoleInput.getInput();
    }

    private String password() {
        consoleOutput.display("Enter password:");
        return consoleInput.getInput();
    }

    private boolean isAuthenticatedUser(User thatUser) {
        for(User user : listOfUsers) {
            if(user.equals(thatUser)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }
}
