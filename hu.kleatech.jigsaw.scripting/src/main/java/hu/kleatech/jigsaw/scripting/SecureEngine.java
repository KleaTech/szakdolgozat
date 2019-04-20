package hu.kleatech.jigsaw.scripting;

import hu.kleatech.jigsaw.api.MyScriptException;
import java.io.*;
import java.net.InetAddress;
import java.nio.file.Path;
import java.security.Permission;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import javax.script.*;

public class SecureEngine implements hu.kleatech.jigsaw.api.EngineProvider.Engine {
    private final Path scriptPath;
    private final ScriptEngineManager engineManager = new ScriptEngineManager();
    
    SecureEngine(Path scriptPath) {
        this.scriptPath = scriptPath;
    }

    private ScriptEngine resolveEngine(String filename) {
        String[] str = filename.split("\\.");
        ScriptEngine engine = engineManager.getEngineByExtension(str[str.length-1]);
        return engine;
    }

    private FileReader resolveReader(String fileName) throws FileNotFoundException {
        return new FileReader(scriptPath.resolve(fileName).toFile());
    }

    @Override
    public Function<List<Double>, List<Double>> preresults(String filename) throws FileNotFoundException, MyScriptException, ClassCastException {
        try {
            MyIsolatedThread isolatedThread = new MyIsolatedThread(Request.PRERESULTS, resolveReader(filename), resolveEngine(filename));
            isolatedThread.start();
            return isolatedThread.preresults.get(1, TimeUnit.HOURS);
        } catch (TimeoutException e) {
            throw new MyScriptException("Timeout while running external script");
        } catch (InterruptedException | ExecutionException e) {
            throw new MyScriptException("Interruped while running external script: " + e.getMessage());
        } catch (RuntimeException e) {
            if ((e instanceof MyScriptException) || (e instanceof ClassCastException)) throw e;
        }
        throw new IllegalStateException("How did we get here?");
    }

    @Override
    public Function<List<Double>, Double> result(String filename) throws FileNotFoundException, MyScriptException, ClassCastException {
        try {
            MyIsolatedThread isolatedThread = new MyIsolatedThread(Request.PRERESULTS, resolveReader(filename), resolveEngine(filename));
            isolatedThread.start();
            return isolatedThread.result.get(1, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            throw new MyScriptException("Timeout while running external script");
        } catch (InterruptedException | ExecutionException e) {
            throw new MyScriptException("Interruped while running external script: " + e.getMessage());
        } catch (RuntimeException e) {
            if ((e instanceof MyScriptException) || (e instanceof ClassCastException)) throw e;
        }
        throw new IllegalStateException("How did we get here?");
    }
    
    
    
    //<editor-fold defaultstate="collapsed" desc="Security functionality">    
    private static class MySecurityManager extends SecurityManager {
        private volatile ThreadLocal<Boolean> enabled = null;
        private final Object secret;
        public MySecurityManager(Object pass) { 
            secret = pass;
            enabled = new ThreadLocal<Boolean>() {
                @Override protected Boolean initialValue() { return false; }
                @Override
                public void set(Boolean enabled) { super.set(enabled); }
            };
        }
        private void disable(Object pass) {
            if (pass == secret) enabled.set(false);
        }
        private void enable() {
            if (System.getSecurityManager() != this) System.setSecurityManager(this);
            enabled.set(true);
        }
        private void T(String cause) {
            if (enabled.get()) throw new SecurityException(cause);
        }
        private void L(String msg, Object cause) {
            if (enabled.get()) {
                if (cause.equals("java.io") || cause.equals("java.lang.invoke")) return;
                System.out.println(msg + " : " + cause);
                System.out.flush();
            }
        }
        private void A(Object... ignored) {}
        @Override public void checkPermission(Permission perm)                      { A("Permission denied", perm); }
        @Override public void checkPermission(Permission perm, Object context)      { A("Permission denied", perm); }
        @Override public void checkSecurityAccess(String target)                    { L("Security access denied", target); }
        @Override public void checkSetFactory()                                     { L("Set factory denied", ""); }
        @Override public void checkPackageDefinition(String pkg)                    { L("Package definition denied", pkg); }
        @Override public void checkPackageAccess(String pkg)                        { L("Package access denied", pkg); }
        @Override public void checkPrintJobAccess()                                 { L("Print job access denied", ""); }
        @Override public void checkPropertyAccess(String key)                       { L("Property access denied", key); }
        @Override public void checkPropertiesAccess()                               { L("Properties access denied", ""); }
        @Override public void checkMulticast(InetAddress maddr)                     { L("Multicast denied", maddr); }
        @Override public void checkAccept(String host, int port)                    { L("Accept denied", host); }
        @Override public void checkListen(int port)                                 { L("Listen denied", port); }
        @Override public void checkConnect(String host, int port, Object context)   { L("Connect denied", host); }
        @Override public void checkConnect(String host, int port)                   { L("Connect denied", host); }
        @Override public void checkDelete(String file)                              { L("Delete denied", file); }
        @Override public void checkWrite(String file)                               { L("Write denied", file); }
        @Override public void checkWrite(FileDescriptor fd)                         { L("Write denied", fd); }
        @Override public void checkRead(String file, Object context)                { A("Read denied", file); }
        @Override public void checkRead(String file)                                { A("Read denied", file); }
        @Override public void checkRead(FileDescriptor fd)                          { A("Read denied", fd); }
        @Override public void checkLink(String lib)                                 { L("Link denied", lib); }
        @Override public void checkExec(String cmd)                                 { L("Exec denied", cmd); }
        @Override public void checkExit(int status)                                 { L("Exit denied", status); }
        @Override public void checkAccess(ThreadGroup g)                            { L("Access denied", g); }
        @Override public void checkAccess(Thread t)                                 { L("Access denied", t); }
        @Override public void checkCreateClassLoader()                              { L("CreateClassLoader denied", ""); }
    }
    
    private static class MyIsolatedThread extends Thread {
        private final Object pass = new Object();
        private final MyClassLoader loader = new MyClassLoader();
        private final MySecurityManager sm = new MySecurityManager(pass);
        private final Request requestType;
        private final FileReader file;
        private final ScriptEngine engine;
        public final MyFuture<Function<List<Double>, List<Double>>> preresults = new MyFuture<>();
        public final MyFuture<Function<List<Double>, Double>> result = new MyFuture<>();

        public MyIsolatedThread(Request requestType, FileReader file, ScriptEngine engine) {
            super("IsolationThread");
            this.requestType = requestType;
            this.file = file;
            this.engine = engine;
        }
        
        @Override public void run() {
            sm.enable();
            runUntrustedCode();
            sm.disable(pass);
        }
        
        private void runUntrustedCode() {
            try {
                String className = "hu.kleatech.jigsaw.scripting.inner.EngineInner";
                Object scriptResult = loader.loadClass(className)
                        .getMethod(requestType.methodName, ScriptEngine.class, FileReader.class)
                        .invoke(null, engine, file);
                switch (requestType) {
                    case PRERESULTS:    preresults.result = (Function<List<Double>, List<Double>>) scriptResult;
                                        result.result = (s) -> { throw new IllegalStateException(); };
                                        break;
                    case RESULT:        result.result = (Function<List<Double>, Double>) scriptResult;
                                        preresults.result = (s) -> { throw new IllegalStateException(); };
                                        break;
                    default:            throw new IllegalArgumentException(requestType.name() + "is not supported.");
                }
            } catch (Throwable t) {
                t.printStackTrace();
                throw new MyScriptException("Error when running external script: " + t);
            }
        }
    }
    
    private static class MyFuture<T> implements Future<T> {
        volatile T result;
        @Override public boolean cancel(boolean bln) { throw new UnsupportedOperationException("Not supported yet."); }
        @Override public boolean isCancelled() { return false; }
        @Override public boolean isDone() { return result != null; }
        @Override public T get() throws InterruptedException, ExecutionException {
            while (result == null) Thread.sleep(1);
            return result;
        }
        @Override public T get(long l, TimeUnit tu) throws InterruptedException, ExecutionException, TimeoutException {
            long timespan = tu.toMillis(l);
            while (result == null) {
                Thread.sleep(l);
                timespan--;
                if (timespan <= 0) throw new TimeoutException();
            }
            return result;
        }
    }
//</editor-fold>
}

class MyClassLoader extends ClassLoader {
    private static final Set<String> allowedClassNames = Set.of(String.class.getName());
    
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        //If the class is precomiled by the JDK and is whitelisted we can load it here
        if (Class.forName(name).getPackageName().startsWith("java")) return super.loadClass(name);
        //If the class is not on the whitelist we load it manually
        return findClass(name);
    }
    @Override
    public Class findClass(String name) {
        byte[] b = loadClassData(name);
        return defineClass(name, b, 0, b.length);
    }
    private byte[] loadClassData(String name) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(name.replace(".", "/")+".class");
        ByteArrayOutputStream byteSt = new ByteArrayOutputStream();
        int len;
        //Checks for illegal actions in the bytecode can be placed here
        try {
            while((len=is.read())!=-1) {
                byteSt.write(len);
            }
        } catch (IOException e) {
            throw new MyScriptException("Error when converting external script to bytecode: " + e.getMessage());
        }
        return byteSt.toByteArray();
    }
}

enum Request {
    PRERESULTS("preresults"), RESULT("result");
    public final String methodName;
    private Request(String methodName) {
        this.methodName = methodName;
    }
}