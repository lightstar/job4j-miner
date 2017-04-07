package ru.lightstar.sapper.exception;

/**
 * Error in processing game logic.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class LogicException extends Exception {

    /**
     * Constructs <code>LogicException</code> object.
     *
     * @param message error message.
     */
    public LogicException(final String message) {
        super(message);
    }
}
