package edu.stanford.bmir.gwtcodemirror.client;

import com.google.common.base.Objects;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 19/03/2014
 *         Represents a CodeMirror editor position, which is identified by the zero-based line number and column number.
 */
public class EditorPosition implements Serializable, IsSerializable {

    private int lineNumber;

    private int columnNumber;

    private EditorPosition() {
    }

    public EditorPosition(int lineNumber, int columnNumber) {
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    @Override
    public int hashCode() {
        return "EditorPosition".hashCode() + lineNumber + columnNumber * 13;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof EditorPosition)) {
            return false;
        }
        EditorPosition other = (EditorPosition) o;
        return this.lineNumber == other.lineNumber && this.columnNumber == other.columnNumber;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("EditorPosition")
                .add("lineNumber", lineNumber)
                .add("columnNumber", columnNumber)
                .toString();
    }

    /**
     * Consverts this {@code EditorPosition} to a {@code JavaScriptObject}.
     *
     * @return The {@code JavaScriptObject} that is equivalent to this {@code EditorPosition}.
     */
    public JavaScriptObject toJavaScriptObject() {
        return toJavaScriptObject(lineNumber, columnNumber);
    }

    /**
     * Create a JavaScriptObject that CodeMirror can use.
     *
     * @param line The line number.
     * @param ch   The column number.
     * @return The JavaScriptObject position object.
     */
    private native JavaScriptObject toJavaScriptObject(int line, int ch)/*-{
        return {
            line: line,
            ch: ch
        };
    }-*/;


    /**
     * Extracts an {@link EditorPosition} from the specified JavaScriptObject.
     *
     * @param object The object.  Not {@code null}.
     * @return The corresponding {@link EditorPosition}
     * @throws java.lang.NullPointerException is {@code object} is {@code null}.
     */
    public static EditorPosition fromJavaScriptObject(JavaScriptObject object) {
        checkNotNull(object);
        int line = getIntFromJavaScriptObject(object, "line", 0);
        int ch = getIntFromJavaScriptObject(object, "ch", 0);
        return new EditorPosition(line, ch);
    }

    private static native int getIntFromJavaScriptObject(JavaScriptObject object, String propertyName, int defaultValue)/*-{
        if (object[propertyName] == undefined) {
            return defaultValue;
        }
        return object[propertyName];
    }-*/;

}
