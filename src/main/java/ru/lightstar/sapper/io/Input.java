package ru.lightstar.sapper.io;

/**
 * User input interface
 *
 * @author LightStar
 * @since 0.0.1
 */
public interface Input {

    /**
     * Get next string from input.
     *
     * @return next string.
     */
    String next();

    /**
     * Wait for user to press 'Enter' key.
     */
    void waitEnter();

    /**
     * Ask user a question and get answer.
     *
     * @param output output for question.
     * @param question question.
     * @return user's answer.
     */
    String ask(Output output, String question);

    /**
     * Ask user a question requesting a number and get answer.
     *
     * @param output output for question.
     * @param question question.
     * @return user's answer.
     */
    int askNumber(Output output, String question);

    /**
     * Close this input.
     */
    void close();
}
