package ru.lightstar.sapper;

import ru.lightstar.sapper.exception.LogicException;

/**
 * Sapper game logic interface.
 *
 * @author LightStar
 * @since 0.0.1
 */
public interface Logic {

    /**
     * Set game board.
     * This operation resets the game.
     *
     * @param cells board cells.
     * @throws LogicException thrown if something is wrong with cells.
     */
    void setBoard(Cell[][] cells) throws LogicException;

    /**
     * Get board width.
     *
     * @return board width.
     */
    int getWidth();

    /**
     * Get board height.
     *
     * @return board height.
     */
    int getHeight();

    /**
     * Get total bombs count.
     *
     * @return bombs count.
     */
    int getBombCount();

    /**
     * Get count of bombs already suggested by user.
     *
     * @return count of bombs suggested by user.
     */
    int getSuggestedBombCount();

    /**
     * Check if game is already lost.
     *
     * @return <code>true</code> if game is lost.
     */
    boolean isLose();

    /**
     * Check if game is already won.
     *
     * @return <code>true</code> if game is won.
     */
    boolean isWin();

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
