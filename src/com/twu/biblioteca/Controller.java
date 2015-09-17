/*Controls flow of Biblioteca*/
package com.twu.biblioteca;

import java.util.ArrayList;

public class Controller {

    private ArrayList<User> listOfUsers;
    private ConsoleInput consoleInput;
    private ConsoleOutput consoleOutput;
    private WelcomeMessage welcomeMessage;
    private MainMenu mainMenu;
    private BookLibrary bookLibrary;
    private MovieLibrary movieLibrary;

    public Controller(ArrayList<User> listOfUsers, ConsoleInputAndOutputFactory consoleInputAndOutputFactory, BookLibraryFactory bookLibraryFactory, MainMenuFactory mainMenuFactory,
                      MovieLibraryFactory movieLibraryFactory, WelcomeMessageFactory welcomeMessageFactory) {
        this.listOfUsers = listOfUsers;
        consoleInput = consoleInputAndOutputFactory.createConsoleInput();
        consoleOutput = consoleInputAndOutputFactory.createConsoleOutput();
        welcomeMessage = welcomeMessageFactory.createWelcomeMessage();
        mainMenu = mainMenuFactory.createMainMenu();
        bookLibrary = bookLibraryFactory.createBookLibrary();
        movieLibrary = movieLibraryFactory.createMovieLibrary();
    }

    public void initialiseBibliotecaApplication() {
        displayWelcomeMessage();

        for (; ; ) {
            displayMenuOptions();
            dispatchMenuOption(menuOptionsInput());
        }
    }

    public void displayWelcomeMessage() {
        consoleOutput.display(welcomeMessage.getWelcomeMessage());
    }

    public void displayMenuOptions() {
        consoleOutput.display(mainMenu.mainMenuOptions());
    }

    protected String menuOptionsInput() {
        return consoleInput.getInput();
    }

    public void dispatchMenuOption(String menuOption) {
        if (menuOption.equals("2")) {
            ListLibraryMenuOption listLibraryMenuOption = new ListLibraryMenuOption(bookLibrary, consoleOutput);
            listLibraryMenuOption.executeOptionOperation();
        } else if (menuOption.equals("3")) {
            CheckOutMenuOption checkOutMenuOption = new CheckOutMenuOption(bookLibrary, consoleInput, consoleOutput);
            checkOutMenuOption.executeOptionOperation();
        } else if (menuOption.equals("4")) {
            ReturnBookMenuOption returnBookMenuOption = new ReturnBookMenuOption(bookLibrary, consoleInput, consoleOutput);
            returnBookMenuOption.executeOptionOperation();
        } else if (menuOption.equals("5")) {
            ListLibraryMenuOption listLibraryMenuOption = new ListLibraryMenuOption(movieLibrary, consoleOutput);
            listLibraryMenuOption.executeOptionOperation();
        } else if (menuOption.equals("6")) {
            CheckOutMenuOption checkOutMenuOption = new CheckOutMenuOption(movieLibrary, consoleInput, consoleOutput);
            checkOutMenuOption.executeOptionOperation();
        } else if (menuOption.equals("9")) {
            QuitMenuOption quitMenuOption = new QuitMenuOption();
            quitMenuOption.executeOptionOperation();
        } else {
            InvalidMenuOption invalidMenuOption = new InvalidMenuOption(consoleOutput);
            invalidMenuOption.executeOptionOperation();
        }
    }
}