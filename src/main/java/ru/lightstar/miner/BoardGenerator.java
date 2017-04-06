package ru.lightstar.miner;

/**
 * Interface for board generator.
 *
 * @author LightStar
 * @since 0.0.1
 */
public interface BoardGenerator {

    /**
     * Generate board cells.
     *
     * @return generated cells.
     */
    Cell[][] generate();
}
