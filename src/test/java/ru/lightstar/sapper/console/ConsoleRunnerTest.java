package ru.lightstar.sapper.console;

import org.junit.Test;
import ru.lightstar.sapper.*;
import ru.lightstar.sapper.io.ByteArrayOutput;
import ru.lightstar.sapper.io.IteratorInput;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * <code>ConsoleRunner</code> class tests.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class ConsoleRunnerTest {

    /**
     * Test correctness of runner's run.
     */
    @Test
    public void whenRunThenItRuns() {
        final IteratorInput input = new IteratorInput();
        final ByteArrayOutput output = new ByteArrayOutput();
        final ConsoleRunner runner = new ConsoleRunner(input, output);
        final IoTestHelper helper = new IoTestHelper();

        List<String> inputList = Arrays.asList(
                "2",
                "4",
                "3",

                "1",
                "1",
                "y",

                "1",
                "2",
                "n",

                "1",
                "3",
                "n",

                "1",
                "4",
                "y",

                "2",
                "2",
                "n",

                "2",
                "3",
                "n",

                "2",
                "4",
                "n"
        );
        input.setIterator(inputList.iterator());
        runner.run(TestBoardGenerator.class);

        assertThat(output.toString(), is(helper.joinLines(new String[]{
                "Board width:",
                "Board height:",
                "Bomb count:",

                "[X] [X] ",
                "[X] [X] ",
                "[X] [X] ",
                "[X] [X] ",
                "",

                "Bombs remained: 3",
                "x:",
                "y:",
                "Bomb [y/n]:",

                "[?] [X] ",
                "[X] [X] ",
                "[X] [X] ",
                "[X] [X] ",
                "",

                "Bombs remained: 2",
                "x:",
                "y:",
                "Bomb [y/n]:",

                "[?] [X] ",
                "[2] [X] ",
                "[X] [X] ",
                "[X] [X] ",
                "",

                "Bombs remained: 2",
                "x:",
                "y:",
                "Bomb [y/n]:",

                "[?] [X] ",
                "[2] [X] ",
                "[1] [X] ",
                "[X] [X] ",
                "",

                "Bombs remained: 2",
                "x:",
                "y:",
                "Bomb [y/n]:",

                "[?] [X] ",
                "[2] [X] ",
                "[1] [X] ",
                "[?] [X] ",
                "",

                "Bombs remained: 1",
                "x:",
                "y:",
                "Bomb [y/n]:",

                "[?] [X] ",
                "[2] [2] ",
                "[1] [X] ",
                "[?] [X] ",
                "",

                "Bombs remained: 1",
                "x:",
                "y:",
                "Bomb [y/n]:",

                "[?] [X] ",
                "[2] [2] ",
                "[1] [1] ",
                "[?] [X] ",
                "",

                "Bombs remained: 1",
                "x:",
                "y:",
                "Bomb [y/n]:",

                "***** CONGRATULATE *****",
                "[*] [*] ",
                "[2] [2] ",
                "[1] [1] ",
                "[*] [1] ",
                ""
        })));
    }

    /**
     * Test board generator.
     */
    private static class TestBoardGenerator implements BoardGenerator {

        /**
         * Constructs <code>TestBoardGenerator</code> object.
         */
        public TestBoardGenerator() {
            super();
        }

        /**
         * Generate test board cells.
         *
         * @param width test width.
         * @param height test height.
         * @param bombCount test bomb count.
         * @return generated cells.
         */
        @Override
        public Cell[][] generate(final int width, final int height, final int bombCount) {
            if (width != 2 || height != 4 || bombCount != 3) {
                throw new IllegalArgumentException("Test parameters are wrong");
            }
            return new Cell[][]{
                    {new BaseCell(Bomb.BOMB, 0), new BaseCell(Bomb.BOMB, 0)},
                    {new BaseCell(Bomb.NONE, 2), new BaseCell(Bomb.NONE, 2)},
                    {new BaseCell(Bomb.NONE, 1), new BaseCell(Bomb.NONE, 1)},
                    {new BaseCell(Bomb.BOMB, 0), new BaseCell(Bomb.NONE, 1)}
            };
        }
    }
}