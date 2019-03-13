module hu.kleatech.jigsaw.controller {
    requires hu.kleatech.jigsaw.service;
    requires hu.kleatech.jigsaw.scripting;
    requires hu.kleatech.jigsaw.model;
    
    requires spring.context;
    requires spring.beans;
    requires spring.web;
    requires spring.boot;
    
    exports hu.kleatech.jigsaw.controller;
    opens hu.kleatech.jigsaw.controller to spring.core;
}