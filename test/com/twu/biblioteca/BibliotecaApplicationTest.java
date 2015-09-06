package com.twu.biblioteca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class BibliotecaApplicationTest {

    private final ByteArrayOutputStream outputContent = new ByteArrayOutputStream();

    @Before
    public void setStreamsWithInitialValue() {
        System.setOut(new PrintStream(outputContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(System.out);
    }

    @Test
    public void shouldPrintAWelcomeMessageAndMenuWhenBibliotecaApplicationStarts() {
        BibliotecaApplication bibliotecaApplication = new BibliotecaApplication();
        bibliotecaApplication.start();

        assertEquals("Hello! Welcome to Bangalore Public Library!\nMAIN MENU\n1. List Books\n", outputContent.toString());
    }
}
