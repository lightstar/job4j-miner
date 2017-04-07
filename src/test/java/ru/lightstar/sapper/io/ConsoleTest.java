package ru.lightstar.sapper.io;

import org.junit.Test;
import ru.lightstar.sapper.IoTestHelper;

import java.io.OutputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * <code>Console</code> class tests.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class ConsoleTest {

    /**
     * Helper object used for I/O.
     */
    private final IoTestHelper helper;

    /**
     * Constructs <code>ConsoleTest</code> object.
     */
    public ConsoleTest() {
        super();
        this.helper = new IoTestHelper();
    }

    /**
     * Test correctness of <code>println</code> method.
     */
    @Test
    public void whenPrintlnThenResult() {
        final OutputStream mockedOutput = this.helper.mockStandardOutput();
        final Console console = new Console();

        console.println("Hello, world!");

        assertThat(mockedOutput.toString(), is(String.format("Hello, world!%n")));
    }

    /**
     * Test correctness of <code>print</code> method.
     */
    @Test
    public void whenPrintThenResult() {
        final OutputStream mockedOutput = this.helper.mockStandardOutput();
        final Console console = new Console();

        console.print("Hello, world!");

        assertThat(mockedOutput.toString(), is("Hello, world!"));
    }

    /**
     * Test correctness of <code>next</code> method.
     */
    @Test
    public void whenNextThenResult() {
        this.helper.mockStandardInput(String.format("Hello!%n").getBytes());

        final Console console = new Console();
        final String result = console.next();

        assertThat(result, is("Hello!"));
    }

    /**
     * Test correctness of <code>ask</code> method.
     */
    @Test
    public void whenAskThenResult() {
        this.helper.mockStandardInput(String.format("Boris%n").getBytes());
        final OutputStream mockedOutput = this.helper.mockStandardOutput();

        final Console console = new Console();
        final String result = console.ask(console, "What is your name?");

        assertThat(mockedOutput.toString(), is(String.format("What is your name?%n")));
        assertThat(result, is("Boris"));
    }

    /**
     * Test correctness of <code>ask</code> method.
     */
    @Test
    public void whenAskNumberThenResult() {
        this.helper.mockStandardInput(String.format("5%n").getBytes());
        final OutputStream mockedOutput = this.helper.mockStandardOutput();

        final Console console = new Console();
        final int result = console.askNumber(console, "What number?");

        assertThat(mockedOutput.toString(), is(String.format("What number?%n")));
        assertThat(result, is(5));
    }

    /**
     * Test correctness of <code>ask</code> method.
     */
    @Test
    public void whenAskNumberAndWrongInputThenRepeatQuestion() {
        this.helper.mockStandardInput(this.helper.joinLines(new String[]{
                "hello",
                "5"
        }).getBytes());
        final OutputStream mockedOutput = this.helper.mockStandardOutput();

        final Console console = new Console();
        final int result = console.askNumber(console, "What number?");

        assertThat(mockedOutput.toString(), is(helper.joinLines(new String[]{
                "What number?",
                "Not a number.",
                "What number?"
        })));
        assertThat(result, is(5));
    }
}
