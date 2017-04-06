package ru.lightstar.miner.console;

import ru.lightstar.miner.RandomBoardGenerator;
import ru.lightstar.miner.exception.LogicException;
import ru.lightstar.miner.io.Console;

/**
 * <code>Main</code> class.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class Main {

    public static void main(final String[] args) throws LogicException {
        final Console console = new Console();
        final ConsoleRunner consoleRunner = new ConsoleRunner(console, console);
        consoleRunner.run(RandomBoardGenerator.class);
    }
}
