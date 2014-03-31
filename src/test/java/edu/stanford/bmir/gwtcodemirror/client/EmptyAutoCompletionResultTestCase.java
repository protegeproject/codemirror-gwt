package edu.stanford.bmir.gwtcodemirror.client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 31/03/2014
 */
@RunWith(MockitoJUnitRunner.class)
public class EmptyAutoCompletionResultTestCase {

    private AutoCompletionResult autoCompletionResult;

    @Before
    public void setUp() {
        autoCompletionResult = new AutoCompletionResult();
    }


    @Test
    public void getChoicesShouldReturnEmptyList() {
        assertThat(autoCompletionResult.getChoices(), is(empty()));
    }

    @Test
    public void getFromPositionReturnsOrigin() {
        assertThat(autoCompletionResult.getFromPosition().getLineNumber(), is(equalTo(0)));
        assertThat(autoCompletionResult.getFromPosition().getColumnNumber(), is(equalTo(0)));
    }

    @Test
    public void equalsReturnsTrueForOtherEmptyResult() {
        AutoCompletionResult other = new AutoCompletionResult();
        assertThat(autoCompletionResult, is(equalTo(other)));
    }

    @Test
    public void hashCodeReturnsSameHashCodeAsOtherEmptyResult() {
        AutoCompletionResult other = new AutoCompletionResult();
        assertThat(autoCompletionResult.hashCode(), is(equalTo(other.hashCode())));
    }


}
