package ru.lightstar.sapper.io;

import java.util.Collections;
import java.util.Iterator;

/**
 * Input data from given string iterator.
 *
 * @author LightStar
 * @since 0.0.1
 */
public class IteratorInput implements Input {

    /**
     * Inner iterator.
     */
    private Iterator<String> iterator;

    /**
     * Constructs <code>IteratorInput</code> object.
     *
     * @param iterator iterator.
     */
    public IteratorInput(final Iterator<String> iterator) {
        super();
        this.iterator = iterator;
    }

    /**
     * Constructs <code>IteratorInput</code> object with empty iterator.
     */
    public IteratorInput() {
        this(Collections.emptyIterator());
    }

    /**
     * Set new iterator object.
     *
     * @param iterator iterator.
     */
    public void setIterator(Iterator<String> iterator) {
        this.iterator = iterator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String next() {
        return this.iterator.next();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void waitEnter() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String ask(final Output output, final String question) {
        output.println(question);
        return this.iterator.next();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int askNumber(final Output output, final String question) {
        while (true) {
            try {
                return Integer.valueOf(this.ask(output, question));
            } catch(NumberFormatException e) {
                output.println("Not a number.");
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
    }
}
