package hu.kleatech.jigsaw.service;

import hu.kleatech.jigsaw.persistence.Persistence;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class Service {
    public void test() {
        System.out.println("Test successful");
    }

    @Autowired
    private Persistence<Object> persistence;
    public void testRepo() {
        persistence.save("Test");
        String string = (String) persistence.get(new String("Test"));
        System.out.println(string==null?"Test failed":"Test successful");
    }
}
