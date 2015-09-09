package com.twu.biblioteca;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class ControllerTest {

    @Test
    public void shouldInitialiseWelcomeMessageAndDisplayIt() {
        ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));

        Controller controller = new Controller();
        controller.displayWelcomeMessage();

        assertEquals("Hello! Welcome to Bangalore Public Library!\n", outputContent.toString());
    }

    @Test
    public void shouldInitialiseMenuAndReturnIt() {
        ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));

        Controller controller = new Controller();
        MainMenu mainMenu = controller.initialiseMainMenuWithOptions();
        mainMenu.display();

        assertEquals("MAIN MENU\n1. List Books\n2. Check Out\nQuit\n", outputContent.toString());
    }
}
