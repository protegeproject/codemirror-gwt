package edu.stanford.bmir.gwtcodemirror.client;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 18/03/2014
 */
public class AutoCompletionResult implements Serializable, IsSerializable {

    private static final AutoCompletionResult EMPTY_RESULT = new AutoCompletionResult();

    private List<AutoCompletionChoice> choices;

    private EditorPosition fromPosition;

    public AutoCompletionResult() {
        this(new ArrayList<AutoCompletionChoice>(), new EditorPosition(0, 0));
    }

    public AutoCompletionResult(List<AutoCompletionChoice> choices, EditorPosition fromPosition) {
        this.choices = new ArrayList<AutoCompletionChoice>(choices);
        this.fromPosition = fromPosition;
    }

    public EditorPosition getFromPosition() {
        return fromPosition;
    }

    public List<AutoCompletionChoice> getChoices() {
        return new ArrayList<AutoCompletionChoice>(choices);
    }

    public static AutoCompletionResult emptyResult() {
        return EMPTY_RESULT;
    }

}
