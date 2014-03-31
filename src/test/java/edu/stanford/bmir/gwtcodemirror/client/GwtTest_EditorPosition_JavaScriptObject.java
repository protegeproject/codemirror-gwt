package edu.stanford.bmir.gwtcodemirror.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.junit.client.GWTTestCase;


/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 31/03/2014
 */
public class GwtTest_EditorPosition_JavaScriptObject extends GWTTestCase {

    private static final int LINE_NUMBER = 2;

    private static final int COLUMN_NUMBER = 5;

    private static final EditorPosition editorPosition = new EditorPosition(LINE_NUMBER, COLUMN_NUMBER);


    @Override
    public String getModuleName() {
        return "edu.stanford.bmir.gwtcodemirror.client.GWTCodeMirrorModuleJUnit";
    }

    @Override
    protected void gwtSetUp() throws Exception {
        super.gwtSetUp();
        delayTestFinish(10000);
    }

    public void test_JavaScriptObjectIsNotNull() {
        JavaScriptObject javaScriptObject = editorPosition.toJavaScriptObject();
        assertNotNull(javaScriptObject);
        finishTest();
    }

    public void test_JavaScriptObjectShouldHaveSuppliedLineNumber() {
        JavaScriptObject javaScriptObject = editorPosition.toJavaScriptObject();
        assertTrue(hasPropertyWithValue(javaScriptObject, "line", LINE_NUMBER));
        finishTest();
    }

    public void test_JavaScriptObjectShouldHaveSuppliedColumnNumber() {
        JavaScriptObject javaScriptObject = editorPosition.toJavaScriptObject();
        assertTrue(hasPropertyWithValue(javaScriptObject, "ch", COLUMN_NUMBER));
        finishTest();
    }

    public void test_fromJavaScriptObjectShouldReturnEditorWithMatchingColumn() {
        JavaScriptObject javaScriptObject = createTestObject();
        EditorPosition editorPositionFromJavaScriptObject = EditorPosition.fromJavaScriptObject(javaScriptObject);
        assertTrue(editorPositionFromJavaScriptObject.getColumnNumber() == COLUMN_NUMBER);
        finishTest();
    }

    public void test_fromJavaScriptObjectShouldReturnEditorWithMatchingLine() {
        JavaScriptObject javaScriptObject = createTestObject();
        EditorPosition editorPositionFromJavaScriptObject = EditorPosition.fromJavaScriptObject(javaScriptObject);
        assertTrue(editorPositionFromJavaScriptObject.getLineNumber() == LINE_NUMBER);
        finishTest();
    }

    private static native boolean hasPropertyWithValue(JavaScriptObject object, String property, int value)/*-{
        return object[property] == value;
    }-*/;

    private static native JavaScriptObject createTestObject()/*-{
        return {line: 2, ch: 5};
    }-*/;
}
