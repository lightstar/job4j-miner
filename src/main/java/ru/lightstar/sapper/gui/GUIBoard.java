package ru.lightstar.sapper.gui;

import ru.lightstar.sapper.Board;
import ru.lightstar.sapper.Bomb;
import ru.lightstar.sapper.Cell;
import ru.lightstar.sapper.Suggest;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

/**
 * Game board GUI component.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class GUIBoard extends JPanel implements Board {

    /**
     * Board cells.
     */
    private Cell[][] cells;

    /**
     * Flag to draw board in its unfolded state.
     */
    private boolean isUnfold;

    /**
     * Color for grid drawing.
     */
    private final Color gridColor;

    /**
     * Stroke for grid drawing.
     */
    private final Stroke gridStroke;

    /**
     * Color for drawing cell in 'hidden' state.
     */
    private final Color hiddenColor;

    /**
     * Stroke for drawing cell in 'hidden' state.
     */
    private final Stroke hiddenStroke;

    /**
     * Color for drawing cell with wrong suggest.
     */
    private final Color mistakeColor;

    /**
     * Stroke for drawing cell with wrong suggest.
     */
    private final Stroke mistakeStroke;

    /**
     * Color for drawing suggested bomb on cell.
     */
    private final Color suggestBombColor;

    /**
     * Stroke for drawing suggested bomb on cell.
     */
    private final Stroke suggestBombStroke;

    /**
     * Color for drawing bomb.
     */
    private final Color bombColor;

    /**
     * Color for drawing blown bomb.
     */
    private final Color blownBombColor;

    /**
     * Stroke for drawing bomb.
     */
    private final Stroke bombStroke;

    /**
     * Map of colors for nearest bomb counts.
     */
    private final Map<Integer, Color> nearestBombCountColors;

    /**
     * Font for nearest bomb counts.
     */
    private final Font nearestBombFont;

    /**
     * Constructs <code>GUIBoard</code> object.
     */
    public GUIBoard() {
        super();

        this.gridColor = Color.decode(GUIParams.CELL_GRID_COLOR);
        this.gridStroke = new BasicStroke(GUIParams.CELL_GRID_STROKE);

        this.hiddenColor = Color.decode(GUIParams.CELL_HIDDEN_COLOR);
        this.hiddenStroke = new BasicStroke(GUIParams.CELL_HIDDEN_STROKE);

        this.mistakeColor = Color.decode(GUIParams.CELL_MISTAKE_COLOR);
        this.mistakeStroke = new BasicStroke(GUIParams.CELL_MISTAKE_STROKE);

        this.suggestBombColor = Color.decode(GUIParams.CELL_SUGGEST_BOMB_COLOR);
        this.suggestBombStroke = new BasicStroke(GUIParams.CELL_SUGGEST_BOMB_STROKE);

        this.bombColor = Color.decode(GUIParams.CELL_BOMB_COLOR);
        this.blownBombColor = Color.decode(GUIParams.CELL_BLOWN_BOMB_COLOR);
        this.bombStroke = new BasicStroke(GUIParams.CELL_BOMB_STROKE);

        this.nearestBombCountColors = new HashMap<>();
        this.nearestBombCountColors.put(1, Color.decode(GUIParams.CELL_COUNT_COLOR_1));
        this.nearestBombCountColors.put(2, Color.decode(GUIParams.CELL_COUNT_COLOR_2));
        this.nearestBombCountColors.put(3, Color.decode(GUIParams.CELL_COUNT_COLOR_3));
        this.nearestBombCountColors.put(4, Color.decode(GUIParams.CELL_COUNT_COLOR_4));
        this.nearestBombCountColors.put(5, Color.decode(GUIParams.CELL_COUNT_COLOR_5));
        this.nearestBombCountColors.put(6, Color.decode(GUIParams.CELL_COUNT_COLOR_6));
        this.nearestBombCountColors.put(7, Color.decode(GUIParams.CELL_COUNT_COLOR_7));
        this.nearestBombCountColors.put(8, Color.decode(GUIParams.CELL_COUNT_COLOR_8));

        this.nearestBombFont =  new Font(GUIParams.CELL_FONT_NAME, Font.BOLD, GUIParams.CELL_FONT_SIZE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paint(final Graphics graphics) {
        super.paint(graphics);

        if (this.cells == null) {
            return;
        }

        this.setGraphicsParams((Graphics2D) graphics);

        for (int y = 0; y != this.cells.length; y++) {
            for (int x = 0; x != this.cells[0].length; x++) {
                graphics.setColor(this.gridColor);
                ((Graphics2D) graphics).setStroke(this.gridStroke);
                graphics.drawRect(x * GUIParams.CELL_SIZE, y * GUIParams.CELL_SIZE,
                        GUIParams.CELL_SIZE, GUIParams.CELL_SIZE);
                if (this.isUnfold) {
                    this.drawCellUnfold(graphics, this.cells[y][x], x, y);
                } else {
                    this.drawCellSuggest(graphics, this.cells[y][x], x, y);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBoard(final Cell[][] cells) {
        this.cells = cells;
        this.isUnfold = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawBoard() {
        this.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawLose() {
        this.isUnfold = true;
        this.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawWin() {
        this.isUnfold = true;
        this.repaint();
    }

    /**
     * Draw cell reflecting user suggest about it.
     *
     * @param graphics <code>Graphics</code> object where to draw on.
     * @param cell displayed cell.
     * @param x 'x' coordinate of displayed cell.
     * @param y 'y' coordinate of displayed cell.
     */
    private void drawCellSuggest(final Graphics graphics, final Cell cell, final int x, final int y) {
        switch(cell.getSuggest()) {
            case BOMB:
                this.drawSuggestBomb(graphics, cell, x, y);
                break;
            case EMPTY:
                this.drawEmpty(graphics, cell, x, y);
                break;
            case NONE:
                this.drawHidden(graphics, cell, x, y);
                break;
        }
    }

    /**
     * Draw cell with its real state (when game is over).
     *
     * @param graphics <code>Graphics</code> object where to draw on.
     * @param cell displayed cell.
     * @param x 'x' coordinate of displayed cell.
     * @param y 'y' coordinate of displayed cell.
     */
    private void drawCellUnfold(final Graphics graphics, final Cell cell, final int x, final int y) {
        if (cell.getBomb() != Bomb.NONE) {
            this.drawBomb(graphics, cell, x, y);
        } else {
            this.drawEmpty(graphics, cell, x, y);
        }
    }

    /**
     * Draw cell with bomb user suggest.
     *
     * @param graphics <code>Graphics</code> object where to draw on.
     * @param cell displayed cell.
     * @param x 'x' coordinate of displayed cell.
     * @param y 'y' coordinate of displayed cell.
     */
    protected void drawSuggestBomb(final Graphics graphics, final Cell cell, final int x, final int y) {
        final int pointX = x * GUIParams.CELL_SIZE;
        final int pointY = y * GUIParams.CELL_SIZE;
        final int size = GUIParams.CELL_SIZE;

        graphics.setColor(this.suggestBombColor);
        ((Graphics2D) graphics).setStroke(this.suggestBombStroke);
        graphics.drawLine(pointX + size / 4, pointY + 2 * size / 5,
                pointX + 2 * size / 5, pointY + size - 2 * size / 5);
        graphics.drawLine(pointX + 3 * size / 4, pointY + size / 5,
                pointX + 2 * size / 5, pointY + size - 2 * size / 5);
    }

    /**
     * Draw empty cell.
     *
     * @param graphics <code>Graphics</code> object where to draw on.
     * @param cell displayed cell.
     * @param x 'x' coordinate of displayed cell.
     * @param y 'y' coordinate of displayed cell.
     */
    protected void drawEmpty(final Graphics graphics, final Cell cell, final int x, final int y) {
        if (this.isUnfold && cell.getSuggest() == Suggest.BOMB) {
            this.drawBomb(graphics, cell, x, y);
            this.drawMistake(graphics, cell, x, y);
        } else if (cell.getNearestBombCount() > 0) {
            this.drawNearestBombCount(graphics, x, y, cell.getNearestBombCount());
        }
    }

    /**
     * Draw cell with bomb.
     *
     * @param graphics <code>Graphics</code> object where to draw on.
     * @param cell displayed cell.
     * @param x 'x' coordinate of displayed cell.
     * @param y 'y' coordinate of displayed cell.
     */
    protected void drawBomb(final Graphics graphics, final Cell cell, final int x, final int y) {
        final int pointX = x * GUIParams.CELL_SIZE;
        final int pointY = y * GUIParams.CELL_SIZE;
        final int size = GUIParams.CELL_SIZE;
        final int bombSize = GUIParams.CELL_BOMB_SIZE;

        graphics.setColor(cell.getSuggest() == Suggest.EMPTY ? this.blownBombColor : this.bombColor);
        ((Graphics2D) graphics).setStroke(this.bombStroke);
        graphics.fillArc(pointX + (size - bombSize) / 2, pointY + (size - bombSize) / 2, bombSize,
                bombSize, 0, 360);
    }

    /**
     * Draw cell which contents is hidden.
     *
     * @param graphics <code>Graphics</code> object where to draw on.
     * @param cell displayed cell.
     * @param x 'x' coordinate of displayed cell.
     * @param y 'y' coordinate of displayed cell.
     */
    protected void drawHidden(final Graphics graphics, final Cell cell, final int x, final int y) {
        final int pointX = x * GUIParams.CELL_SIZE;
        final int pointY = y * GUIParams.CELL_SIZE;
        final int size = GUIParams.CELL_SIZE;
        final int padding = GUIParams.CELL_HIDDEN_PADDING;

        graphics.setColor(this.hiddenColor);
        ((Graphics2D) graphics).setStroke(this.hiddenStroke);
        graphics.drawLine(pointX + padding, pointY + padding,
                pointX + size - padding, pointY + size - padding);
        graphics.drawLine(pointX + size - padding, pointY + padding,
                pointX + padding, pointY + size - padding);
    }

    /**
     * Draw cell with wrong suggest.
     *
     * @param graphics <code>Graphics</code> object where to draw on.
     * @param cell displayed cell.
     * @param x 'x' coordinate of displayed cell.
     * @param y 'y' coordinate of displayed cell.
     */
    protected void drawMistake(final Graphics graphics, final Cell cell, final int x, final int y) {
        final int pointX = x * GUIParams.CELL_SIZE;
        final int pointY = y * GUIParams.CELL_SIZE;
        final int size = GUIParams.CELL_SIZE;
        final int padding = GUIParams.CELL_MISTAKE_PADDING;

        graphics.setColor(this.mistakeColor);
        ((Graphics2D) graphics).setStroke(this.mistakeStroke);
        graphics.drawLine(pointX + padding, pointY + padding,
                pointX + size - padding, pointY + size - padding);
        graphics.drawLine(pointX + size - padding, pointY + padding,
                pointX + padding, pointY + size - padding);
    }

    /**
     * Draw nearest bomb count in cell's center.
     * Using technique from http://stackoverflow.com/questions/23729944/java-how-to-visually-center-a-specific-string-not-just-a-font-in-a-rectangle
     *
     * @param graphics <code>Graphics</code> object where to draw on.
     * @param x 'x' coordinate of displayed cell.
     * @param y 'y' coordinate of displayed cell.
     * @param count what number to draw.
     */
    private void drawNearestBombCount(final Graphics graphics, final int x, final int y, final int count) {
        graphics.setColor(this.nearestBombCountColors.get(count));
        graphics.setFont(this.nearestBombFont);

        final String countString = String.valueOf(count);
        final FontRenderContext frc = ((Graphics2D) graphics).getFontRenderContext();
        final GlyphVector gv = this.nearestBombFont.createGlyphVector(frc, countString);
        final Rectangle2D box = gv.getVisualBounds();
        final FontMetrics metrics = graphics.getFontMetrics();

        final int pointX = x * GUIParams.CELL_SIZE +
                ((GUIParams.CELL_SIZE - metrics.stringWidth(countString)) / 2);
        final int pointY = y * GUIParams.CELL_SIZE +
                (int) (((GUIParams.CELL_SIZE - box.getHeight()) / 2) - box.getY());

        graphics.drawString(countString, pointX, pointY);
    }

    /**
     * Set global graphics properties (such as antialiasing).
     *
     * @param graphics <code>Graphics</code> object which properties are set.
     */
    private void setGraphicsParams(final Graphics2D graphics) {
        graphics.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        graphics.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
    }
}
