/*List Library displays the bookList in Book Library or movieList in Movie Library*/
package com.twu.biblioteca;

public class ListLibraryMenuOption implements MainMenuOption {
    private Library library;
    private ConsoleOutput consoleOutput;

    public ListLibraryMenuOption(Library library, ConsoleOutput consoleOutput) {
        this.library = library;
        this.consoleOutput = consoleOutput;
    }

    @Override
    public void executeOptionOperation() {
        consoleOutput.display(list());
    }

    private String list() {
        return library.list();
    }
}
