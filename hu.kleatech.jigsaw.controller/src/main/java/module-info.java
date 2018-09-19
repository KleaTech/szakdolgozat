module hu.kleatech.jigsaw.controller {
    requires hu.kleatech.jigsaw.service;
    requires hu.kleatech.jigsaw.scripting;
    requires spring.context;
    requires spring.beans;
    exports hu.kleatech.jigsaw.controller;
    opens hu.kleatech.jigsaw.controller to spring.core;
}