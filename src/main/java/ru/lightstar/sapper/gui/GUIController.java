package ru.lightstar.sapper.gui;

import ru.lightstar.sapper.*;
import ru.lightstar.sapper.exception.GenerateException;
import ru.lightstar.sapper.exception.LogicException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Game GUI controller which can start/finish game and process user input.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class GUIController extends BaseController implements ActionListener, MouseListener {

    /**
     * Main frame.
     */
    private final JFrame frame;

    /**
     * Label with game information.
     */
    private final JLabel infoLabel;

    /**
     * Current board's width.
     */
    private int width;

    /**
     * Current board's height.
     */
    private int height;

    /**
     * Current total bombs on board.
     */
    private int bombCount;

    /**
     * Constructs <code>GUIController</code> object.
     *
     * @param logic game logic.
     * @param board game board.
     * @param generator game board generator.
     * @param frame main frame.
     * @param infoLabel label with game information.
     */
    public GUIController(final Logic logic, final GUIBoard board, final BoardGenerator generator,
                         final JFrame frame, final JLabel infoLabel) {
        super(logic, board, generator);

        this.frame = frame;
        this.infoLabel = infoLabel;

        board.addMouseListener(this);
    }

    /**
     * Run game. It runs in 'Easy' difficulty mode by default but that can be changed by user later.
     */
    public void run() {
        this.begin(GUIParams.EASY_BOARD_WIDTH, GUIParams.EASY_BOARD_HEIGHT, GUIParams.EASY_BOARD_BOMBS);
    }

    /**
     * Buttons press handler.
     *
     * @param event incoming action event.
     */
    public void actionPerformed(final ActionEvent event) {
        switch (event.getActionCommand()) {
            case "easy":
                this.begin(GUIParams.EASY_BOARD_WIDTH, GUIParams.EASY_BOARD_HEIGHT, GUIParams.EASY_BOARD_BOMBS);
                break;
            case "medium":
                this.begin(GUIParams.MEDIUM_BOARD_WIDTH, GUIParams.MEDIUM_BOARD_HEIGHT, GUIParams.MEDIUM_BOARD_BOMBS);
                break;
            case "hard":
                this.begin(GUIParams.HARD_BOARD_WIDTH, GUIParams.HARD_BOARD_HEIGHT, GUIParams.HARD_BOARD_BOMBS);
                break;
            case "custom":
                this.beginCustom();
                break;
            }
    }

    /**
     * Mouse click handler. Processing user's suggest here.
     *
     * @param event incoming mouse event.
     */
    public void mouseClicked(final MouseEvent event) {
        if (this.getLogic().isLose() || this.getLogic().isWin()) {
            return;
        }

        final int x = event.getX() / GUIParams.CELL_SIZE;
        final int y = event.getY() / GUIParams.CELL_SIZE;

        try {
            if (event.getButton() == MouseEvent.BUTTON1) {
                this.suggest(x, y, Suggest.EMPTY);
            } else if (event.getButton() == MouseEvent.BUTTON3) {
                this.suggest(x, y, Suggest.BOMB);
            }
        } catch (LogicException e) {
            this.showMessage(e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Mouse press handler. Ignored.
     *
     * @param event incoming mouse event.
     */
    public void mousePressed(final MouseEvent event) {
    }

    /**
     * Mouse release handler. Ignored.
     *
     * @param event incoming mouse event.
     */
    public void mouseReleased(final MouseEvent event) {
    }

    /**
     * Mouse enter handler. Ignored.
     *
     * @param event incoming mouse event.
     */
    public void mouseEntered(final MouseEvent event) {
    }

    /**
     * Mouse exit handler. Ignored.
     *
     * @param event incoming mouse event.
     */
    public void mouseExited(final MouseEvent event) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void suggest(int x, int y, Suggest suggest) throws LogicException {
        super.suggest(x, y, suggest);

        if (this.getLogic().isLose()) {
            this.showMessage("You lose", "Sorry", JOptionPane.INFORMATION_MESSAGE);
        } else if (this.getLogic().isWin()) {
            this.showMessage("You win", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
        }

        this.showRemainedBombs();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GUIBoard getBoard() {
        return (GUIBoard) super.getBoard();
    }

    /**
     * Set parameters and begin game.
     *
     * @param width board's width.
     * @param height board's height.
     * @param bombCount total bombs on game board.
     */
    private void begin(final int width, final int height, final int bombCount) {
        this.width = width;
        this.height = height;
        this.bombCount = bombCount;
        this.begin();
    }

    /**
     * Ask parameters from user and begin game.
     */
    private void beginCustom() {
        this.width = this.askNumber(String.format("Board's width (%d-%d): ",
                GUIParams.MIN_BOARD_WIDTH, GUIParams.MAX_BOARD_WIDTH),
                "Board's width", GUIParams.MIN_BOARD_WIDTH, GUIParams.MAX_BOARD_WIDTH);

        this.height = this.askNumber(String.format("Board's height (%d-%d): ",
                GUIParams.MIN_BOARD_HEIGHT, GUIParams.MAX_BOARD_HEIGHT),
                "Board's width", GUIParams.MIN_BOARD_HEIGHT, GUIParams.MAX_BOARD_HEIGHT);

        this.bombCount = this.askNumber(String.format("Bombs on board (%d-%d): ",
                1, this.width * this.height),
                "Board's width",1, this.width * this.height);

        this.begin();
    }

    /**
     * Begin game with already set parameters.
     */
    private void begin() {
        try {
            this.infoLabel.setText(" ");
            this.resizeFrame();
            this.init(this.width, this.height, this.bombCount);
            this.showRemainedBombs();
        } catch (GenerateException | LogicException e) {
            this.showMessage(e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Show remained bombs on info label.
     */
    private void showRemainedBombs() {
        this.infoLabel.setText(String.format("Bombs remained: %d",
                this.getLogic().getBombCount() - this.getLogic().getSuggestedBombCount()));
    }

    /**
     * Show message box.
     *
     * @param message message.
     * @param title title.
     * @param type message's type.
     */
    private void showMessage(final String message, final String title, final int type) {
        JOptionPane.showMessageDialog(this.frame, message, title, type);
    }

    /**
     * Resize main frame to reflect board size.
     */
    private void resizeFrame() {

        this.getBoard().setPreferredSize(new Dimension(GUIParams.CELL_SIZE * this.width + 2,
                GUIParams.CELL_SIZE * this.height + 2));
        this.frame.setResizable(true);
        this.frame.pack();
        this.frame.setResizable(false);
        this.frame.setLocationRelativeTo(null);
    }

    /**
     * Ask user for some number in given interval.
     *
     * @param message message for user.
     * @param title title of input dialog.
     * @param min minimum value of the user's answer.
     * @param max maximum value of the user's answer.
     * @return user's answer.
     */
    private int askNumber(final String message, final String title, final int min, final int max) {
        while (true) {
            try {
                final int answer = Integer.valueOf((String) JOptionPane.showInputDialog(
                        this.frame, message, title, JOptionPane.QUESTION_MESSAGE,
                        null, null, null));
                if (answer < min || answer > max) {
                    throw new NumberFormatException();
                }
                return answer;
            } catch (NumberFormatException e) {
                this.showMessage("Wrong input. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
