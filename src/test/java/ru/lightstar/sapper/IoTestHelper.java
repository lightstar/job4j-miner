package ru.lightstar.sapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Helper class used in tests with I/O.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class IoTestHelper {

    /**
     * Substitutes standard output stream with byte array stream
     *
     * @return output stream backed by byte array
     */
    public OutputStream mockStandardOutput() {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        return outputStream;
    }

    /**
     * Substitutes standard input stream with byte array stream
     *
     * @param buf byte buffer used as content of byte array stream
     */
    public void mockStandardInput(final byte[] buf) {
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(buf);
        System.setIn(inputStream);
    }

    /**
     * Join lines in one string using system line separator.
     *
     * @param lines joined lines
     * @return result string
     */
    public String joinLines(final String[] lines) {
        return String.format("%s%n", String.join(String.format("%n"), lines));
    }
}
