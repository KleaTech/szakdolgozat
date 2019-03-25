module hu.kleatech.jigsaw {
    
    requires spring.beans;
    requires spring.data.jpa;
    requires hibernate.core;
    requires spring.context;
    requires spring.core;
    requires spring.jdbc;
    requires spring.orm;
    requires thymeleaf;
    requires thymeleaf.extras.java8time;
    requires thymeleaf.spring5;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires java.sql;
    exports hu.kleatech.jigsaw to spring.core, spring.beans, spring.context;
    opens hu.kleatech.jigsaw to spring.core;

    requires hu.kleatech.jigsaw.model;
    requires hu.kleatech.jigsaw.controller;
    requires hu.kleatech.jigsaw.service;
    requires hu.kleatech.jigsaw.scripting;
    requires hu.kleatech.jigsaw.utils;
    requires hu.kleatech.jigsaw.api;
}