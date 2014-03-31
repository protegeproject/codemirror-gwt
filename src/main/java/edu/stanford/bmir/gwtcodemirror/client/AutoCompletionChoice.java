package edu.stanford.bmir.gwtcodemirror.client;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 18/03/2014
 */
public class AutoCompletionChoice implements Serializable, IsSerializable {

    private String text;

    private String displayText;

    private String cssClassName;

    private EditorPosition replaceTextFrom;

    private EditorPosition getReplaceTextTo;

    /**
     * For serialization purposes only
     */
    private AutoCompletionChoice() {
    }

    public AutoCompletionChoice(String text, String displayText, String cssClassName, EditorPosition replaceTextFrom, EditorPosition getReplaceTextTo) {
        this.text = text;
        this.displayText = displayText;
        this.cssClassName = cssClassName;
        this.replaceTextFrom = replaceTextFrom;
        this.getReplaceTextTo = getReplaceTextTo;
    }

    public String getText() {
        return text;
    }

    public String getDisplayText() {
        return displayText;
    }

    public String getCssClassName() {
        return cssClassName;
    }

    public EditorPosition getReplaceTextFrom() {
        return replaceTextFrom;
    }

    public EditorPosition getGetReplaceTextTo() {
        return getReplaceTextTo;
    }
}
