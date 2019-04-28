package hu.kleatech.jigsaw.controller;

import hu.kleatech.jigsaw.service.interfaces.EventGroupService;
import hu.kleatech.jigsaw.service.summarization.SummarizationService;
import hu.kleatech.jigsaw.utils.Utils;
import java.io.FileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class SummarizationController {
    
    @Autowired EventGroupService eventGroupService;
    @Autowired SummarizationService summarizationService;
    
    @GetMapping("sum/{id}")
    @ResponseBody
    public String sum(@PathVariable Long id) {
        return Utils.TryOrNull(FileNotFoundException.class, () -> summarizationService.summarize(eventGroupService.get(id), null).toString());
    }
}
