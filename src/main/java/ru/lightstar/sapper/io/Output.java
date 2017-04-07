package ru.lightstar.sapper.io;

/**
 * Generic output interface.
 *
 * @author LightStar
 * @since 0.0.1
 */
public interface Output {

    /**
     * Output line of text.
     *
     * @param line line of text.
     */
    void println(String line);

    /**
     * Output text without line separator character.
     *
     * @param text text.
     */
    void print(String text);

    /**
     * Close this output.
     */
    void close();
}
