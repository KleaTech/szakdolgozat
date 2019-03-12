module hu.kleatech.jigsaw.service {
    requires spring.context;
    requires hu.kleatech.jigsaw.persistence;
    requires hu.kleatech.jigsaw.utils;
    requires spring.beans;
    exports hu.kleatech.jigsaw.service.interfaces;
    opens hu.kleatech.jigsaw.service to spring.core;
    exports hu.kleatech.jigsaw.service to spring.core;
    opens hu.kleatech.jigsaw.service.interfaces to spring.core;
}