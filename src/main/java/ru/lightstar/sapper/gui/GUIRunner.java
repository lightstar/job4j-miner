package ru.lightstar.sapper.gui;

import ru.lightstar.sapper.BaseLogic;
import ru.lightstar.sapper.BoardGenerator;
import ru.lightstar.sapper.Logic;

import javax.swing.*;
import java.awt.*;

/**
 * GUI runner for sapper game.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class GUIRunner {

    /**
     * Main frame.
     */
    private JFrame frame;

    /**
     * Label with game information.
     */
    private JLabel infoLabel;

    /**
     * Game controller.
     */
    private GUIController controller;

    /**
     * Run this runner.
     *
     * @param generatorClass board generator's interface implementation. It must have default constructor.
     */
    public void run(final Class<? extends BoardGenerator> generatorClass) {
        EventQueue.invokeLater(() -> {
            this.initFrame();
            this.initInfoPanel();
            this.initController(generatorClass);
            this.initControlPanel();
            this.controller.run();
            this.showFrame();
        });
    }

    /**
     * Create and initialize main frame.
     */
    private void initFrame() {
        this.frame = new JFrame();
        this.frame.setTitle("Sapper");
        this.frame.setLayout(new BorderLayout());
        this.frame.setResizable(false);
        this.frame.setIconImage(new ImageIcon(this.getClass().getResource("/bomb.png")).getImage());
    }

    /**
     * Create and initialize game controller.
     *
     * @param generatorClass board generator's interface implementation. It must have default constructor.
     */
    private void initController(final Class<? extends BoardGenerator> generatorClass) {
        final BoardGenerator generator;
        try {
            generator = generatorClass.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Wrong board generator class. It must have default constructor.");
        }

        final GUIBoard board = new GUIBoard();
        final Logic logic = new BaseLogic();
        this.controller = new GUIController(logic, board, generator, this.frame, this.infoLabel);

        this.frame.add(board, BorderLayout.CENTER);
    }

    /**
     * Create and initialize game information panel.
     */
    private void initInfoPanel() {
        final JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout());
        this.infoLabel = new JLabel(" ");
        infoPanel.add(this.infoLabel);
        this.frame.add(infoPanel, BorderLayout.PAGE_START);
    }

    /**
     * Create and initialize panel with buttons.
     */
    private void initControlPanel() {
        final JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        this.addControlButton(controlPanel, "Easy", "easy");
        this.addControlButton(controlPanel, "Medium", "medium");
        this.addControlButton(controlPanel, "Hard", "hard");
        this.addControlButton(controlPanel, "Custom", "custom");

        this.frame.add(controlPanel, BorderLayout.PAGE_END);
    }

    /**
     * Add button to control panel.
     *
     * @param controlPanel control panel.
     * @param label button's label.
     * @param command button's command.
     */
    private void addControlButton(final JPanel controlPanel, final String label, final String command) {
        final JButton button = new JButton(label);
        button.setActionCommand(command);
        button.addActionListener(this.controller);
        controlPanel.add(button);
    }

    /**
     * Show main frame.
     */
    private void showFrame() {
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }
}
