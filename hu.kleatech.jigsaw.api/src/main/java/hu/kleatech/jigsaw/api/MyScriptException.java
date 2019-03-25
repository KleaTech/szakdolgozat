package hu.kleatech.jigsaw.api;

import static hu.kleatech.jigsaw.utils.StringUtils.*;

public class MyScriptException extends RuntimeException{
    
    private String fileName;
    private Integer lineNumber;
    private Integer columnNumber;
    
    public MyScriptException(String s) {
        super(s);
    }

    public MyScriptException(String message, String fileName, int lineNumber) {
        this(message);
        this.fileName = fileName;
        this.lineNumber = lineNumber;
    }

    public MyScriptException(String message, String fileName, int lineNumber, int columnNumber) {
        this(message, fileName, lineNumber);
        this.columnNumber = columnNumber;
    }

    @Override
    public String toString() {
        return concat("MyScriptException", " : ", super.getMessage(), " (In: ", fileName, " , at line ", lineNumber, " column ", columnNumber, ")");
    }
}
