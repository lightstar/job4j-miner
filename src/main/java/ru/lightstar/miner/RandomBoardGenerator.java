package ru.lightstar.miner;

import ru.lightstar.miner.exception.LogicException;

import java.util.Random;

/**
 * Generator of random board with given width, height and bomb count.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class RandomBoardGenerator implements BoardGenerator {

    /**
     * Width of board.
     */
    private final int width;

    /**
     * Height of board.
     */
    private final int height;

    /**
     * Total bomb count on board.
     */
    private final int bombCount;

    /**
     * Constructs <code>RandomBoardGenerator</code> object.
     *
     * @param width board's width.
     * @param height board's height/
     * @param bombCount total bomb count on board.
     * @throws LogicException thrown if parameters are invalid.
     */
    public RandomBoardGenerator(final int width, final int height, final int bombCount) throws LogicException {
        if (width <= 0 || height <= 0 || bombCount <= 0) {
            throw new LogicException("Width, height and bombCount must be greater than zero");
        }

        if (bombCount > width * height) {
            throw new LogicException("Too big bomb count");
        }

        this.width = width;
        this.height = height;
        this.bombCount = bombCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cell[][] generate() {
        final boolean[][] bombLocations = this.generateBombLocations();
        final int[][] nearestBombCounts = this.generateNearestBombCounts(bombLocations);
        final Cell[][] cells = new Cell[this.height][];

        for (int y = 0; y < this.height; y++) {
            cells[y] = new Cell[this.width];
            for (int x = 0; x < this.width; x++) {
                cells[y][x] = this.generateCell(bombLocations[y][x], nearestBombCounts[y][x]);
            }
        }

        return cells;
    }

    /**
     * Generate cell object.
     * Override if you need to generate some specific cell object.
     *
     * @param bomb flag of bomb in this cell.
     * @param nearestBombCount count of bombs near this cell.
     * @return generated cell object.
     */
    protected Cell generateCell(final boolean bomb, final int nearestBombCount) {
        return new BaseCell(bomb ? Bomb.BOMB : Bomb.NONE, nearestBombCount);
    }

    /**
     * Generate array of random bomb locations.
     *
     * @return array of bomb locations.
     */
    private boolean[][] generateBombLocations() {
        final boolean[][] bombLocations = new boolean[this.height][];
        for (int y = 0; y < this.height; y++) {
            bombLocations[y] = new boolean[this.width];
        }

        final Random random = new Random();
        for (int i = 0; i < this.bombCount; i++) {
            final int index = random.nextInt(this.height * this.width);
            final int y = index / this.width;
            final int x = index - y * this.width;
            if (bombLocations[y][x]) {
                i--;
            } else {
                bombLocations[y][x] = true;
            }
        }

        return bombLocations;
    }

    /**
     * Generate array of nearest bombs counts.
     *
     * @param bombLocations array with bomb locations.
     * @return array of nearest bombs counts.
     */
    private int[][] generateNearestBombCounts(final boolean[][] bombLocations) {
        final int[][] nearestBombCounts = new int[this.height][];

        for (int y = 0; y < this.height; y++) {
            nearestBombCounts[y] = new int[this.width];
            for (int x = 0; x < this.width; x++) {
                if (bombLocations[y][x]) {
                    continue;
                }
                nearestBombCounts[y][x] = this.getNearestBombCount(x, y, bombLocations);
            }
        }

        return nearestBombCounts;
    }

    /**
     * Calculate nearest bombs count for given location.
     *
     * @param x 'x' coordinate of given location.
     * @param y 'y' coordinate of given location.
     * @param bombLocations array with bomb locations.
     * @return count of nearest bombs.
     */
    private int getNearestBombCount(final int x, final int y, final boolean[][] bombLocations) {
        int count = 0;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (this.isBombOnLocation(x + dx, y + dy, bombLocations)) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * Check if bomb is on given location.
     *
     * @param x 'x' coordinate of given location.
     * @param y 'y' coordinate of given location.
     * @param bombLocations array with bomb locations.
     * @return <code>true</code> if bomb is on location or <code>false</code> otherwise.
     */
    private boolean isBombOnLocation(final int x, final int y, final boolean[][] bombLocations) {
        return y >= 0 && y < this.height && x >= 0 && x < this.width && bombLocations[y][x];
    }
}
