package com.showga.databaseMySQL;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping(path = "hello")
    public String helloPal() {
        return "Hello, Pal !";
    }

}
