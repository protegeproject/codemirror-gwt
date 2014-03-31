package edu.stanford.bmir.gwtcodemirror.client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 31/03/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class AutoCompletionChoiceTestCase {

    @Mock
    protected EditorPosition from;

    @Mock
    protected EditorPosition to;

    private static final String text = "text";

    private static final  String displayText = "displayText";

    private static final  String cssName = "cssName";

    private AutoCompletionChoice choice;

    @Before
    public void setUp() throws Exception {
        choice = new AutoCompletionChoice(text, displayText, cssName, from, to);
    }

    @Test(expected = NullPointerException.class)
    public void nullTextThrowsNullPointerException() {
        new AutoCompletionChoice(null, displayText, cssName, from, to);
    }

    @Test(expected = NullPointerException.class)
    public void nullDisplayTextThrowsNullPointerException() {
        new AutoCompletionChoice(text, null, cssName, from, to);
    }

    @Test(expected = NullPointerException.class)
    public void nullCssClassNameThrowsNullPointerException() {
        new AutoCompletionChoice(text, displayText, null, from, to);
    }

    @Test(expected = NullPointerException.class)
    public void nullFromThrowsNullPointerException() {
        new AutoCompletionChoice(text, displayText, cssName, null, to);
    }

    @Test(expected = NullPointerException.class)
    public void nullToThrowsNullPointerException() {
        new AutoCompletionChoice(text, displayText, cssName, from, null);
    }

    @Test
    public void getTextShouldReturnSuppliedText() {
        assertThat(choice.getText(), is(equalTo(text)));
    }

    @Test
    public void getDisplayTextShouldReturnSuppliedDisplayText() {
        assertThat(choice.getDisplayText(), is(equalTo(displayText)));
    }

    @Test
    public void getCssClassNameShouldReturnSuppliedCssClassName() {
        assertThat(choice.getCssClassName(), is(equalTo(cssName)));
    }

    @Test
    public void getReplaceTextFromShouldReturnSuppliedFrom() {
        assertThat(choice.getReplaceTextFrom(), is(equalTo(from)));
    }

    @Test
    public void getReplaceTextToShouldReturnSuppliedTo() {
        assertThat(choice.getReplaceTextTo(), is(equalTo(to)));
    }

    @Test
    public void equalsShouldReturnTrueForEqualObjects() {
        AutoCompletionChoice other = new AutoCompletionChoice(text, displayText, cssName, from, to);
        assertThat(choice, is(equalTo(other)));
    }

    @Test
    public void hashCodeShouldReturnEqualHashCodesForEqualObjects() {
        AutoCompletionChoice other = new AutoCompletionChoice(text, displayText, cssName, from, to);
        assertThat(choice.hashCode(), is(equalTo(other.hashCode())));
    }

}
