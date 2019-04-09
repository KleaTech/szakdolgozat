module hu.kleatech.jigsaw.service {
    
    requires spring.context;
    requires spring.beans;
    requires spring.boot;
    requires com.fasterxml.jackson.databind;
    //exports hu.kleatech.jigsaw.service.serialization to com.fasterxml.jackson.databind;
    exports hu.kleatech.jigsaw.service to spring.core;
    opens hu.kleatech.jigsaw.service to spring.core;
    opens hu.kleatech.jigsaw.service.interfaces to spring.core;
    
    requires hu.kleatech.jigsaw.persistence;
    requires hu.kleatech.jigsaw.utils;
    requires hu.kleatech.jigsaw.api;
    
    exports hu.kleatech.jigsaw.service.interfaces;
}