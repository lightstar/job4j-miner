package ru.lightstar.sapper;

import ru.lightstar.sapper.exception.GenerateException;
import ru.lightstar.sapper.exception.LogicException;

/**
 * Sapper game controller interface.
 *
 * @author LightStar
 * @since 0.0.1
 */
public interface Controller {

    /**
     * Initialize sapper game and all of its components.
     *
     * @throws GenerateException thrown if generator can't generate board.
     * @throws LogicException thrown if game logic can't initialize.
     */
    void init(int width, int height, int bombCount) throws GenerateException, LogicException;

    /**
     * Make user's suggestion about the given cell.
     *
     * @param x cell's 'x' coordinate.
     * @param y cell's 'y' coordinate.
     * @param suggest user's suggest.
     * @throws LogicException thrown if coordinates are wrong or suggest can't be made for other reasons.
     */
    void suggest(int x, int y, Suggest suggest) throws LogicException;
}
