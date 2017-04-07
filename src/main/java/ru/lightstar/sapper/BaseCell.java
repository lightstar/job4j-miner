package ru.lightstar.sapper;

/**
 * Base implementation of <code>Cell</code>.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class BaseCell implements Cell {

    /**
     * Type of bomb in this cell.
     */
    private final Bomb bomb;

    /**
     * Count of bombs near this cell.
     */
    private final int nearestBombCount;

    /**
     * Current user's suggest for this cell.
     */
    private Suggest suggest;

    /**
     * Constructs <code>BaseCell</code> object.
     *
     * @param bomb type of bomb in this cell.
     * @param nearestBombCount count of bombs near this cell.
     */
    public BaseCell(final Bomb bomb, final int nearestBombCount) {
        this.bomb = bomb;
        this.nearestBombCount = nearestBombCount;
        this.suggest = Suggest.NONE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bomb getBomb() {
        return this.bomb;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNearestBombCount() {
        return this.nearestBombCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Suggest getSuggest() {
        return this.suggest;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSuggest(final Suggest suggest) {
        this.suggest = suggest;
    }
}
