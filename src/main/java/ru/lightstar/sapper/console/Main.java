package ru.lightstar.sapper.console;

import ru.lightstar.sapper.RandomBoardGenerator;
import ru.lightstar.sapper.io.Console;

/**
 * Entry point for console runner.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class Main {

    /**
     * Entry point for console runner.
     *
     * @param args not used.
     */
    public static void main(final String[] args) {
        final Console console = new Console();
        final ConsoleRunner consoleRunner = new ConsoleRunner(console, console);
        consoleRunner.run(RandomBoardGenerator.class);
    }
}
