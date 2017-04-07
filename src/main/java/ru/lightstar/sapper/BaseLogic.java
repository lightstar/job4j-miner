package ru.lightstar.sapper;

import ru.lightstar.sapper.exception.LogicException;

/**
 * Base Sapper game logic.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class BaseLogic implements Logic {

    /**
     * Board cells.
     */
    private Cell[][] cells;

    /**
     * Board width.
     */
    private int width;

    /**
     * Board height.
     */
    private int height;

    /**
     * Total bombs count on board.
     */
    private int bombCount;

    /**
     * Count of bombs suggested by user.
     */
    protected int suggestedBombCount;

    /**
     * Count of user's correct suggests.
     */
    protected int correctSuggestCount;

    /**
     * Game 'lose' flag.
     */
    protected boolean isLose;

    /**
     * Game 'win' flag.
     */
    protected boolean isWin;


    /**
     * {@inheritDoc}
     */
    @Override
    public void setBoard(final Cell[][] cells) throws LogicException {
        this.checkBoard(cells);

        this.cells = cells;
        this.height = cells.length;
        this.width = cells[0].length;
        this.correctSuggestCount = 0;
        this.suggestedBombCount = 0;
        this.isLose = false;
        this.isWin = false;

        this.bombCount = 0;
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                if (cells[y][x].getBomb() != Bomb.NONE) {
                    this.bombCount++;
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBombCount() {
        return this.bombCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSuggestedBombCount() {
        return this.suggestedBombCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLose() {
        return this.isLose;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWin() {
        return this.isWin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void suggest(final int x, final int y, final Suggest suggest) throws LogicException {
        this.checkSuggest(x, y);

        final Cell cell = this.cells[y][x];
        final Suggest oldSuggest = cell.getSuggest();
        cell.setSuggest(suggest);

        this.countCorrectSuggests(cell, oldSuggest);
        this.countSuggestedBombs(cell, oldSuggest);
        this.checkForFinish(cell, oldSuggest);

        if (suggest == Suggest.EMPTY && cell.getNearestBombCount() == 0) {
            this.autoEmptySuggestAllNearest(x, y);
        }
    }

    /**
     * Called to check if game is finished after user makes a suggest about cell.
     * Override this if you want to change default logic.
     *
     * @param cell       cell which suggest was changed.
     * @param oldSuggest old suggest of this cell.
     */
    protected void checkForFinish(final Cell cell, final Suggest oldSuggest) {
        if (cell.getSuggest() == Suggest.EMPTY && cell.getBomb() != Bomb.NONE) {
            this.isLose = true;
        } else if (this.height * this.width - this.correctSuggestCount <= this.bombCount - this.suggestedBombCount) {
            this.isWin = true;
        }
    }

    /**
     * Checks if given suggest about given cell is correct.
     *
     * @param cell    given cell.
     * @param suggest given suggest.
     * @return <code>true</code> if suggest is correct, and <code>false</code> otherwise.
     */
    protected boolean isSuggestCorrect(final Cell cell, final Suggest suggest) {
        return (suggest == Suggest.EMPTY && cell.getBomb() == Bomb.NONE) ||
                (suggest == Suggest.BOMB && cell.getBomb() != Bomb.NONE);
    }

    /**
     * Called to count user's correct suggests.
     *
     * @param cell       given cell.
     * @param oldSuggest given suggest.
     */
    protected void countCorrectSuggests(final Cell cell, final Suggest oldSuggest) {
        if (cell.getSuggest() != oldSuggest) {
            if (this.isSuggestCorrect(cell, cell.getSuggest())) {
                this.correctSuggestCount++;
            } else if (this.isSuggestCorrect(cell, oldSuggest)) {
                this.correctSuggestCount--;
            }
        }
    }

    /**
     * Called to count user's suggests about bombs.
     *
     * @param cell       given cell.
     * @param oldSuggest given suggest.
     */
    protected void countSuggestedBombs(final Cell cell, final Suggest oldSuggest) {
        if (cell.getSuggest() != oldSuggest) {
            if (cell.getSuggest() == Suggest.BOMB) {
                this.suggestedBombCount++;
            } else if (oldSuggest == Suggest.BOMB) {
                this.suggestedBombCount--;
            }
        }
    }

    /**
     * If user opens empty cell with zero nearest bombs count then all nearest cells are auto-opened by this method.
     *
     * @param x empty cell's 'x' coordinate.
     * @param y empty cell's 'y' coordinate.
     * @throws LogicException thrown by <code>suggest</code> method, but that shouldn't happen
     *                        because all necessary conditions are checked.
     */
    private void autoEmptySuggestAllNearest(final int x, final int y) throws LogicException {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                this.autoEmptySuggest(x + dx, y + dy);
            }
        }
    }

    /**
     * Auto-open guaranteed-empty cell.
     *
     * @param x opened cell's 'x' coordinate.
     * @param y opened cell's 'y' coordinate.
     * @throws LogicException thrown by <code>suggest</code> method, but that shouldn't happen
     *                        because all necessary conditions are checked.
     */
    private void autoEmptySuggest(final int x, final int y) throws LogicException {
        if (this.isLose || this.isWin ||
                x < 0 || x >= this.width || y < 0 || y >= this.height ||
                this.cells[y][x].getSuggest() == Suggest.EMPTY) {
            return;
        }

        this.suggest(x, y, Suggest.EMPTY);
    }

    /**
     * Check correctness of board dimensions.
     *
     * @param cells board cells.
     */
    private void checkBoard(final Cell[][] cells) throws LogicException {
        if (cells.length == 0) {
            throw new LogicException("Board can't have zero height");
        }

        if (cells[0].length == 0) {
            throw new LogicException("Board can't have zero width");
        }

        for (final Cell[] row : cells) {
            if (row.length != cells[0].length) {
                throw new LogicException("Wrong board dimensions");
            }
        }
    }

    /**
     * Check suggest permissibility and correctness of its coordinates.
     *
     * @param x suggest 'x' coordinate.
     * @param y suggest 'y' coordinate.
     * @throws LogicException thrown if game is over or one of coordinates is out of bounds
     *                        or given cell is already opened.
     */
    private void checkSuggest(final int x, final int y) throws LogicException {
        if (this.isLose || this.isWin) {
            throw new LogicException("Game is already over");
        }

        if (x < 0 || x >= this.width) {
            throw new LogicException("Provided 'x' parameter is out of bounds");
        }

        if (y < 0 || y >= this.height) {
            throw new LogicException("Provided 'y' parameter is out of bounds");
        }

        if (this.cells[y][x].getSuggest() == Suggest.EMPTY) {
            throw new LogicException("Cell already opened");
        }
    }
}
