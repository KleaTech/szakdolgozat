package hu.kleatech.jigsaw.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class Common {
    @ModelAttribute("js")
    public JavaScript getJavaScript() {
        return JavaScript.JAVASCRIPT;
    }
    
    @ModelAttribute("utils")
    public ViewUtils getViewUtils() {
        return ViewUtils.VIEWUTILS;
    }
}
