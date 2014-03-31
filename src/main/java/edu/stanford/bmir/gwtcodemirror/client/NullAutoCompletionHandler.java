package edu.stanford.bmir.gwtcodemirror.client;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 18/03/2014
 */
public class NullAutoCompletionHandler implements AutoCompletionHandler {

    public void getCompletions(String text, EditorPosition caretPosition, int caretIndex, AutoCompletionCallback callback) {
        callback.completionsReady(AutoCompletionResult.emptyResult());
    }
}
