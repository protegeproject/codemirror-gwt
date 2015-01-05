package edu.stanford.bmir.gwtcodemirror.client;

import com.google.gwt.junit.client.GWTTestCase;

import javax.validation.constraints.Null;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date: 31/03/2014
 */
public class GwtTest_GWTCodeMirror extends GWTTestCase {

    private GWTCodeMirror codeMirror;

    @Override
    public String getModuleName() {
        return "edu.stanford.bmir.gwtcodemirror.client.CodeMirrorModuleJUnit";
    }

    @Override
    protected void gwtSetUp() throws Exception {
        super.gwtSetUp();
        delayTestFinish(10000);
        codeMirror = new GWTCodeMirror();
    }

    public void test_NullModeThrowsNullPointerException() {
        try {
            new GWTCodeMirror(null);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            // Pass
        }
    }

    public void test_NullThemeThrowsNullPointerException() {
        try {
            new GWTCodeMirror("manchestersyntax", null);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            // Pass
        }
    }

    public void test_getValue_Returns_InitialValueOfEmptyString() {
        String value = codeMirror.getValue();
        assertEquals("", value);
        finishTest();
    }

    public void test_getValue_Returns_InitialSetValue() {
        String testValue = "TestValue";
        codeMirror.setValue(testValue);
        assertEquals(testValue, codeMirror.getValue());
        finishTest();
    }

    public void test_setValue_Throws_NullPointerException_ForNullParameter() {
        try {
            codeMirror.setValue(null);
            fail();
        } catch (NullPointerException e) {
            finishTest();
        }
    }

    public void test_isEnabled_Returns_InitialValueOfTrue() {
        assertTrue(codeMirror.isEnabled());
        finishTest();
    }

    public void test_isEnabled_Returns_FalseWhenSetFalse() {
        codeMirror.setEnabled(false);
        assertFalse(codeMirror.isEnabled());
        finishTest();
    }

    public void test_setAutoCompletionHandlerThrowsNullPointerFroNullParameter() {
        try {
            codeMirror.setAutoCompletionHandler(null);
            fail();
        } catch (NullPointerException e) {
            finishTest();
        }
    }

    public void test_getCaretPosition_ReturnsLineZeroColumnZeroForInitialState() {
        EditorPosition editorPosition = codeMirror.getCaretPosition();
        assertEquals(0, editorPosition.getLineNumber());
        assertEquals(0, editorPosition.getColumnNumber());
        finishTest();
    }

    public void test_getCaretIndex_Returns_ZeroForInitialState() {
        assertEquals(0, codeMirror.getCaretIndex());
        finishTest();
    }

    public void test_getIndexFromEditorPosition_Returns_ZeroForInitialState() {
        assertEquals(0, codeMirror.getIndexFromEditorPosition(new EditorPosition(0, 0)));
        finishTest();
    }

}
