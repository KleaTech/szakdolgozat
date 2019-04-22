package hu.kleatech.jigsaw.scripting.inner;

import java.io.FileReader;
import javax.script.ScriptEngine;

public enum RequestType {
    PRERESULTS("preresults", ScriptEngine.class, FileReader.class), 
    RESULT("result", ScriptEngine.class, FileReader.class),
    TEST("testPutAndGet", ScriptEngine.class);
    public final String methodName;
    public final Class[] signature;
    private RequestType(String methodName, Class... signature) {
        this.methodName = methodName;
        this.signature = signature;
    }
}