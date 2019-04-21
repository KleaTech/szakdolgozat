package hu.kleatech.jigsaw.scripting.inner;

public enum RequestType {
    PRERESULTS("preresults"), 
    RESULT("result");
    public final String methodName;
    private RequestType(String methodName) {
        this.methodName = methodName;
    }
}