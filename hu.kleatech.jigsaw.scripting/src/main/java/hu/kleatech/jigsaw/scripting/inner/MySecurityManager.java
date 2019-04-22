package hu.kleatech.jigsaw.scripting.inner;

import java.io.FileDescriptor;
import java.net.InetAddress;
import java.security.Permission;

class MySecurityManager extends SecurityManager {
    private volatile ThreadLocal<Boolean> enabled = null;
    private final Object secret;
    MySecurityManager(Object pass) { 
        secret = pass;
        enabled = new ThreadLocal<Boolean>() {
            @Override protected Boolean initialValue() { return false; }
            @Override
            public void set(Boolean enabled) { super.set(enabled); }
        };
    }
    void disable(Object pass) {
        if (pass == secret) enabled.set(false);
    }
    void enable() {
        if (System.getSecurityManager() != this) System.setSecurityManager(this);
        enabled.set(true);
    }
    private void deny(String msg, Object... cause) {
        if (enabled.get()) throw new SecurityException(msg);
    }
    private void log(String msg, Object... cause) {
        if (enabled.get()) {
            System.out.println(concat("Allowed " + msg, cause));
        }
    }
    private void allow(Object... cause) {
    }
    @Override public void checkPermission(Permission perm)                      { allow("Permission", perm); }
    @Override public void checkPermission(Permission perm, Object context)      { allow("Permission", perm, context); }
    @Override public void checkSecurityAccess(String target)                    { deny("Security access", target); }
    @Override public void checkSetFactory()                                     { deny("Set factory"); }
    @Override public void checkPackageDefinition(String pkg)                    { deny("Package definition", pkg); }
    @Override public void checkPackageAccess(String pkg)                        { allow("Package access", pkg); }
    @Override public void checkPrintJobAccess()                                 { deny("Print job access"); }
    @Override public void checkPropertyAccess(String key)                       { log("Property access", key); }
    @Override public void checkPropertiesAccess()                               { deny("Properties access"); }
    @Override public void checkMulticast(InetAddress maddr)                     { deny("Multicast", maddr); }
    @Override public void checkAccept(String host, int port)                    { deny("Accept", host, port); }
    @Override public void checkListen(int port)                                 { deny("Listen", port); }
    @Override public void checkConnect(String host, int port, Object context)   { deny("Connect", host, port, context); }
    @Override public void checkConnect(String host, int port)                   { deny("Connect", host, port); }
    @Override public void checkDelete(String file)                              { deny("Delete", file); }
    @Override public void checkWrite(String file)                               { deny("Write", file); }
    @Override public void checkWrite(FileDescriptor fd)                         { deny("Write", fd); }
    @Override public void checkRead(String file, Object context)                { allow("Read", file, context); }
    @Override public void checkRead(String file)                                { allow("Read", file); }
    @Override public void checkRead(FileDescriptor fd)                          { allow("Read", fd); }
    @Override public void checkLink(String lib)                                 { deny("Link", lib); }
    @Override public void checkExec(String cmd)                                 { deny("Exec", cmd); }
    @Override public void checkExit(int status)                                 { deny("Exit", status); }
    @Override public void checkAccess(ThreadGroup g)                            { deny("Access", g); }
    @Override public void checkAccess(Thread t)                                 { deny("Access", t); }
    @Override public void checkCreateClassLoader()                              { log("CreateClassLoader"); }
    
    private static String concat(String msg, Object... cause) {
        String str = msg + ": ";
        for(Object s : cause) {
            str += s.toString() + " ";
        }
        return str;
    }
}