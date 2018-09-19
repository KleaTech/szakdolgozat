module hu.kleatech.jigsaw.api {
    requires spring.context;
    requires spring.beans;
    opens hu.kleatech.jigsaw.api to spring.core;

    exports hu.kleatech.jigsaw.api;

    requires hu.kleatech.jigsaw.persistence;
}