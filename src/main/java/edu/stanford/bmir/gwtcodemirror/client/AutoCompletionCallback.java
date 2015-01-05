package edu.stanford.bmir.gwtcodemirror.client;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 18/03/2014
 */
public interface AutoCompletionCallback {

    /**
     * A callback that can be used to inform the editor that an auto-completion result is ready.
     * @param result The result.  Not {@code null}.
     */
    void completionsReady(AutoCompletionResult result);

}
