module hu.kleatech.jigsaw {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires java.sql;
    requires hu.kleatech.jigsaw.model;
    exports hu.kleatech.jigsaw to spring.core, spring.beans, spring.context;
    opens hu.kleatech.jigsaw to spring.core;

    requires hu.kleatech.jigsaw.controller;
    requires hu.kleatech.jigsaw.service;
    requires spring.beans;
    requires spring.data.jpa;
    requires hibernate.core;
    requires spring.context;
    requires spring.core;
    requires spring.jdbc;
    requires spring.orm;
    requires thymeleaf;
    requires thymeleaf.extras.java8time;
    requires thymeleaf.spring;
    requires hu.kleatech.jigsaw.utils;
}