package hu.kleatech.jigsaw.service.interfaces;

import java.io.IOException;
import java.util.List;

public abstract class ManifestHandlerService implements TemplateLoaderService {
    
    public abstract void loadManifest(String eventGroupName) throws IOException;
    @Override public abstract void loadTemplates(String eventGroupName) throws IOException;
    public abstract void copyTemplates(String eventGroupName) throws IOException;
    public abstract List<String> findManifests();
    public abstract String getTeamFragment(String eventGroupName) throws IOException;
    public abstract String getParticipantFragment(String eventGroupName) throws IOException;
    public abstract void invalidateCache();
    public abstract void startFileSystemWatch();
    public abstract void refreshNow() throws IOException;
}
