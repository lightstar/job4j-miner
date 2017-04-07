package ru.lightstar.sapper.io;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * <code>ByteArrayOutput</code> class tests.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class ByteArrayOutputTest {

    /**
     * Test correctness of <code>println</code> method.
     */
    @Test
    public void whenPrintlnThenResult() {
        final ByteArrayOutput byteArrayOutput = new ByteArrayOutput();

        byteArrayOutput.println("Hello, world!");

        assertThat(byteArrayOutput.toString(), is(String.format("Hello, world!%n")));
    }

    /**
     * Test correctness of <code>print</code> method.
     */
    @Test
    public void whenPrintThenResult() {
        final ByteArrayOutput byteArrayOutput = new ByteArrayOutput();

        byteArrayOutput.print("Hello, world!");

        assertThat(byteArrayOutput.toString(), is("Hello, world!"));
    }

    /**
     * Test correctness of <code>reset</code> method.
     */
    @Test
    public void whenResetThenResultIsEmpty() {
        final ByteArrayOutput byteArrayOutput = new ByteArrayOutput();

        byteArrayOutput.println("Hello, world!");
        byteArrayOutput.reset();

        assertThat(byteArrayOutput.toString(), is(""));
    }
}
