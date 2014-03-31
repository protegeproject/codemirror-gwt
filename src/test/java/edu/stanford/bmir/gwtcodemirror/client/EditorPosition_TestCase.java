package edu.stanford.bmir.gwtcodemirror.client;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 30/03/2014
 */
public class EditorPosition_TestCase {

    private int lineNumber;
    private int columnNumber;
    private EditorPosition editorPosition;

    @Before
    public void setUp() throws Exception {
        lineNumber = 2;
        columnNumber = 4;
        editorPosition = new EditorPosition(lineNumber, columnNumber);
    }

    @Test
    public void getLineNumberShouldReturnSuppliedLineNumber() {
        assertThat(editorPosition.getLineNumber(), is(equalTo(lineNumber)));
    }

    @Test
    public void getColumnNumberShouldReturnSuppliedColumnNumber() {
        assertThat(editorPosition.getColumnNumber(), is(equalTo(columnNumber)));
    }

    @Test
    public void equalsShouldReturnTrueForSameColumnAndLineNumber() {
        assertThat(editorPosition, is(equalTo(new EditorPosition(lineNumber, columnNumber))));
    }

    @Test
    public void equalsShouldReturnFalseForNullArgument() {
        assertThat(editorPosition, is(not(equalTo(null))));
    }

    @Test
    public void hashCodeShouldReturnTrueForSameColumnAndLineNumber() {
        assertThat(editorPosition.hashCode(), is(equalTo(new EditorPosition(lineNumber, columnNumber).hashCode())));
    }
}
