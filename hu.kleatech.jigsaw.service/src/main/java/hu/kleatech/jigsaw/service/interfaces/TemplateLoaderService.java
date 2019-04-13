package hu.kleatech.jigsaw.service.interfaces;

import java.io.IOException;
import org.springframework.web.context.request.async.DeferredResult;

public interface TemplateLoaderService {
    void loadTemplates(String eventGroupName) throws IOException;
    void registerListener(DeferredResult<String> listener);
}
