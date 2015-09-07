/*Biblioteca Application displays Welcome Message and Main Menu*/
package com.twu.biblioteca;

import java.util.ArrayList;
import java.util.Arrays;

public class BibliotecaApplication {
    BookList bookList = new BookList();

    public void start() {
        displayWelcomeMessage("Hello! Welcome to Bangalore Public Library!");
        ArrayList<String> menuOptions = new ArrayList<>(Arrays.asList("1. List Books", "2. Quit"));
        MainMenu mainMenu = new MainMenu(menuOptions);
        mainMenu.display();
        mainMenu.dispatch(bookList);
    }

    private void displayWelcomeMessage(String welcomeMessage) {
        System.out.println(welcomeMessage);
    }

    public static void main(String[] args) {
        BibliotecaApplication bibliotecaApplication = new BibliotecaApplication();
        bibliotecaApplication.start();
    }
}