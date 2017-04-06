package ru.lightstar.miner.console;

import org.junit.Test;
import ru.lightstar.miner.*;
import ru.lightstar.miner.io.ByteArrayOutput;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * <code>ConsoleBoard</code> class tests.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class ConsoleBoardTest {

    /**
     * Board cells used in tests.
     */
    private final Cell[][] cells;

    /**
     * <code>ConsoleBoard</code> object used in tests.
     */
    private final ConsoleBoard board;

    /**
     * <code>ByteArrayOutput</code> object used to intercept output in tests.
     */
    private final ByteArrayOutput output = new ByteArrayOutput();

    /**
     * Helper object used in I/O tests.
     */
    private final IoTestHelper helper;

    /**
     * Constructs <code>ConsoleBoardTest</code> object.
     */
    public ConsoleBoardTest() {
        this.board = new ConsoleBoard();
        this.cells = new Cell[][]{
                {new BaseCell(Bomb.BOMB, 0), new BaseCell(Bomb.BOMB, 0)},
                {new BaseCell(Bomb.NONE, 3), new BaseCell(Bomb.NONE, 3)},
                {new BaseCell(Bomb.BOMB, 0), new BaseCell(Bomb.NONE, 1)},
                {new BaseCell(Bomb.NONE, 1), new BaseCell(Bomb.NONE, 1)},
                {new BaseCell(Bomb.NONE, 0), new BaseCell(Bomb.NONE, 0)},
        };
        this.board.setOutput(this.output);
        this.board.setBoard(this.cells);
        this.helper = new IoTestHelper();
    }

    /**
     * Test drawing of initially hidden board.
     */
    @Test
    public void whenDrawHiddenBoardThenResult() {
        this.board.drawBoard();

        assertThat(this.output.toString(), is(this.helper.joinLines(new String[]{
            "[X] [X] ",
            "[X] [X] ",
            "[X] [X] ",
            "[X] [X] ",
            "[X] [X] ",
            ""
        })));
    }

    /**
     * Test drawing of partially suggested board.
     */
    @Test
    public void whenDrawPartiallyOpenBoardThenResult() {
        this.cells[0][0].setSuggest(Suggest.BOMB);
        this.cells[0][1].setSuggest(Suggest.BOMB);
        this.cells[1][0].setSuggest(Suggest.EMPTY);
        this.cells[2][1].setSuggest(Suggest.EMPTY);
        this.cells[4][0].setSuggest(Suggest.EMPTY);
        this.board.drawBoard();

        assertThat(this.output.toString(), is(this.helper.joinLines(new String[]{
                "[?] [?] ",
                "[3] [X] ",
                "[X] [1] ",
                "[X] [X] ",
                "[ ] [X] ",
                ""
        })));
    }

    /**
     * Test drawing one cell.
     */
    @Test
    public void whenDrawCellThenRedrawAll() {
        this.cells[0][0].setSuggest(Suggest.BOMB);
        this.cells[0][1].setSuggest(Suggest.BOMB);
        this.board.drawCell(0, 0);

        assertThat(this.output.toString(), is(this.helper.joinLines(new String[]{
                "***** SELECT *****",
                "[?] [?] ",
                "[X] [X] ",
                "[X] [X] ",
                "[X] [X] ",
                "[X] [X] ",
                ""
        })));
    }

    /**
     * Test drawing on user lose.
     */
    @Test
    public void whenDrawLoseThenResult() {
        this.cells[0][0].setSuggest(Suggest.BOMB);
        this.cells[0][1].setSuggest(Suggest.BOMB);
        this.board.drawLose();

        assertThat(this.output.toString(), is(this.helper.joinLines(new String[]{
                "***** BANG *****",
                "[*] [*] ",
                "[3] [3] ",
                "[*] [1] ",
                "[1] [1] ",
                "[ ] [ ] ",
                ""
        })));
    }

    /**
     * Test drawing on user win.
     */
    @Test
    public void whenDrawWinThenResult() {
        this.cells[0][0].setSuggest(Suggest.BOMB);
        this.cells[0][1].setSuggest(Suggest.BOMB);
        this.board.drawWin();

        assertThat(this.output.toString(), is(this.helper.joinLines(new String[]{
                "***** CONGRATULATE *****",
                "[*] [*] ",
                "[3] [3] ",
                "[*] [1] ",
                "[1] [1] ",
                "[ ] [ ] ",
                ""
        })));
    }
}