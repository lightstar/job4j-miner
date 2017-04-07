package ru.lightstar.sapper;

import org.junit.Test;
import ru.lightstar.sapper.exception.LogicException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * <code>BaseLogic</code> class tests.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class BaseLogicTest {

    /**
     * <code>BaseLogic</code> object used in tests.
     */
    private final BaseLogic logic;

    /**
     * Constructs <code>BaseLogicTest</code> object
     */
    public BaseLogicTest() {
        this.logic = new BaseLogic();
    }

    /**
     * Test correctness of <code>setBoard</code> method and getters.
     */
    @Test
    public void whenSetBoardThenItSets() throws LogicException {
        this.initTestLogic();

        assertThat(this.logic.getHeight(), is(6));
        assertThat(this.logic.getWidth(), is(2));
        assertThat(this.logic.getBombCount(), is(4));
        assertThat(this.logic.getSuggestedBombCount(), is(0));
        assertThat(this.logic.isLose(), is(false));
        assertThat(this.logic.isWin(), is(false));
    }

    /**
     * Test exception thrown for empty board.
     */
    @Test(expected = LogicException.class)
    public void whenZeroBoardThenException() throws LogicException {
        this.logic.setBoard(new Cell[][]{});
    }

    /**
     * Test exception thrown for empty row on board.
     */
    @Test(expected = LogicException.class)
    public void whenZeroRowOnBoardThenException() throws LogicException {
        this.logic.setBoard(new Cell[][]{
                {new BaseCell(Bomb.BOMB, 0)},
                {}
        });
    }

    /**
     * Test exception thrown for inconsistent board dimensions.
     */
    @Test(expected = LogicException.class)
    public void whenIncorrectBoardThenException() throws LogicException {
        this.logic.setBoard(new Cell[][]{
                {new BaseCell(Bomb.BOMB, 0),new BaseCell(Bomb.NONE, 1)},
                {new BaseCell(Bomb.NONE, 1)}
        });
    }

    /**
     * Test correctness of suggesting bomb.
     */
    @Test
    public void whenSuggestBombThenItSuggests() throws LogicException {
        this.initTestLogic();
        this.logic.suggest(0, 0, Suggest.BOMB);

        assertThat(this.logic.getSuggestedBombCount(), is(1));
        assertThat(this.logic.isLose(), is(false));
        assertThat(this.logic.isWin(), is(false));
    }

    /**
     * Test correctness of suggesting empty cell.
     */
    @Test
    public void whenSuggestEmptyThenItSuggests() throws LogicException {
        this.initTestLogic();
        this.logic.suggest(0, 1, Suggest.EMPTY);

        assertThat(this.logic.getSuggestedBombCount(), is(0));
        assertThat(this.logic.isLose(), is(false));
        assertThat(this.logic.isWin(), is(false));
    }

    /**
     * Test game over for suggesting empty cell when there is bomb.
     */
    @Test
    public void whenSuggestEmptyWhenThereIsBombThenItBooms() throws LogicException {
        this.initTestLogic();
        this.logic.suggest(0, 0, Suggest.EMPTY);

        assertThat(this.logic.isLose(), is(true));
        assertThat(this.logic.isWin(), is(false));
    }

    /**
     * Test winning game when correct suggesting enough cells.
     */
    @Test
    public void whenSuggestAllThenWin() throws LogicException {
        this.initTestLogic();
        this.logic.suggest(0, 0, Suggest.BOMB);
        this.logic.suggest(1, 0, Suggest.BOMB);
        this.logic.suggest(0, 1, Suggest.EMPTY);
        this.logic.suggest(1, 1, Suggest.EMPTY);
        this.logic.suggest(1, 2, Suggest.EMPTY);
        this.logic.suggest(1, 3, Suggest.EMPTY);
        this.logic.suggest(0, 5, Suggest.EMPTY);

        assertThat(this.logic.isWin(), is(true));
        assertThat(this.logic.isLose(), is(false));
    }

    /**
     * Test winning game when correct suggesting enough cells with changing decision in process.
     */
    @Test
    public void whenSuggestsAllWithChangingDecisionThenWin() throws LogicException {
        this.initTestLogic();
        this.logic.suggest(0, 0, Suggest.BOMB);
        this.logic.suggest(1, 0, Suggest.BOMB);

        this.logic.suggest(0, 1, Suggest.BOMB);
        this.logic.suggest(0, 1, Suggest.BOMB);
        this.logic.suggest(0, 1, Suggest.NONE);
        this.logic.suggest(0, 1, Suggest.EMPTY);

        this.logic.suggest(1, 1, Suggest.BOMB);
        this.logic.suggest(1, 1, Suggest.EMPTY);

        this.logic.suggest(1, 2, Suggest.BOMB);
        this.logic.suggest(1, 2, Suggest.NONE);
        this.logic.suggest(1, 2, Suggest.BOMB);
        this.logic.suggest(1, 2, Suggest.EMPTY);

        this.logic.suggest(1, 3, Suggest.EMPTY);
        this.logic.suggest(0, 5, Suggest.EMPTY);

        assertThat(this.logic.isWin(), is(true));
        assertThat(this.logic.isLose(), is(false));
    }

    /**
     * Test exception thrown when suggest after game over.
     */
    @Test(expected = LogicException.class)
    public void whenSuggestAndGameIsOverThenException() throws LogicException {
        this.logic.setBoard(new Cell[][]{
                {new BaseCell(Bomb.BOMB, 0),new BaseCell(Bomb.NONE, 1)}
        });

        this.logic.suggest(1, 0, Suggest.EMPTY);
        this.logic.suggest(0, 0, Suggest.BOMB);
    }

    /**
     * Test exception thrown when suggest with wrong coordinates.
     */
    @Test(expected = LogicException.class)
    public void whenSuggestIncorrectCoordinatesThenException() throws LogicException {
        this.initTestLogic();
        this.logic.suggest(3, 1, Suggest.EMPTY);
    }

    /**
     * Initialize test board used in several tests.
     */
    private void initTestLogic() throws LogicException {
        this.logic.setBoard(new Cell[][]{
                {new BaseCell(Bomb.BOMB, 0), new BaseCell(Bomb.BOMB, 0)},
                {new BaseCell(Bomb.NONE, 3), new BaseCell(Bomb.NONE, 3)},
                {new BaseCell(Bomb.BOMB, 0), new BaseCell(Bomb.NONE, 2)},
                {new BaseCell(Bomb.BOMB, 0), new BaseCell(Bomb.NONE, 2)},
                {new BaseCell(Bomb.NONE, 1), new BaseCell(Bomb.NONE, 1)},
                {new BaseCell(Bomb.NONE, 0), new BaseCell(Bomb.NONE, 0)}
        });
    }
}