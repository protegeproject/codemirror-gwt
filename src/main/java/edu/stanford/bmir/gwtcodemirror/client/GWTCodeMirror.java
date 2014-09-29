package edu.stanford.bmir.gwtcodemirror.client;

import com.google.common.base.Optional;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.SimplePanel;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 18/03/2014
 * @author <a href='donbeave@gmail.com'>Alexey Zhokhov</a>
 *         A wrapper for the native JavaScript CodeMirror editor.
 */
public class GWTCodeMirror extends Composite implements TakesValue<String>, HasValueChangeHandlers<String>, HasEnabled {

    private static final NullAutoCompletionHandler NULL_AUTO_COMPLETION_HANDLER = new NullAutoCompletionHandler();

    private static final String ELEMENT_ID_PREFIX = "cm-editor-";
    private static final String DEFAULT_MODE = "manchestersyntax";
    private static final String DEFAULT_THEME = "default";
    private static final boolean DEFAULT_READ_ONLY = false;
    private static final boolean DEFAULT_LINE_NUMBERS = true;
    private static final boolean DEFAULT_LINE_WRAPPING = true;

    /*
        Important things to remember:
        JavaScriptObjects can only be created in and mutated in native java script code.
     */
    private static int counter = 0;

    private boolean settingValue = false;

    private JavaScriptObject theCM;

    private Optional<TextMarker> errorMarker = Optional.absent();

    private AutoCompletionHandler autoCompletionHandler = NULL_AUTO_COMPLETION_HANDLER;

    private InitialOptions initialOptions = new InitialOptions();

    public GWTCodeMirror() {
        this(DEFAULT_MODE);
    }

    public GWTCodeMirror(String mode) {
        this(mode, null);
    }

    public GWTCodeMirror(String mode, String theme) {
        initialOptions.setMode(mode);
        if (theme != null)
            initialOptions.setTheme(theme);

        initWidget(new SimplePanel());
    }

    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

    public String getValue() {
        if (theCM == null)
            return initialOptions.getValue();

        return getValue(theCM);
    }

    /**
     * Sets the value of the editor.
     *
     * @param value The value.  Not {@code null}.
     * @throws java.lang.NullPointerException if {@code value} is {@code null}.
     */
    public void setValue(String value) {
        checkNotNull(value);
        if (theCM == null) {
            initialOptions.setValue(value);
            return;
        }
        try {
            settingValue = true;
            setValue(theCM, value);
        } finally {
            settingValue = false;
        }
    }


    /**
     * Sets the {@link edu.stanford.bmir.gwtcodemirror.client.AutoCompletionHandler}.
     *
     * @param autoCompletionHandler The handler.  Not {@code null}.
     * @throws java.lang.NullPointerException if {@code autoCompletionHandler} is {@code null}.
     */
    public void setAutoCompletionHandler(AutoCompletionHandler autoCompletionHandler) {
        this.autoCompletionHandler = checkNotNull(autoCompletionHandler);
    }

    /**
     * Clears the previously set {@link edu.stanford.bmir.gwtcodemirror.client.AutoCompletionHandler}.
     */
    public void clearAutoCompletionHandler() {
        setAutoCompletionHandler(NULL_AUTO_COMPLETION_HANDLER);
    }


    public boolean isEnabled() {
        if (theCM == null)
            return !initialOptions.isReadOnly();

        return isEnabled(theCM);
    }

    public native boolean isEnabled(JavaScriptObject theCM)/*-{
        return !theCM.getOption("readOnly");
    }-*/;

    public void setEnabled(boolean enabled) {
        if (theCM == null) {
            initialOptions.setReadOnly(!enabled);
            return;
        }
        setReadOnly(theCM, !enabled);
    }

    private native void setReadOnly(JavaScriptObject theCM, boolean readOnly)/*-{
        theCM.setOption("readOnly", readOnly);
    }-*/;

    public void setLineWrapping(boolean b) {
        if (theCM == null) {
            initialOptions.setLineWrapping(b);
            return;
        }
        setLineWrapping(theCM, b);
    }

    private native void setLineWrapping(JavaScriptObject theCM, boolean b)/*-{
        theCM.setOption("lineWrapping", b);
    }-*/;


    public EditorPosition getCaretPosition() {
        if (theCM == null)
            return new EditorPosition(0, 0);

        JavaScriptObject editorPosition = getEditorPosition(theCM);
        return EditorPosition.fromJavaScriptObject(editorPosition);
    }

    private native JavaScriptObject getEditorPosition(JavaScriptObject theCM)/*-{
        return theCM.getCursor("start");
    }-*/;

    public int getCaretIndex() {
        return getIndexFromEditorPosition(getCaretPosition());
    }

    public int getIndexFromEditorPosition(EditorPosition editorPosition) {
        if (theCM == null)
            return 0;

        return getIndexFromPosition(theCM, editorPosition.toJavaScriptObject());
    }

    private native int getIndexFromPosition(JavaScriptObject theCM, JavaScriptObject position)/*-{
        return theCM.indexFromPos(position);
    }-*/;

    public void reindentLines() {
        if (theCM == null) {
            return;
        }
        reindentLines(theCM);
    }

    private native void reindentLines(JavaScriptObject theCM)/*-{
        var lineCount = theCM.lineCount();
        for (i = 0; i < lineCount; i++) {
            theCM.indentLine(i);
        }
    }-*/;


    public void clearErrorRange() {
        if (theCM == null) {
            return;
        }
        if (!errorMarker.isPresent()) {
            return;
        }
        errorMarker.get().clear();
        errorMarker = Optional.absent();
    }


    public void setErrorRange(EditorPosition start, EditorPosition end) {
        if (theCM == null)
            return;

        clearErrorRange();
        JavaScriptObject mark = markText(theCM, start.toJavaScriptObject(), end.toJavaScriptObject(), "error");
        errorMarker = Optional.of(new TextMarker(mark));
    }


    private void handleChange() {
        if (!settingValue)
            ValueChangeEvent.fire(this, getValue());
    }

    /**
     * Overrides the onLoad() to set up the CodeMirror instance.
     */
    @Override
    protected void onLoad() {
        super.onLoad();
        Element element = getElement();
        String id = ELEMENT_ID_PREFIX + ++counter;
        element.setId(id);
        theCM = setup(this, id, initialOptions.toJavaScriptObject());
    }

    private native String getValue(JavaScriptObject thCM)/*-{
        return thCM.getValue();
    }-*/;

    private native void setValue(JavaScriptObject theCM, String text)/*-{
        theCM.setValue(text);
    }-*/;

    /**
     * Called to retrive completions.
     *
     * @param editorText     The current editor text.
     * @param line           The line number that the caret is at (zero based).
     * @param ch             The character number that the caret is at (zero based).
     * @param completionList A JavaScriptObject that is an array and should be populated with lists of completions.
     *                       This can be done by calling {@link #addElement(JavaScriptObject, JavaScriptObject)}.
     * @param callback
     */
    private void getCompletions(final String editorText, final int line, final int ch, final int index, final JavaScriptObject completionList, final JavaScriptObject callback) {
        autoCompletionHandler.getCompletions(editorText, new EditorPosition(line, ch), index, new AutoCompletionCallback() {
            public void completionsReady(AutoCompletionResult result) {
                for (AutoCompletionChoice choice : result.getChoices()) {
                    addElement(completionList, createAutoCompletionResult(choice));
                }
                int fromLineNumber = result.getFromPosition().getLineNumber();
                int fromColumnNumber = result.getFromPosition().getColumnNumber();
                doAutoCompleteCallback(callback, completionList, fromLineNumber, fromColumnNumber);
            }
        });
    }

    /**
     * Adds a JavaScriptObject to a JavaScript array.
     *
     * @param jsListObject The JavaScriptObject that represents the array.
     * @param elementToAdd The JavaScriptObject that represents the element to be added.
     */
    private native void addElement(JavaScriptObject jsListObject, JavaScriptObject elementToAdd)/*-{
        jsListObject.push(elementToAdd);
    }-*/;

    private JavaScriptObject createAutoCompletionResult(AutoCompletionChoice choice) {
        JavaScriptObject from = choice.getReplaceTextFrom().toJavaScriptObject();
        JavaScriptObject to = choice.getReplaceTextTo().toJavaScriptObject();
        return createAutoCompletionResult(choice.getText(), choice.getDisplayText(), choice.getCssClassName(), from, to);
    }

    /**
     * Creates a JavaScriptObject that has the appropriate properties to describe the auto-completion result.
     *
     * @param text        The text to insert.
     * @param displayText The text to display.
     * @param className   The CSS class name of the item in the list.
     * @return The JavaScriptObject that specified the given properties.
     */
    private native JavaScriptObject createAutoCompletionResult(String text, String displayText, String className, JavaScriptObject from, JavaScriptObject to)/*-{
        return {
            'text': text,
            'displayText': displayText,
            'className': className,
            'from': from,
            'to': to

        }
    }-*/;

    /**
     * Calls the auto-complete callback with the specified argument.
     *
     * @param callbackFunction The actual function to call.
     * @param argument         The argument to pass to the function.
     * @param line             The line of the completion (zero based index).
     * @param ch               The character index on the line of the completion (zero based index).
     */
    private native void doAutoCompleteCallback(JavaScriptObject callbackFunction, JavaScriptObject argument, int line, int ch)/*-{
        callbackFunction({
            list: argument,
            from: {'line': line, 'ch': ch}
        });
    }-*/;

    /**
     * Sets up the CodeMirror object in native JavaScript.
     *
     * @param myCodeMirror A pointer to the this instance which is used to call out from native code to methods on this.
     * @param id           The id of the element which the CodeMirror editor should be appended to.
     * @return The native CodeMirror object that was created and set up.  Other functions can use this as a pointer
     * for calls into native code.
     */
    private native JavaScriptObject setup(GWTCodeMirror myCodeMirror, String id, JavaScriptObject initialOptions)/*-{
        // We install an instance of the CodeMirror editor by assigning an id to the intended parent element
        // and then asking code mirror to create the editor for that element.
        var element = $doc.getElementById(id);
        var theCM = $wnd.CodeMirror(
            element,
            {
                mode: initialOptions["mode"],
                theme: initialOptions["theme"],
                readOnly: initialOptions["readOnly"],
                lineNumbers: initialOptions["lineNumbers"],
                lineWrapping: initialOptions["lineWrapping"],
                viewportMargin: Infinity,
                extraKeys: {
                    "Ctrl-Space": function (editor) {
                        $wnd.CodeMirror.showHint(editor, function (editor, callback) {
                            var result = [];
                            var cursor = editor.doc.getCursor();
                            var index = editor.indexFromPos(cursor);
                            $entry(myCodeMirror.@edu.stanford.bmir.gwtcodemirror.client.GWTCodeMirror::getCompletions(Ljava/lang/String;IIILcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)(editor.getValue(), cursor.line, cursor.ch, index, result, callback));
                        }, {async: true});
                    }
                }
            }
        );
        // Listener for changes and propagate them back into the GWT compiled code
        theCM.on("change", function () {
            $entry(myCodeMirror.@edu.stanford.bmir.gwtcodemirror.client.GWTCodeMirror::handleChange()());
        });
        return theCM;


    }-*/;

    private native JavaScriptObject markText(JavaScriptObject theCM, JavaScriptObject start, JavaScriptObject end, String cssClassName)/*-{
        return theCM.markText(start, end, {
            className: cssClassName
        });
    }-*/;

    private static class InitialOptions {

        private String mode = DEFAULT_MODE;
        private String theme = DEFAULT_THEME;
        private String value = "";
        private boolean readOnly = DEFAULT_READ_ONLY;
        private boolean lineNumbers = DEFAULT_LINE_NUMBERS;
        private boolean lineWrapping = DEFAULT_LINE_WRAPPING;

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public boolean isReadOnly() {
            return readOnly;
        }

        public boolean isLineNumbers() {
            return lineNumbers;
        }

        public boolean isLineWrapping() {
            return lineWrapping;
        }

        public void setReadOnly(boolean readOnly) {
            this.readOnly = readOnly;
        }

        public void setLineNumbers(boolean lineNumbers) {
            this.lineNumbers = lineNumbers;
        }

        public void setLineWrapping(boolean lineWrapping) {
            this.lineWrapping = lineWrapping;
        }

        public JavaScriptObject toJavaScriptObject() {
            JavaScriptObject result = JavaScriptObject.createObject();
            addProperty(result, "value", value);
            addProperty(result, "mode", mode);
            if (theme != null)
                addProperty(result, "theme", theme);
            addProperty(result, "readOnly", readOnly);
            addProperty(result, "lineNumbers", lineNumbers);
            addProperty(result, "lineWrapping", lineWrapping);
            return result;
        }

        private static native void addProperty(JavaScriptObject javaScriptObject, String property, String value)/*-{
            javaScriptObject[property] = value;
        }-*/;

        private static native void addProperty(JavaScriptObject javaScriptObject, String property, boolean value)/*-{
            javaScriptObject[property] = value;
        }-*/;

    }

    private static class TextMarker {

        private JavaScriptObject javaScriptObject;

        public TextMarker(JavaScriptObject javaScriptObject) {
            this.javaScriptObject = checkNotNull(javaScriptObject);
        }

        public void clear() {
            clear(javaScriptObject);
        }

        private native void clear(JavaScriptObject object)/*-{
            object.clear();
        }-*/;

    }

}
