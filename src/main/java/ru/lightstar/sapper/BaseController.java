package ru.lightstar.sapper;

import ru.lightstar.sapper.exception.GenerateException;
import ru.lightstar.sapper.exception.LogicException;

/**
 * Base implementation of <code>Controller</code> interface.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class BaseController implements Controller {

    /**
     * Board generator.
     */
    private final BoardGenerator generator;

    /**
     * Game board.
     */
    private final Board board;

    /**
     * Game logic.
     */
    private final Logic logic;

    /**
     * Constructs <code>BaseController</code> object.
     *
     * @param logic game logic.
     * @param board game board.
     * @param generator generator of game board.
     */
    public BaseController(final Logic logic, final Board board, final BoardGenerator generator) {
        this.generator = generator;
        this.board = board;
        this.logic = logic;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final int width, final int height, final int bombCount) throws GenerateException, LogicException {
        final Cell[][] cells = this.generator.generate(width, height, bombCount);
        this.logic.setBoard(cells);
        this.board.setBoard(cells);
        this.board.drawBoard();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void suggest(final int x, final int y, final Suggest suggest) throws LogicException {
        this.logic.suggest(x, y, suggest);

        if (this.logic.isLose()) {
            this.board.drawLose();
        } else if (this.logic.isWin()) {
            this.board.drawWin();
        } else {
            this.board.drawBoard();
        }
    }

    /**
     * Get game board.
     *
     * @return game board.
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Get game logic.
     *
     * @return game logic.
     */
    public Logic getLogic() {
        return this.logic;
    }
}
