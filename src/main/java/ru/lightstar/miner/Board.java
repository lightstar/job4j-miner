package ru.lightstar.miner;

/**
 * Interface used to draw board and show other UI.
 *
 * @author LightStar
 * @since 0.0.1
 */
public interface Board<T> {

    /**
     * Set output object (where to draw).
     *
     * @param output output object.
     */
    void setOutput(T output);

    /**
     * Set board cells.
     *
     * @param cells board cells.
     */
    void setBoard(Cell[][] cells);

    /**
     * Draw board.
     */
    void drawBoard();

    /**
     * Draw cell at given coordinates.
     *
     * @param x cell's 'x' coordinate.
     * @param y cell's 'y' coordinate.
     */
    void drawCell(int x, int y);

    /**
     * Draw user's lose state.
     */
    void drawLose();

    /**
     * Draw user's win state.
     */
    void drawWin();
}
