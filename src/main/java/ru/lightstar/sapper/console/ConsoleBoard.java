package ru.lightstar.sapper.console;

import ru.lightstar.sapper.Board;
import ru.lightstar.sapper.Bomb;
import ru.lightstar.sapper.Cell;
import ru.lightstar.sapper.io.Output;

/**
 * Implementation of <code>Board</code> interface used to draw board on console.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class ConsoleBoard implements Board {

    /**
     * <code>Output</code> to draw board on.
     */
    private final Output output;

    /**
     * Board cells.
     */
    private Cell[][] cells;

    /**
     * Constructs <code>ConsoleBoard</code> object.
     *
     * @param output <code>Output</code> object for this board.
     */
    public ConsoleBoard(final Output output) {
        this.output = output;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBoard(final Cell[][] cells) {
        this.cells = cells;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawBoard() {
        this.redraw(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawLose() {
        this.output.println("***** BANG *****");
        this.redraw(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawWin() {
        this.output.println("***** CONGRATULATE *****");
        this.redraw(true);
    }

    /**
     * Redraw board on console.
     *
     * @param isUnfold if <code>true</code> then real cell state is printed, otherwise only suggested state is printed.
     */
    private void redraw(final boolean isUnfold) {
        for (final Cell[] row : this.cells) {
            for (final Cell cell : row) {
                if (isUnfold) {
                    this.drawCellUnfold(cell);
                } else {
                    this.drawCellSuggest(cell);
                }
            }
            this.output.println("");
        }
        this.output.println("");
    }

    /**
     * Draw cell reflecting user suggest about it.
     *
     * @param cell displayed cell.
     */
    private void drawCellSuggest(final Cell cell) {
        switch(cell.getSuggest()) {
            case BOMB:
                this.drawSuggestBomb(cell);
               break;
            case EMPTY:
                this.drawEmpty(cell);
                break;
            case NONE:
                this.drawHidden(cell);
                break;
        }
    }

    /**
     * Draw cell with its real state (when game is over).
     *
     * @param cell displayed cell.
     */
    private void drawCellUnfold(final Cell cell) {
        if (cell.getBomb() != Bomb.NONE) {
            this.drawBomb(cell);
        } else {
            this.drawEmpty(cell);
        }
    }

    /**
     * Draw cell with bomb user suggest.
     *
     * @param cell displayed cell.
     */
    protected void drawSuggestBomb(final Cell cell) {
        this.output.print("[?] ");
    }

    /**
     * Draw empty cell.
     *
     * @param cell displayed cell.
     */
    protected void drawEmpty(final Cell cell) {
        if (cell.getNearestBombCount() == 0) {
            this.output.print("[ ] ");
        } else {
            this.output.print(String.format("[%d] ", cell.getNearestBombCount()));
        }
    }

    /**
     * Draw cell with bomb.
     *
     * @param cell displayed cell.
     */
    protected void drawBomb(final Cell cell) {
        this.output.print("[*] ");
    }

    /**
     * Draw cell which contents is hidden.
     *
     * @param cell displayed cell.
     */
    protected void drawHidden(final Cell cell) {
        this.output.print("[X] ");
    }
}
