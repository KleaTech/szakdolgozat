module hu.kleatech.jigsaw.persistence {
    requires spring.context;
    requires hu.kleatech.jigsaw.model;
    requires spring.data.jpa;
    exports hu.kleatech.jigsaw.persistence;
    opens hu.kleatech.jigsaw.persistence to spring.core;
}