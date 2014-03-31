package edu.stanford.bmir.gwtcodemirror.client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 31/03/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class AutoCompletionResultTestCase {


    @Mock
    protected EditorPosition fromPosition;

    protected  List<AutoCompletionChoice> choices;

    private AutoCompletionResult autoCompletionResult;

    @Before
    public void setUp() {
        choices = new ArrayList<AutoCompletionChoice>();
        choices.add(mock(AutoCompletionChoice.class));
        choices.add(mock(AutoCompletionChoice.class));
        autoCompletionResult = new AutoCompletionResult(choices, fromPosition);
    }

    @Test(expected = NullPointerException.class)
    public void constructorThrowsNullPointerExceptionForNullChoicesList() {
        new AutoCompletionResult(null, fromPosition);
    }

    @Test(expected = NullPointerException.class)
    public void constructorThrowsNullPointerExceptionForNullFromPosition() {
        new AutoCompletionResult(choices, null);
    }

    @Test
    public void getChoicesShouldReturnSuppliedValue() {
        assertThat(autoCompletionResult.getChoices(), is(equalTo(choices)));
    }

    @Test
    public void getFromPositionShouldReturnSuppliedValue() {
        assertThat(autoCompletionResult.getFromPosition(), is(equalTo(fromPosition)));
    }

    @Test
    public void equalObjectsShouldBeEqual() {
        AutoCompletionResult other = new AutoCompletionResult(choices, fromPosition);
        assertThat(autoCompletionResult, is(equalTo(other)));
    }

    @Test
    public void equalObjectsShouldHaveTheSameHashCode() {
        AutoCompletionResult other = new AutoCompletionResult(choices, fromPosition);
        assertThat(autoCompletionResult.hashCode(), is(equalTo(other.hashCode())));
    }

    @Test
    public void mutatingSuppliedListShouldNotMutateObject() {
        List<AutoCompletionChoice> originalChoices = new ArrayList<AutoCompletionChoice>(choices);
        choices.remove(0);
        assertThat(autoCompletionResult.getChoices(), is(equalTo(originalChoices)));
    }


}
