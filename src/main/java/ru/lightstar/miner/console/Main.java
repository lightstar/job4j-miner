package ru.lightstar.miner.console;

import ru.lightstar.miner.RandomBoardGenerator;
import ru.lightstar.miner.io.Console;

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
