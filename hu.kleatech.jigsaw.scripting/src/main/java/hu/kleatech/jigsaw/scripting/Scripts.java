package hu.kleatech.jigsaw.scripting;

import hu.kleatech.jigsaw.scripting.engine.Engine;

import java.nio.file.Paths;

public final class Scripts {
    private Scripts() {}
    public static Runnable getTest() {
        return () -> new Engine(Paths.get("c:\\Users\\Adam\\IdeaProjects\\jigsaw\\")).test();
    }
    public static Runnable getTestPut() {
        return () -> new Engine(Paths.get("c:\\Users\\Adam\\IdeaProjects\\jigsaw\\")).testPut();
    }
    public static Runnable getTestPutAndGet() {
        return () -> new Engine(Paths.get("c:\\Users\\Adam\\IdeaProjects\\jigsaw\\")).testPutAndGet();
    }
}
