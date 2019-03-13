package hu.kleatech.jigsaw.scripting;

import javax.script.ScriptException;

public class MyScriptException extends ScriptException {
    public MyScriptException(String s) {
        super(s);
    }

    public MyScriptException(Exception e) {
        super(e);
    }

    public MyScriptException(String message, String fileName, int lineNumber) {
        super(message, fileName, lineNumber);
    }

    public MyScriptException(String message, String fileName, int lineNumber, int columnNumber) {
        super(message, fileName, lineNumber, columnNumber);
    }

    public MyScriptException(ScriptException e) {
        super(e.getMessage(), e.getFileName(), e.getLineNumber(), e.getColumnNumber());
    }
}
