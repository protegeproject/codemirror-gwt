package edu.stanford.bmir.gwtcodemirror.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 05/01/15
 */
public interface CodeMirrorClientBundle extends ClientBundle {

    public static final CodeMirrorClientBundle INSTANCE = GWT.create(CodeMirrorClientBundle.class);

    @Source("js/codemirror/lib/codemirror.js")
    TextResource codeMirrorLibrary();

    @Source("js/codemirror/addon/mode/overlay.js")
    TextResource overlayLibrary();

    @Source("js/codemirror/addon/hint/show-hint.js")
    TextResource jsHintLibrary();

    @Source("js/codemirror/mode/manchestersyntax/manchestersyntax.js")
    TextResource manchesterSyntaxMode();

    @Source("js/codemirror/mode/markdown/markdown.js")
    TextResource markdownMode();

    @Source("js/codemirror/mode/gfm/gfm.js")
    TextResource gfmMarkdownMode();
}
