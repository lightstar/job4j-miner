package ru.lightstar.miner;

import org.junit.Test;
import ru.lightstar.miner.exception.LogicException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * <code>RandomBoardGenerator</code> class tests.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class RandomBoardGeneratorTest {

    /**
     * Test board generation with correct params.
     */
    @Test
    public void whenGenerateThenResult() throws LogicException {
        final RandomBoardGenerator generator = new RandomBoardGenerator(4, 2, 3);
        final Cell[][] cells = generator.generate();

        int bombCount = 0;
        assertThat(cells.length, is(2));
        for (int y = 0; y < 2; y++) {
            assertThat(cells[y].length, is(4));
            for (int x = 0; x < 4; x++) {
                if (cells[y][x].getBomb() != Bomb.NONE) {
                    bombCount++;
                }
            }
        }

        assertThat(bombCount, is(3));
    }

    /**
     * Test exception thrown on attempt to generate board with too many bombs.
     */
    @Test(expected = LogicException.class)
    public void whenTooManyBombsThenException() throws LogicException {
        new RandomBoardGenerator(4, 2, 10);
    }

    /**
     * Test exception thrown on attempt to generate board with zero dimensions.
     */
    @Test(expected = LogicException.class)
    public void whenZeroDimensionsThenException() throws LogicException {
        new RandomBoardGenerator(0, 2, 1);
    }

    /**
     * Test exception thrown on attempt to generate board with zero bombs.
     */
    @Test(expected = LogicException.class)
    public void whenZeroBombsThenException() throws LogicException {
        new RandomBoardGenerator(4, 2, 0);
    }
}
