package com.twu.biblioteca;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LibraryTest {
    private ByteArrayOutputStream outputContent = new ByteArrayOutputStream();

    @Before
    public void setStreamsWithInitialValue() {
        System.setOut(new PrintStream(outputContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(System.out);
    }

    @Test
    public void shouldPrintListOfBooksInColumnFormat() {
        Library library = new Library();
        library.listBooks();

        assertEquals("NAME OF BOOK\tNAME OF AUTHOR\tYEAR OF PUBLICATION\nTo Kill A Mockingbird\tHarper Lee\t1968\nGone Girl\tGillian Flynn\t2000\n", outputContent.toString());
    }

    @Test
    public void shouldCheckOutTheBookSpecifiedByTheUser() {
        Library library = new Library();
        ConsoleInput consoleInput = mock(ConsoleInput.class);

        when(consoleInput.getInput()).thenReturn("Gone Girl");

        assertEquals("Thank you! Enjoy the book", library.checkOut(consoleInput));
    }

    @Test
    public void shouldNotHaveCheckedOutBookInUpdatedBookList() {
        ByteArrayInputStream inputBookName = new ByteArrayInputStream("Gone Girl".getBytes());
        System.setIn(inputBookName);
        Library library = new Library();
        ConsoleInput consoleInput = mock(ConsoleInput.class);
        when(consoleInput.getInput()).thenReturn("Gone Girl");
        library.checkOut(consoleInput);
        library.listBooks();

        assertEquals("NAME OF BOOK\tNAME OF AUTHOR\tYEAR OF PUBLICATION\nTo Kill A Mockingbird\tHarper Lee\t1968\n", outputContent.toString());
    }

    @Test
    public void shouldNotCheckOutBookSpecifiedByUserIfNotFoundInLibrary() {
        Library library = new Library();
        ConsoleInput consoleInput = mock(ConsoleInput.class);
        library.checkOut(consoleInput);
        library.listBooks();

        when(consoleInput.getInput()).thenReturn("Goner Girl");

        assertEquals("That book is not available", library.checkOut(consoleInput));
    }
}
