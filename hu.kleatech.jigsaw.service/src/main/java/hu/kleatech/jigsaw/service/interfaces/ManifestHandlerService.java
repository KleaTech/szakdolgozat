package hu.kleatech.jigsaw.service.interfaces;

import java.io.IOException;
import java.util.List;

public abstract class ManifestHandlerService {
    
    public abstract void loadManifest(String eventGroupName) throws IOException;
    public abstract List<String> findManifests();
}
