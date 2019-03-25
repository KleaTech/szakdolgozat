module hu.kleatech.jigsaw.controller {
    requires hu.kleatech.jigsaw.service;
    requires hu.kleatech.jigsaw.model;
    requires hu.kleatech.jigsaw.api;
    
    requires spring.context;
    requires spring.beans;
    requires spring.web;
    requires spring.boot;
    opens hu.kleatech.jigsaw.controller to spring.core;
    
    exports hu.kleatech.jigsaw.controller;
}