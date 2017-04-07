package ru.lightstar.sapper;

import ru.lightstar.sapper.exception.GenerateException;

/**
 * Interface for board generator.
 *
 * @author LightStar
 * @since 0.0.1
 */
public interface BoardGenerator {

    /**
     * Generate board cells.
     *
     * @param width board's width.
     * @param height board's height/
     * @param bombCount total bomb count on board.
     * @return generated cells.
     * @throws GenerateException thrown if parameters are invalid.
     */
    Cell[][] generate(int width, int height, int bombCount) throws GenerateException;
}
