package ru.lightstar.sapper.io;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * <code>Output</code> interface realization, which outputs data to byte array.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class ByteArrayOutput implements Output {

    /**
     * Inner <code>ByteArrayOutputStream</code>.
     */
    private final ByteArrayOutputStream byteArrayOutputStream;

    /**
     * Inner <code>PrintStream</code>, created on top of {@link #byteArrayOutputStream}.
     */
    private final PrintStream byteArrayPrintStream;

    /**
     * Constructs <code>ByteArrayOutput</code> object.
     */
    public ByteArrayOutput() {
        super();
        this.byteArrayOutputStream = new ByteArrayOutputStream();
        this.byteArrayPrintStream = new PrintStream(this.byteArrayOutputStream);
    }

    /**
     * Discard all text inside inner {@link #byteArrayOutputStream}.
     */
    public void reset() {
        this.byteArrayOutputStream.reset();
    }

    /**
     * Get all text inside inner {@link #byteArrayOutputStream}.
     *
     * @return result string.
     */
    @Override
    public String toString() {
        return this.byteArrayOutputStream.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void println(final String line) {
        this.byteArrayPrintStream.println(line);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void print(final String text) {
        this.byteArrayPrintStream.print(text);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        this.byteArrayPrintStream.close();
    }
}
