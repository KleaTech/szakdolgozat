module hu.kleatech.jigsaw.service {
    requires spring.context;
    requires hu.kleatech.jigsaw.persistence;
    requires spring.beans;
    exports hu.kleatech.jigsaw.service;
    opens hu.kleatech.jigsaw.service to spring.core;
}