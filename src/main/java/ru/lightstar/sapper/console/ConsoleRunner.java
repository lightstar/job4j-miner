package ru.lightstar.sapper.console;

import ru.lightstar.sapper.*;
import ru.lightstar.sapper.exception.GenerateException;
import ru.lightstar.sapper.exception.LogicException;
import ru.lightstar.sapper.io.Input;
import ru.lightstar.sapper.io.Output;

/**
 * Console runner for Sapper game.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class ConsoleRunner {

    /**
     * <code>Input</code> object used for user input.
     */
    private final Input input;

    /**
     * <code>Output</code> object used for user output.
     */
    private final Output output;

    /**
     * Game logic.
     */
    private Logic logic;

    /**
     * Game controller.
     */
    private Controller controller;

    /**
     * Constructs <code>ConsoleRunner</code> object.
     *
     * @param input <code>Input</code> object.
     * @param output <code>Output</code> object.
     */
    public ConsoleRunner(final Input input, final Output output) {
        this.input = input;
        this.output = output;
    }

    /**
     * Run sapper game.
     *
     * @param generatorClass board generator class. It must have 3 integer parameters: width, height and bomb count.
     */
    public void run(final Class<? extends BoardGenerator> generatorClass) {
        try {
            this.init(generatorClass);
            this.play();
        } catch(GenerateException | LogicException e) {
            this.output.println(String.format("%s.", e.getMessage()));
        }
    }

    /**
     * Init game.
     *
     *
     * @param generatorClass board generator class. It must have 3 integer parameters: width, height and bomb count.
     * @throws GenerateException thrown when there is error in user input.
     * @throws LogicException thrown when there is error in generated board.
     */
    private void init(final Class<? extends BoardGenerator> generatorClass) throws GenerateException, LogicException {
        final int width = this.input.askNumber(this.output, "Board width:");
        final int height = this.input.askNumber(this.output, "Board height:");
        final int bombCount = this.input.askNumber(this.output, "Bomb count:");

        final BoardGenerator generator;
        try {
            generator = generatorClass.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Wrong board generator class. It must have default constructor.");
        }

        final ConsoleBoard board = new ConsoleBoard(this.output);
        this.logic = new BaseLogic();
        this.controller = new BaseController(this.logic, board, generator);
        this.controller.init(width, height, bombCount);
    }

    /**
     * Play game.
     */
    private void play() {
        while (!this.logic.isWin() && !this.logic.isLose()) {
            this.output.println(String.format("Bombs remained: %d",
                    this.logic.getBombCount() - this.logic.getSuggestedBombCount()));
            final int x = this.input.askNumber(this.output, "x:") - 1;
            final int y = this.input.askNumber(this.output, "y:") - 1;
            final String isBomb = this.input.ask(this.output, "Bomb [y/n]:").toLowerCase();
            try {
                this.controller.suggest(x, y, isBomb.equals("y") ? Suggest.BOMB : Suggest.EMPTY);
            } catch (LogicException e) {
                this.output.println(String.format("%s.", e.getMessage()));
            }
        }
    }
}
