package com.twu.biblioteca;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class LogoutMenuOptionTest {

    @Test
    public void shouldCreateNewCurrentUser() {
        ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
        ConsoleOutput consoleOutput = new ConsoleOutput();
        User currentUser = new User("GUEST USER", "NO PASSWORD", ROLE.GUEST_USER, "NO NAME", "NO EMAIL ADDRESS", 0);
        LogoutMenuOption logoutMenuOption = new LogoutMenuOption(consoleOutput, currentUser);
        logoutMenuOption.executeOptionOperation();

        assertEquals("You are logged out\n", outputContent.toString());
    }

    @Test
    public void shouldUseConsoleOutputToPrintSuccessfulLogOutMessage() {
        ConsoleOutput consoleOutput = mock(ConsoleOutput.class);
        User currentUser = new User("GUEST USER", "NO PASSWORD", ROLE.GUEST_USER, "NO NAME", "NO EMAIL ADDRESS", 0);
        LogoutMenuOption logoutMenuOption = new LogoutMenuOption(consoleOutput, currentUser);
        logoutMenuOption.executeOptionOperation();

        verify(consoleOutput, times(1)).display("You are logged out");
    }
}