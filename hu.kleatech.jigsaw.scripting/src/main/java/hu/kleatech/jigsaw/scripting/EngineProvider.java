package hu.kleatech.jigsaw.scripting;

import hu.kleatech.jigsaw.api.Dispatcher;
import java.nio.file.Path;

public class EngineProvider implements hu.kleatech.jigsaw.api.EngineProvider {

    public static final void load() {
        Dispatcher.setEngineProvider(new EngineProvider());
    }
    
    @Override
    public hu.kleatech.jigsaw.api.EngineProvider.Engine getEngine(Path scriptPath) {
        return new hu.kleatech.jigsaw.scripting.Engine(scriptPath);
    }
}
