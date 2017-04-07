package ru.lightstar.sapper;

/**
 * Interface for Sapper cell model.
 *
 * @author LightStar
 * @since 0.0.1
 */
public interface Cell {

    /**
     * Get bomb type in cell.
     *
     * @return bomb type.
     */
    Bomb getBomb();

    /**
     * Get nearest bombs count.
     *
     * @return nearest bombs count.
     */
    int getNearestBombCount();

    /**
     * Get current user suggest for this cell.
     *
     * @return user's suggest.
     */
    Suggest getSuggest();

    /**
     * Set current user suggest for this cell.
     *
     * @param suggest user's suggest.
     */
    void setSuggest(Suggest suggest);
}
