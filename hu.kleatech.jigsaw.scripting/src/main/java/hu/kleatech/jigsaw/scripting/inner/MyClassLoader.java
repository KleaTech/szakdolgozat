package hu.kleatech.jigsaw.scripting.inner;

import hu.kleatech.jigsaw.api.MyScriptException;
import java.io.*;

class MyClassLoader extends ClassLoader {    
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        //If the class is precomiled by the JDK and is whitelisted we can load it here
        if (Class.forName(name).getPackageName().startsWith("java") || 
            MySecurityManager.allowedClassNames.contains(name) || 
            MySecurityManager.allowedPackages.contains(Class.forName(name).getPackageName())) {
            return super.loadClass(name);
        }
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