package ru.lightstar.sapper;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * <code>BaseCell</code> class tests.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class BaseCellTest {

    /**
     * <code>BaseCell</code> object used in tests.
     */
    private final BaseCell baseCell;

    /**
     * Constructs <code>BaseCellTest</code> object.
     */
    public BaseCellTest() {
        this.baseCell = new BaseCell(Bomb.BOMB, 2);
    }

    /**
     * Test correctness of <code>getBomb</code> method.
     */
    @Test
    public void whenGetBombThenResult() {
        assertThat(this.baseCell.getBomb(), is(Bomb.BOMB));
    }

    /**
     * Test correctness of <code>getNearestBombCount</code> method.
     */
    @Test
    public void whenGetNearestBombCountThenResult() {
        assertThat(this.baseCell.getNearestBombCount(), is(2));
    }

    /**
     * Test correctness of <code>getSuggest</code> method.
     */
    @Test
    public void whenGetSuggestThenResult() {
        assertThat(this.baseCell.getSuggest(), is(Suggest.NONE));
    }

    /**
     * Test correctness of <code>setSuggest</code> count.
     */
    @Test
    public void whenSetSuggestThenItSets() {
        this.baseCell.setSuggest(Suggest.EMPTY);
        assertThat(this.baseCell.getSuggest(), is(Suggest.EMPTY));
    }
}
