package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ControllerTest {

    private ByteArrayOutputStream outputContent;

    @Before
    public void setOutputContent() {
        outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
    }

    @Test
    public void shouldInitialiseWelcomeMessageAndDisplayIt() {
        User currentUser = new User("GUEST USER", "NO PASSWORD", ROLE.GUEST_USER, "NO NAME", "NO EMAIL ADDRESS", 0);
        Controller controller = new Controller(new ArrayList<User>(), new ConsoleInputAndOutputFactory(), new BookLibraryFactory(),
                new MainMenuFactory(), new MovieLibraryFactory(), new WelcomeMessageFactory(), currentUser);
        controller.displayWelcomeMessage();

        assertEquals("Hello! Welcome to Bangalore Public Library!\n", outputContent.toString());
    }

    @Test
    public void shouldInitialiseMenuToDisplayItsOptionsAndDispatch() {
        ByteArrayInputStream inputOption = new ByteArrayInputStream("Invalid".getBytes());
        System.setIn(inputOption);

        User currentUser = new User("GUEST USER", "NO PASSWORD", ROLE.GUEST_USER, "NO NAME", "NO EMAIL ADDRESS", 0);
        Controller controller = new Controller(new ArrayList<User>(), new ConsoleInputAndOutputFactory(), new BookLibraryFactory(),
                new MainMenuFactory(), new MovieLibraryFactory(), new WelcomeMessageFactory(), currentUser);
        controller.displayMenuOptions();

        assertEquals("MAIN MENU\n1. Login\n2. List Books\n3. Checkout Book\n4. Return Book\n5. List Movies\n6. Checkout Movie" +
                "\n7. List Checked Out Books with User's Library Number\n8. User Information\n9. Logout\n10. Quit\n\n", outputContent.toString());
    }

    @Test
    public void shouldUseAFactoryToCreateConsoleInput() {
        ConsoleInputAndOutputFactory consoleInputAndOutputFactory = mock(ConsoleInputAndOutputFactory.class);
        User currentUser = new User("GUEST USER", "NO PASSWORD", ROLE.GUEST_USER, "NO NAME", "NO EMAIL ADDRESS", 0);
        Controller controller = new Controller(new ArrayList<User>(), consoleInputAndOutputFactory, new BookLibraryFactory(),
                new MainMenuFactory(), new MovieLibraryFactory(), new WelcomeMessageFactory(), currentUser);

        verify(consoleInputAndOutputFactory, times(1)).createConsoleInput();
    }

    @Test
    public void shouldUseAFactoryToCreateConsoleOutput() {
        ConsoleInputAndOutputFactory consoleInputAndOutputFactory = mock(ConsoleInputAndOutputFactory.class);
        User currentUser = new User("GUEST USER", "NO PASSWORD", ROLE.GUEST_USER, "NO NAME", "NO EMAIL ADDRESS", 0);
        Controller controller = new Controller(new ArrayList<User>(), consoleInputAndOutputFactory, new BookLibraryFactory(),
                new MainMenuFactory(), new MovieLibraryFactory(), new WelcomeMessageFactory(), currentUser);

        verify(consoleInputAndOutputFactory, times(1)).createConsoleOutput();
    }

    @Test
    public void shouldUseBookLibraryFactoryToCreateBookLibrary() {
        BookLibraryFactory bookLibraryFactory = mock(BookLibraryFactory.class);
        User currentUser = new User("GUEST USER", "NO PASSWORD", ROLE.GUEST_USER, "NO NAME", "NO EMAIL ADDRESS", 0);
        Controller controller = new Controller(new ArrayList<User>(), new ConsoleInputAndOutputFactory(), bookLibraryFactory,
                new MainMenuFactory(), new MovieLibraryFactory(), new WelcomeMessageFactory(), currentUser);

        verify(bookLibraryFactory, times(1)).createBookLibrary();
    }

    @Test
    public void shouldUseMovieLibraryFactoryToCreateMovieLibrary() {
        MovieLibraryFactory movieLibraryFactory = mock(MovieLibraryFactory.class);
        User currentUser = new User("GUEST USER", "NO PASSWORD", ROLE.GUEST_USER, "NO NAME", "NO EMAIL ADDRESS", 0);
        Controller controller = new Controller(new ArrayList<User>(), new ConsoleInputAndOutputFactory(), new BookLibraryFactory(),
                new MainMenuFactory(), movieLibraryFactory, new WelcomeMessageFactory(), currentUser);

        verify(movieLibraryFactory, times(1)).createMovieLibrary();
    }

    @Test
    public void shouldUseMainMenuFactoryToCreateBookLibrary() {
        MainMenuFactory mainMenuFactory = mock(MainMenuFactory.class);
        User currentUser = new User("GUEST USER", "NO PASSWORD", ROLE.GUEST_USER, "NO NAME", "NO EMAIL ADDRESS", 0);
        Controller controller = new Controller(new ArrayList<User>(), new ConsoleInputAndOutputFactory(), new BookLibraryFactory(),
                mainMenuFactory, new MovieLibraryFactory(), new WelcomeMessageFactory(), currentUser);

        verify(mainMenuFactory, times(1)).createMainMenu();
    }

    @Test
    public void shouldUseWelcomeMessageFactoryToCreateWelcomeMessage() {
        WelcomeMessageFactory welcomeMessageFactory = mock(WelcomeMessageFactory.class);
        User currentUser = new User("GUEST USER", "NO PASSWORD", ROLE.GUEST_USER, "NO NAME", "NO EMAIL ADDRESS", 0);
        Controller controller = new Controller(new ArrayList<User>(), new ConsoleInputAndOutputFactory(), new BookLibraryFactory(),
                new MainMenuFactory(), new MovieLibraryFactory(), welcomeMessageFactory, currentUser);

        verify(welcomeMessageFactory, times(1)).createWelcomeMessage();
    }

    @Test
    public void shouldUseConsoleInputWhileTakingMenuOptionsAsInputFromConsole() {
        ByteArrayInputStream inputOneOption = new ByteArrayInputStream("1".getBytes());
        System.setIn(inputOneOption);
        ConsoleInputAndOutputFactory consoleInputAndOutputFactory = mock(ConsoleInputAndOutputFactory.class);
        when(consoleInputAndOutputFactory.createConsoleInput()).thenReturn(new ConsoleInput());
        User currentUser = new User("GUEST USER", "NO PASSWORD", ROLE.GUEST_USER, "NO NAME", "NO EMAIL ADDRESS", 0);
        Controller controller = new Controller(new ArrayList<User>(), consoleInputAndOutputFactory, new BookLibraryFactory(),
                new MainMenuFactory(), new MovieLibraryFactory(), new WelcomeMessageFactory(), currentUser);

        assertEquals("1", controller.menuOption());
    }

    @Test
    public void shouldDisplayInvalidMessageWhenInvalidOptionIsEntered() {
        ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
        ConsoleInputAndOutputFactory consoleInputAndOutputFactory = mock(ConsoleInputAndOutputFactory.class);

        when(consoleInputAndOutputFactory.createConsoleOutput()).thenReturn(new ConsoleOutput());
        User currentUser = new User("GUEST USER", "NO PASSWORD", ROLE.GUEST_USER, "NO NAME", "NO EMAIL ADDRESS", 0);
        Controller controller = new Controller(new ArrayList<User>(), consoleInputAndOutputFactory, new BookLibraryFactory(),
                new MainMenuFactory(), new MovieLibraryFactory(), new WelcomeMessageFactory(), currentUser);
        controller.dispatchMenuOption("Invalid");

        assertEquals("Select a valid option!\n", outputContent.toString());
    }

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void shouldQuitTheApplicationWhenMenuOptionOfTenIsEntered() {
        exit.expectSystemExitWithStatus(0);
        ConsoleInputAndOutputFactory consoleInputAndOutputFactory = mock(ConsoleInputAndOutputFactory.class);
        User currentUser = new User("GUEST USER", "NO PASSWORD", ROLE.GUEST_USER, "NO NAME", "NO EMAIL ADDRESS", 0);
        Controller controller = new Controller(new ArrayList<User>(), consoleInputAndOutputFactory, new BookLibraryFactory(),
                new MainMenuFactory(), new MovieLibraryFactory(), new WelcomeMessageFactory(), currentUser);
        controller.dispatchMenuOption("10");
    }

    @Test
    public void shouldNotLogoutTheUserWhenUserIsNotLoggedInWhenMenuOptionNineIsEntered() {
        ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
        User currentUser = new User("GUEST USER", "NO PASSWORD", ROLE.GUEST_USER, "NO NAME", "NO EMAIL ADDRESS", 0);
        Controller controller = new Controller(new ArrayList<User>(), new ConsoleInputAndOutputFactory(), new BookLibraryFactory(),
                new MainMenuFactory(), new MovieLibraryFactory(), new WelcomeMessageFactory(), currentUser);
        controller.dispatchMenuOption("9");

        assertEquals("NOT AUTHORISED to use this option. Please try again.\n", outputContent.toString());
    }

    @Test
    public void shouldLogoutTheUserWhenMenuOptionNineIsEntered() {
        ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
        User currentUser = new User("lib-1000", "password", ROLE.LIBRARIAN, "Madam Pince", "librarian@hogwarts.com", 968684524);
        Controller controller = new Controller(new ArrayList<User>(), new ConsoleInputAndOutputFactory(), new BookLibraryFactory(),
                new MainMenuFactory(), new MovieLibraryFactory(), new WelcomeMessageFactory(), currentUser);
        controller.dispatchMenuOption("9");

        assertEquals("You are logged out\n", outputContent.toString());
    }

    @Test
    public void shouldDisplayUserInformationWhenMenuOptionEightIsEntered() {
        ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
        User currentUser = new User("GUEST USER", "NO PASSWORD", ROLE.GUEST_USER, "NO NAME", "NO EMAIL ADDRESS", 0);
        Controller controller = new Controller(new ArrayList<User>(), new ConsoleInputAndOutputFactory(), new BookLibraryFactory(),
                new MainMenuFactory(), new MovieLibraryFactory(), new WelcomeMessageFactory(), currentUser);
        controller.dispatchMenuOption("8");

        assertEquals("NOT AUTHORISED to use this option. Please try again.\n", outputContent.toString());
    }

    @Test
    public void shouldNotDisplayUserInformationWhenMenuOptionEightIsEnteredAndUserIsAGuest() {
        ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
        User currentUser = new User("lib-1000", "password", ROLE.LIBRARIAN, "Madam Pince", "librarian@hogwarts.com", 968684524);
        Controller controller = new Controller(new ArrayList<User>(), new ConsoleInputAndOutputFactory(), new BookLibraryFactory(),
                new MainMenuFactory(), new MovieLibraryFactory(), new WelcomeMessageFactory(), currentUser);
        controller.dispatchMenuOption("8");

        assertEquals("USER INFORMATION\nLibrary Number: lib-1000\nUser Name: Madam Pince\nEmail Address: librarian@hogwarts.com\n" +
                "Phone Number: 968684524\n", outputContent.toString());
    }

    @Test
    public void shouldNotDisplayListOfCheckedOutItemsWithUserIfUserIsNotALibrarianAndOptionSevenIsEntered() {
        ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
        User currentUser = new User("GUEST USER", "NO PASSWORD", ROLE.GUEST_USER, "NO NAME", "NO EMAIL ADDRESS", 0);
        Controller controller = new Controller(new ArrayList<User>(), new ConsoleInputAndOutputFactory(), new BookLibraryFactory(),
                new MainMenuFactory(), new MovieLibraryFactory(), new WelcomeMessageFactory(), currentUser);
        controller.dispatchMenuOption("7");

        assertEquals("NOT AUTHORISED to use this option. Please try again.\n", outputContent.toString());
    }

    @Test
    public void shouldDisplayListOfCheckedOutBooksWithUserIfUserIsALibrarianAndOptionSevenIsEntered() {
        ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
        User currentUser = new User("lib-1000", "password", ROLE.LIBRARIAN, "Madam Pince", "librarian@hogwarts.com", 968684524);
        BookLibraryFactory bookLibraryFactory = mock(BookLibraryFactory.class);
        BookLibrary bookLibrary = mock(BookLibrary.class);
        when(bookLibraryFactory.createBookLibrary()).thenReturn(bookLibrary);
        String formattedList = String.format("%-30s%-30s%-15s%-20s\n", "NAME OF BOOK", "NAME OF AUTHOR", "YEAR OF PUBLICATION", "USER LIBRARY NUMBER") +
                String.format("%-30s%-30s%-15s%-20s\n", "The Scarlett Letter", "Nathaniel Hawthorne", 1850, "usr-1001");
        when(bookLibrary.checkedOutBookListWithUser()).thenReturn(formattedList);
        Controller controller = new Controller(new ArrayList<User>(), new ConsoleInputAndOutputFactory(), bookLibraryFactory,
                new MainMenuFactory(), new MovieLibraryFactory(), new WelcomeMessageFactory(), currentUser);
        controller.dispatchMenuOption("7");

        assertEquals(formattedList + "\n", outputContent.toString());
    }

    @Test
    public void shouldCheckOutAMovieFromMovieLibraryWhenMenuOptionOfSixIsEntered() {
        ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
        ConsoleInputAndOutputFactory consoleInputAndOutputFactory = mock(ConsoleInputAndOutputFactory.class);
        ConsoleInput consoleInput = mock(ConsoleInput.class);
        when(consoleInputAndOutputFactory.createConsoleOutput()).thenReturn(new ConsoleOutput());
        when(consoleInputAndOutputFactory.createConsoleInput()).thenReturn(consoleInput);
        when(consoleInput.getInput()).thenReturn("Funny Girl");
        User currentUser = new User("GUEST USER", "NO PASSWORD", ROLE.GUEST_USER, "NO NAME", "NO EMAIL ADDRESS", 0);

        Controller controller = new Controller(new ArrayList<User>(), consoleInputAndOutputFactory, new BookLibraryFactory(),
                new MainMenuFactory(), new MovieLibraryFactory(), new WelcomeMessageFactory(), currentUser);
        controller.dispatchMenuOption("6");

        assertEquals("Enter what is to be checked out:\nThank you! Enjoy the movie\n", outputContent.toString());
    }


    @Test
    public void shouldListMoviesFromMovieLibraryWhenMovieOptionOfFiveIsEntered() {
        ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
        ConsoleInputAndOutputFactory consoleInputAndOutputFactory = mock(ConsoleInputAndOutputFactory.class);
        when(consoleInputAndOutputFactory.createConsoleOutput()).thenReturn(new ConsoleOutput());
        User currentUser = new User("GUEST USER", "NO PASSWORD", ROLE.GUEST_USER, "NO NAME", "NO EMAIL ADDRESS", 0);
        Controller controller = new Controller(new ArrayList<User>(), consoleInputAndOutputFactory, new BookLibraryFactory(),
                new MainMenuFactory(), new MovieLibraryFactory(), new WelcomeMessageFactory(), currentUser);
        controller.dispatchMenuOption("5");
        String formattedMovieString = String.format("%-30s%-15s%-30s%-15s\n", "NAME OF MOVIE", "YEAR", "DIRECTOR", "MOVIE RATING") +
                String.format("%-30s%-15s%-30s%-15s\n", "Funny Girl", 1968, "William Wyler", "8") +
                String.format("%-30s%-15s%-30s%-15s\n", "Pretty in Pink", 1986, "John Hughes", "10") + "\n";

        assertEquals(formattedMovieString, outputContent.toString());
    }

    @Test
    public void shouldPrintInvalidOptionIfUserHasNotLoggedInToReturnABookToLibraryWhenMenuOptionOfFourIsEntered() {
        System.setOut(new PrintStream(outputContent));
        ConsoleInputAndOutputFactory consoleInputAndOutputFactory = mock(ConsoleInputAndOutputFactory.class);
        ConsoleInput consoleInput = mock(ConsoleInput.class);
        when(consoleInputAndOutputFactory.createConsoleOutput()).thenReturn(new ConsoleOutput());
        when(consoleInputAndOutputFactory.createConsoleInput()).thenReturn(consoleInput);
        when(consoleInput.getInput()).thenReturn("Gone Girl");
        User currentUser = new User("GUEST USER", "NO PASSWORD", ROLE.GUEST_USER, "NO NAME", "NO EMAIL ADDRESS", 0);
        Controller controller = new Controller(new ArrayList<User>(), consoleInputAndOutputFactory, new BookLibraryFactory(),
                new MainMenuFactory(), new MovieLibraryFactory(), new WelcomeMessageFactory(), currentUser);
        controller.dispatchMenuOption("4");

        assertEquals("NOT AUTHORISED to use this option. Please try again.\n", outputContent.toString());
    }

    @Test
    public void shouldReturnABookToLibraryWhenMenuOptionOfFourIsEntered() {
        System.setOut(new PrintStream(outputContent));
        ConsoleInputAndOutputFactory consoleInputAndOutputFactory = mock(ConsoleInputAndOutputFactory.class);
        ConsoleInput consoleInput = mock(ConsoleInput.class);
        when(consoleInputAndOutputFactory.createConsoleOutput()).thenReturn(new ConsoleOutput());
        when(consoleInputAndOutputFactory.createConsoleInput()).thenReturn(consoleInput);
        when(consoleInput.getInput()).thenReturn("Gone Girl");
        User currentUser = new User("usr-1001", "password1", ROLE.AUTHENTICATED_USER, "Hermione Granger", "hermione@gmail.com", 867546351);
        Controller controller = new Controller(new ArrayList<User>(), consoleInputAndOutputFactory, new BookLibraryFactory(),
                new MainMenuFactory(), new MovieLibraryFactory(), new WelcomeMessageFactory(), currentUser);
        controller.dispatchMenuOption("4");

        assertEquals("Enter book to be returned:\nThat is not a valid book to return\n", outputContent.toString());
    }

    @Test
    public void shouldPrintInvalidOptionIfUserHasNotLoggedInForCheckedOutBookWhenMenuOptionOfThreeIsEntered() {
        ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
        ConsoleInputAndOutputFactory consoleInputAndOutputFactory = mock(ConsoleInputAndOutputFactory.class);
        ConsoleInput consoleInput = mock(ConsoleInput.class);
        when(consoleInputAndOutputFactory.createConsoleInput()).thenReturn(consoleInput);
        when(consoleInput.getInput()).thenReturn("Gone Girl");
        when(consoleInputAndOutputFactory.createConsoleOutput()).thenReturn(new ConsoleOutput());
        User currentUser = new User("GUEST USER", "NO PASSWORD", ROLE.GUEST_USER, "NO NAME", "NO EMAIL ADDRESS", 0);
        ArrayList<User> listOfUsers = new ArrayList<>();
        listOfUsers.add(new User("lib-1000", "password", ROLE.LIBRARIAN, "Madam Pince", "librarian@hogwarts.com", 968684524));
        listOfUsers.add(new User("usr-1001", "password1", ROLE.AUTHENTICATED_USER, "Hermione Granger", "hermione@gmail.com", 867546351));
        Controller controller = new Controller(new ArrayList<User>(), consoleInputAndOutputFactory, new BookLibraryFactory(),
                new MainMenuFactory(), new MovieLibraryFactory(), new WelcomeMessageFactory(), currentUser);
        controller.dispatchMenuOption("3");

        assertEquals("NOT AUTHORISED to use this option. Please try again.\n", outputContent.toString());
    }

    @Test
    public void shouldCheckOutABookFromLibraryWhenMenuOptionOfThreeIsEntered() {
        ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
        ConsoleInputAndOutputFactory consoleInputAndOutputFactory = mock(ConsoleInputAndOutputFactory.class);
        ConsoleInput consoleInput = mock(ConsoleInput.class);
        when(consoleInputAndOutputFactory.createConsoleInput()).thenReturn(consoleInput);
        when(consoleInput.getInput()).thenReturn("Gone Girl");
        when(consoleInputAndOutputFactory.createConsoleOutput()).thenReturn(new ConsoleOutput());
        User currentUser = new User("usr-1001", "password1", ROLE.AUTHENTICATED_USER, "Hermione Granger", "hermione@gmail.com", 867546351);
        Controller controller = new Controller(new ArrayList<User>(), consoleInputAndOutputFactory, new BookLibraryFactory(),
                new MainMenuFactory(), new MovieLibraryFactory(), new WelcomeMessageFactory(), currentUser);
        controller.dispatchMenuOption("3");

        assertEquals("Enter what is to be checked out:\nThank you! Enjoy the book\n", outputContent.toString());
    }

    @Test
    public void shouldListBooksFromBookLibraryWhenMovieOptionOfTwoIsEntered() {
        ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
        ConsoleInputAndOutputFactory consoleInputAndOutputFactory = mock(ConsoleInputAndOutputFactory.class);
        when(consoleInputAndOutputFactory.createConsoleOutput()).thenReturn(new ConsoleOutput());
        User currentUser = new User("GUEST USER", "NO PASSWORD", ROLE.GUEST_USER, "NO NAME", "NO EMAIL ADDRESS", 0);
        Controller controller = new Controller(new ArrayList<User>(), consoleInputAndOutputFactory, new BookLibraryFactory(),
                new MainMenuFactory(), new MovieLibraryFactory(), new WelcomeMessageFactory(), currentUser);
        controller.dispatchMenuOption("2");
        String formattedListOfBooks = String.format("%-30s%-30s%-15s\n", "NAME OF BOOK", "NAME OF AUTHOR", "YEAR OF PUBLICATION")
                + String.format("%-30s%-30s%-15s\n", "To Kill A Mockingbird", "Harper Lee", 1968)
                + String.format("%-30s%-30s%-15s\n", "Gone Girl", "Gillian Flynn", 2000)
                + String.format("%-30s%-30s%-15s\n", "The Scarlett Letter", "Nathaniel Hawthorne", 1850) + "\n";

        assertEquals(formattedListOfBooks, outputContent.toString());
    }

    @Test
    public void shouldInvokeLoginWhenOptionOneIsEntered() {
        ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
        ConsoleInputAndOutputFactory consoleInputAndOutputFactory = mock(ConsoleInputAndOutputFactory.class);
        ConsoleInput consoleInput = mock(ConsoleInput.class);
        when(consoleInputAndOutputFactory.createConsoleInput()).thenReturn(consoleInput);
        when(consoleInput.getInput()).thenReturn("usr-1000", "password1");
        when(consoleInputAndOutputFactory.createConsoleOutput()).thenReturn(new ConsoleOutput());
        User currentUser = new User("GUEST USER", "NO PASSWORD", ROLE.GUEST_USER, "NO NAME", "NO EMAIL ADDRESS", 0);
        Controller controller = new Controller(new ArrayList<User>(), consoleInputAndOutputFactory, new BookLibraryFactory(),
                new MainMenuFactory(), new MovieLibraryFactory(), new WelcomeMessageFactory(), currentUser);
        controller.dispatchMenuOption("1");

        assertEquals("Enter username:\nEnter password:\nLogin Unsuccessful\n", outputContent.toString());
    }

    @Test
    public void shouldInvokeNotAuthorisedMessageWhenOptionOneIsEnteredAndUserIsNotGuest() {
        ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
        ConsoleInputAndOutputFactory consoleInputAndOutputFactory = mock(ConsoleInputAndOutputFactory.class);
        ConsoleInput consoleInput = mock(ConsoleInput.class);
        when(consoleInputAndOutputFactory.createConsoleInput()).thenReturn(consoleInput);
        when(consoleInput.getInput()).thenReturn("usr-1000", "password1");
        when(consoleInputAndOutputFactory.createConsoleOutput()).thenReturn(new ConsoleOutput());
        User currentUser = new User("lib-1000", "password", ROLE.LIBRARIAN, "Madam Pince", "librarian@hogwarts.com", 968684524);
        Controller controller = new Controller(new ArrayList<User>(), consoleInputAndOutputFactory, new BookLibraryFactory(),
                new MainMenuFactory(), new MovieLibraryFactory(), new WelcomeMessageFactory(), currentUser);
        controller.dispatchMenuOption("1");

        assertEquals("NOT AUTHORISED to use this option. Please try again.\n", outputContent.toString());
    }

    @Test
    public void shouldPrintWelcomeMessageAtLeastOnceAndDisplayMainMenuWhenBibliotecaApplicationIsInitialised() {
        exit.expectSystemExitWithStatus(0);
        WelcomeMessageFactory welcomeMessageFactory = mock(WelcomeMessageFactory.class);
        WelcomeMessage welcomeMessage = mock(WelcomeMessage.class);
        when(welcomeMessageFactory.createWelcomeMessage()).thenReturn(welcomeMessage);
        when(welcomeMessage.getWelcomeMessage()).thenReturn("Hello! Welcome to Bangalore Public Library!");
        User currentUser = new User("GUEST USER", "NO PASSWORD", ROLE.GUEST_USER, "NO NAME", "NO EMAIL ADDRESS", 0);
        ConsoleInputAndOutputFactory consoleInputAndOutputFactory = mock(ConsoleInputAndOutputFactory.class);
        ConsoleInput consoleInput = mock(ConsoleInput.class);
        when(consoleInputAndOutputFactory.createConsoleInput()).thenReturn(consoleInput);
        when(consoleInputAndOutputFactory.createConsoleOutput()).thenReturn(new ConsoleOutput());
        when(consoleInput.getInput()).thenReturn("10");
        Controller controller = new Controller(new ArrayList<User>(), consoleInputAndOutputFactory, new BookLibraryFactory(),
                new MainMenuFactory(), new MovieLibraryFactory(), welcomeMessageFactory, currentUser);
        controller.initialiseBibliotecaApplication();

        assertEquals("Hello! Welcome to Bangalore Public Library!\n\"MAIN MENU\n1. Login\n2. List Books\n3. Checkout Book\n4. Return Book\n5. List Movies\n6. Checkout Movie\n"
                +"\n7. List Checked Out Books with User's Library Number\n8. User Information\n9. Logout\n10. Quit\n\n", outputContent.toString());
    }
}