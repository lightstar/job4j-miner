package ru.lightstar.sapper;

import ru.lightstar.sapper.exception.GenerateException;

import java.util.ArrayList;
import java.util.List;
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
    private int width;

    /**
     * Height of board.
     */
    private int height;

    /**
     * Total bomb count on board.
     */
    private int bombCount;

    /**
     * {@inheritDoc}
     */
    @Override
    public Cell[][] generate(final int width, final int height, final int bombCount) throws GenerateException {
        this.checkParams(width, height, bombCount);

        this.width = width;
        this.height = height;
        this.bombCount = bombCount;

        return this.generate();
    }

    /**
     * Generate board after all params are set.
     *
     * @return generated board's cells.
     */
    protected Cell[][] generate() {
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
        final List<Location> remainedLocations = new ArrayList<>(this.height * this.width);
        final boolean[][] bombLocations = new boolean[this.height][];
        for (int y = 0; y < this.height; y++) {
            bombLocations[y] = new boolean[this.width];
            for (int x = 0; x < this.width; x++) {
                remainedLocations.add(new Location(x, y));
            }
        }

        final Random random = new Random();
        for (int i = 0; i < this.bombCount; i++) {
            final int index = random.nextInt(remainedLocations.size());
            final Location location = remainedLocations.get(index);
            bombLocations[location.getY()][location.getX()] = true;
            remainedLocations.remove(index);
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

    /**
     * Check generate params.
     *
     * @param width board's width.
     * @param height board's height.
     * @param bombCount total bombs on board.
     * @throws GenerateException thrown if params are invalid.
     */
    private void checkParams(final int width, final int height, final int bombCount) throws GenerateException {
        if (width <= 0 || height <= 0 || bombCount <= 0) {
            throw new GenerateException("Width, height and bombCount must be greater than zero");
        }

        if (bombCount > width * height) {
            throw new GenerateException("Too big bomb count");
        }
    }

    /**
     * Some cell location.
     */
    private static class Location {

        /**
         * 'x' coordinate of the location.
         */
        private final int x;

        /**
         * 'y' coordinate of the location.
         */
        private final int y;

        /**
         * Constructs <code>Location</code> object
         *
         * @param x 'x' coordinate of the location.
         * @param y 'y' coordinate of the location.
         */
        public Location(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Get 'x' coordinate of the location.
         *
         * @return 'x' coordinate of the location.
         */
        public int getX() {
            return x;
        }

        /**
         * Get 'y' coordinate of the location.
         *
         * @return 'y' coordinate of the location.
         */
        public int getY() {
            return y;
        }
    }
}
