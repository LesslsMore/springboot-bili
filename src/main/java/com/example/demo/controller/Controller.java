package com.example.demo.controller;

import com.example.demo.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Controller {
    @Autowired
    private QueryService queryService;
    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
    //    http://127.0.0.1:8088/hello?name=1%082%093%0a4%0b5%0c6%0d7%7f8
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        LOGGER.error("enterprise instance not found by the scope id: {}", name);
        return String.format("Hello %s!", name);
    }

    @GetMapping("/test")
    public String test(@RequestParam(value = "name", defaultValue = "World") String name) {
        LOGGER.error("enterprise instance not found by the scope id: {}", name);
        return String.format("Hello %s!", name);
    }
//    http://127.0.0.1:8088/q?name=fs&p=1&s=10
    @GetMapping("/query")
    public String query(@RequestParam(value = "name") String name,
                        @RequestParam int p,
                        @RequestParam int s) {
        LOGGER.error("enterprise instance not found by the scope id: {}", name);
        String query = queryService.query(name, p, s);
        return query;
    }
}