package edu.stanford.bmir.gwtcodemirror.client;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

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
        this.choices = new ArrayList<AutoCompletionChoice>(checkNotNull(choices));
        this.fromPosition = checkNotNull(fromPosition);
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

    @Override
    public int hashCode() {
        return "AutoCompletionResult".hashCode() + choices.hashCode() + fromPosition.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof AutoCompletionResult)) {
            return false;
        }
        AutoCompletionResult other = (AutoCompletionResult) o;
        return this.fromPosition.equals(other.fromPosition) && this.choices.equals(other.choices);
    }

}
