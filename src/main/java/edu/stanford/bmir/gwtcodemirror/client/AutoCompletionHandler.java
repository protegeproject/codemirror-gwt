package edu.stanford.bmir.gwtcodemirror.client;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 18/03/2014
 */
public interface AutoCompletionHandler {

    void getCompletions(String text, EditorPosition editorPosition, int editorIndex, AutoCompletionCallback callback);
}
