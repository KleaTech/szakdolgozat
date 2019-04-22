package hu.kleatech.jigsaw.scripting.inner;

import hu.kleatech.jigsaw.api.MyScriptException;
import hu.kleatech.jigsaw.api.Statistics;
import java.io.*;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class MyClassLoader extends ClassLoader {    
    static final Set<String> allowedClassNames = Set.of(Statistics.class.getName());
    static final Set<String> requiredJavaInnerPackages = Set.of("java.lang", "java.io", "java.lang.invoke");
    static final Set<String> allowedOwnPackages = Set.of("hu.kleatech.jigsaw.scripting.inner", "hu.kleatech.jigsaw.api");
    static final Set<String> allowedPackages = Stream.concat(requiredJavaInnerPackages.stream(), allowedOwnPackages.stream()).collect(Collectors.toSet());
    static final Set<String> deniedClassNames = Set.of();
    static final Set<String> deniedPackages = Set.of();
    
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        //If the class is precomiled by the JDK and is whitelisted we can load it here
        if (Class.forName(name).getPackageName().startsWith("java") || 
            allowedClassNames.contains(name) || 
            allowedPackages.contains(Class.forName(name).getPackageName())) {
            return super.loadClass(name);
        } else if (deniedClassNames.contains(name) ||
                   deniedPackages.contains(Class.forName(name).getPackageName())) {
            throw new SecurityException("Class denied: " + name);
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