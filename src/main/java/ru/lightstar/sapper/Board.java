package ru.lightstar.sapper;

/**
 * Interface used to draw board and show other UI.
 *
 * @author LightStar
 * @since 0.0.1
 */
public interface Board {

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
     * Draw user's lose state.
     */
    void drawLose();

    /**
     * Draw user's win state.
     */
    void drawWin();
}
