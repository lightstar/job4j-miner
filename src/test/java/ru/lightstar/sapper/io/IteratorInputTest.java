package ru.lightstar.sapper.io;

import org.junit.Test;
import ru.lightstar.sapper.IoTestHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * <code>IteratorInput</code> class tests.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class IteratorInputTest {

    /**
     * Test correctness of <code>next</code> method.
     */
    @Test
    public void whenNextThenResult() {
        final List<String> input = Arrays.asList("Hello", "World!");
        final IteratorInput iteratorInput = new IteratorInput(input.iterator());

        assertThat(iteratorInput.next(), is("Hello"));
        assertThat(iteratorInput.next(), is("World!"));
    }

    /**
     * Test correctness of <code>setIterator</code> method.
     */
    @Test
    public void whenSetIteratorThenResult() {
        final IteratorInput iteratorInput = new IteratorInput();
        final List<String> input = Arrays.asList("Hello", "World!");

        iteratorInput.setIterator(input.iterator());

        assertThat(iteratorInput.next(), is("Hello"));
        assertThat(iteratorInput.next(), is("World!"));
    }

    /**
     * Test correctness of <code>ask</code> method.
     */
    @Test
    public void whenAskThenResult() {
        final List<String> input = Collections.singletonList("Hello");
        final IteratorInput iteratorInput = new IteratorInput(input.iterator());
        final ByteArrayOutput byteArrayOutput = new ByteArrayOutput();

        final String resultHello = iteratorInput.ask(byteArrayOutput, "Hi!");

        assertThat(resultHello, is("Hello"));
        assertThat(byteArrayOutput.toString(), is(String.format("Hi!%n")));
    }

    /**
     * Test correctness of <code>askNumber</code> method.
     */
    @Test
    public void whenAskNumberThenResult() {
        final List<String> input = Collections.singletonList("5");
        final IteratorInput iteratorInput = new IteratorInput(input.iterator());
        final ByteArrayOutput byteArrayOutput = new ByteArrayOutput();

        final int result = iteratorInput.askNumber(byteArrayOutput, "Hi!");

        assertThat(result, is(5));
        assertThat(byteArrayOutput.toString(), is(String.format("Hi!%n")));
    }

    /**
     * Test correctness of <code>askNumber</code> method when input is not a number.
     */
    @Test
    public void whenAskNumberAndWrongInputThenRepeatQuestion() {
        final List<String> input = Arrays.asList("hello", "5");
        final IteratorInput iteratorInput = new IteratorInput(input.iterator());
        final ByteArrayOutput byteArrayOutput = new ByteArrayOutput();

        final int result = iteratorInput.askNumber(byteArrayOutput, "Hi!");

        assertThat(result, is(5));
        assertThat(byteArrayOutput.toString(), is(new IoTestHelper().joinLines(new String[]{
                "Hi!",
                "Not a number.",
                "Hi!"
        })));
    }
}
