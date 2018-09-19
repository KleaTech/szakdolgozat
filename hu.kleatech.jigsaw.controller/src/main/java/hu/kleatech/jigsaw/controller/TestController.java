package hu.kleatech.jigsaw.controller;

import hu.kleatech.jigsaw.scripting.Scripts;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import hu.kleatech.jigsaw.service.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Controller
public class TestController {

    @Autowired private Service service;

    public void runTests() {
        System.out.println("Testing @Autowire on Service...");
        service.test();
        System.out.println("Testing @Autowired on Service which has @Autowired on an implementation of the Persistence interface...");
        service.testRepo();
        System.out.println("Running first test script...");
        CompletableFuture.runAsync(Scripts.getTest())
                .orTimeout(30, TimeUnit.SECONDS)
                .thenRun(() -> System.out.println("Running second test script..."))
                .thenRun(Scripts.getTestPut())
                .orTimeout(30, TimeUnit.SECONDS)
                .thenRun(() -> System.out.println("Running third test script..."))
                .thenRun(Scripts.getTestPutAndGet())
                .orTimeout(30, TimeUnit.SECONDS)
                .join();
    }
}
