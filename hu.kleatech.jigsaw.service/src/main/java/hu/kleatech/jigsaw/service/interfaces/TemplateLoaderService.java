package hu.kleatech.jigsaw.service.interfaces;

import java.io.IOException;

public interface TemplateLoaderService {
    void loadTemplates(String eventGroupName) throws IOException;
}
