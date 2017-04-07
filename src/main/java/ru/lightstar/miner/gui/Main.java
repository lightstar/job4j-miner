package ru.lightstar.miner.gui;

import ru.lightstar.miner.RandomBoardGenerator;

/**
 * Entry point for GUI runner.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class Main {

    /**
     * Entry point for GUI runner.
     *
     * @param args not used.
     */
    public static void main(final String[] args) {
        final GUIRunner runner = new GUIRunner();
        runner.run(RandomBoardGenerator.class);
    }
}
