package hu.kleatech.jigsaw.service.interfaces;

import java.io.IOException;
import java.util.List;
import org.springframework.web.context.request.async.DeferredResult;

public abstract class ManifestHandlerService implements TemplateLoaderService {
    
    public abstract void loadManifest(String eventGroupName) throws IOException;
    @Override public abstract void loadTemplates(String eventGroupName) throws IOException;
    @Override public abstract void registerListener(DeferredResult<String> listener);
    public abstract void copyTemplates(String eventGroupName) throws IOException;
    public abstract List<String> findManifests();
    public abstract void startFileSystemWatch() throws IOException;
    public abstract void refreshNow() throws IOException;
}
