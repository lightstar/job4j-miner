package ru.lightstar.miner;

import ru.lightstar.miner.exception.LogicException;

/**
 * Miner game controller interface.
 *
 * @author LightStar
 * @since 0.0.1
 */
public interface Controller {

    /**
     * Initialize miner game and all of its components.
     *
     * @throws LogicException thrown if game logic can't initialize.
     */
    void init() throws LogicException;

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
