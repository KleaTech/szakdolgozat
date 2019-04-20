package hu.kleatech.jigsaw.scripting;

import hu.kleatech.jigsaw.api.Dispatcher;
import java.nio.file.Path;

public class SecureEngineProvider implements hu.kleatech.jigsaw.api.EngineProvider {

    public static void load() {
        Dispatcher.setEngineProvider(new SecureEngineProvider());
    }
    
    @Override
    public Engine getEngine(Path scriptPath) {
        return new SecureEngine(scriptPath);
    }
}
