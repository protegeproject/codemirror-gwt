package edu.stanford.bmir.gwtcodemirror.client;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 18/03/2014
 */
public interface AutoCompletionHandler {

    /**
     * Called to get the completions for the specified editor text.
     * @param text The text.  Not {@code null}.
     * @param caretPosition The caret position, containing line and column information.  Not {@code null}.
     * @param caretIndex The caret index with respect to the editor text.  Not {@code null}.
     * @param callback A callback that should be used to signal that auto-completion results are ready.  Not {@code null}.
     */
    void getCompletions(String text, EditorPosition caretPosition, int caretIndex, AutoCompletionCallback callback);
}
