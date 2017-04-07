package ru.lightstar.sapper.exception;

/**
 * Error in board generation.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class GenerateException extends Exception {

    /**
     * Constructs <code>GenerateException</code> object.
     *
     * @param message error message.
     */
    public GenerateException(final String message) {
        super(message);
    }
}
