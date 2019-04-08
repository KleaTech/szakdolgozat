module hu.kleatech.jigsaw.persistence {
    requires spring.context;
    requires transitive hu.kleatech.jigsaw.model;
    requires spring.data.jpa;
    requires spring.tx;
    exports hu.kleatech.jigsaw.persistence;
    opens hu.kleatech.jigsaw.persistence to spring.core;
}