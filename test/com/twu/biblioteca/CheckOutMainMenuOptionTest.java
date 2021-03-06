package com.twu.biblioteca;

import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CheckOutMainMenuOptionTest {

    @Test
    public void shouldTakeInputFromUserOnWhatIsToBeCheckedOutUsingConsoleInput() {
        User currentUser = new User("GUEST USER", "NO PASSWORD", ROLE.GUEST_USER, "NO NAME", "NO EMAIL ADDRESS", 0);
        BookLibrary bookLibrary = mock(BookLibrary.class);
        ConsoleInput consoleInput = mock(ConsoleInput.class);
        ConsoleOutput consoleOutput = mock(ConsoleOutput.class);
        CheckOutMenuOption checkOutMenuOption = new CheckOutMenuOption(bookLibrary, consoleInput, consoleOutput, currentUser);
        checkOutMenuOption.executeOptionOperation();

        verify(consoleInput, times(1)).getInput();
    }

    @Test
    public void shouldDisplayAPromptMessageForUserToInputWhatIsToBeCheckedOutAndTheCheckOutMessageAsWell() {
        ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));

        BookLibrary bookLibrary = mock(BookLibrary.class);
        User currentUser = new User("GUEST USER", "NO PASSWORD", ROLE.GUEST_USER, "NO NAME", "NO EMAIL ADDRESS", 0);
        when(bookLibrary.checkOut("Gone Girl", currentUser)).thenReturn("Thank you! Enjoy the book");
        ConsoleInput consoleInput = mock(ConsoleInput.class);
        when(consoleInput.getInput()).thenReturn("Gone Girl");
        ConsoleOutput consoleOutput = new ConsoleOutput();
        CheckOutMenuOption checkOutMenuOption = new CheckOutMenuOption(bookLibrary, consoleInput, consoleOutput, currentUser);
        checkOutMenuOption.executeOptionOperation();

        assertEquals("Enter what is to be checked out:\nThank you! Enjoy the book\n", outputContent.toString());
    }

    @Test
    public void shouldUseConsoleOutputToPrintPromptMessageToTheConsoleAndPrintCheckOutMessage() {
        BookLibrary bookLibrary = mock(BookLibrary.class);
        User currentUser = new User("lib-1000", "password", ROLE.LIBRARIAN, "Madam Pince", "librarian@hogwarts.com", 968684524);
        when(bookLibrary.checkOut("Gone Girl", currentUser)).thenReturn("Thank you! Enjoy the book");
        ConsoleInput consoleInput = mock(ConsoleInput.class);
        when(consoleInput.getInput()).thenReturn("Gone Girl");
        ConsoleOutput consoleOutput = mock(ConsoleOutput.class);
        CheckOutMenuOption checkOutMenuOption = new CheckOutMenuOption(bookLibrary, consoleInput, consoleOutput, currentUser);
        checkOutMenuOption.executeOptionOperation();

        verify(consoleOutput, times(1)).display("Enter what is to be checked out:");
        verify(consoleOutput, times(1)).display("Thank you! Enjoy the book");
    }

    @Test
    public void shouldPrintUnsuccessfulReturnMessageIfCheckOutWasUnsuccessful() {
        ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));

        BookLibrary bookLibrary = mock(BookLibrary.class);
        User currentUser = new User("lib-1000", "password", ROLE.LIBRARIAN, "Madam Pince", "librarian@hogwarts.com", 968684524);
        when(bookLibrary.checkOut("Goner Girl", currentUser)).thenReturn("This book is not available");
        ConsoleInput consoleInput = mock(ConsoleInput.class);
        when(consoleInput.getInput()).thenReturn("Goner Girl");
        ConsoleOutput consoleOutput = new ConsoleOutput();
        CheckOutMenuOption checkOutMenuOption = new CheckOutMenuOption(bookLibrary, consoleInput, consoleOutput, currentUser);
        checkOutMenuOption.executeOptionOperation();

        assertEquals("Enter what is to be checked out:\nThis book is not available\n", outputContent.toString());
    }

    @Test
    public void shouldPrintSuccessfulReturnMessageIfMovieCheckOutWasSuccessful() {
        ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
        User currentUser = new User("lib-1000", "password", ROLE.LIBRARIAN, "Madam Pince", "librarian@hogwarts.com", 968684524);
        MovieLibrary movieLibrary = mock(MovieLibrary.class);
        when(movieLibrary.checkOut("Funny Girl", currentUser)).thenReturn("Thank you! Enjoy the movie");
        ConsoleInput consoleInput = mock(ConsoleInput.class);
        when(consoleInput.getInput()).thenReturn("Funny Girl");
        ConsoleOutput consoleOutput = new ConsoleOutput();
        CheckOutMenuOption checkOutMenuOption = new CheckOutMenuOption(movieLibrary, consoleInput, consoleOutput, currentUser);
        checkOutMenuOption.executeOptionOperation();

        assertEquals("Enter what is to be checked out:\nThank you! Enjoy the movie\n", outputContent.toString());
    }
}