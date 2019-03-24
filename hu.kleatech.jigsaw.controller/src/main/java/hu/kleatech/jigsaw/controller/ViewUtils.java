package hu.kleatech.jigsaw.controller;

import hu.kleatech.jigsaw.utils.*;
import java.time.LocalDate;
import java.time.Period;

public class ViewUtils {
    public final static ViewUtils VIEWUTILS = new ViewUtils();
    private ViewUtils(){}

    public StaticMap<String> getPojo(){
        return new StaticMap<>();
    }
    
    public Integer birthDateToAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
