package edu.stanford.bmir.gwtcodemirror.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.resources.client.TextResource;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 05/01/15
 */
public class CodeMirror implements EntryPoint {

    @Override
    public void onModuleLoad() {
        CodeMirrorClientBundle clientBundle = CodeMirrorClientBundle.INSTANCE;
        injectScript(clientBundle.codeMirrorLibrary());
        injectScript(clientBundle.jsHintLibrary());
        injectScript(clientBundle.manchesterSyntaxMode());
    }

    private void injectScript(TextResource scriptResource) {
        ScriptInjector.fromString(scriptResource.getText())
                .setRemoveTag(false)
                .setWindow(ScriptInjector.TOP_WINDOW)
                .inject();
    }
}
