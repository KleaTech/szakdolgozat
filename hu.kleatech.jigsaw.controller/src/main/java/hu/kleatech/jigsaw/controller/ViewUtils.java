package hu.kleatech.jigsaw.controller;

import hu.kleatech.jigsaw.utils.*;

public class ViewUtils {
    public final static ViewUtils VIEWUTILS = new ViewUtils();
    private ViewUtils(){}

    public StaticMap<String> getPojo(){
        return new StaticMap<>();
    }
}
