package org.e4s.benchmark.service;

import org.springframework.stereotype.Service;

@Service
public class Greeting {

    public String sayHello(final String name){
        return "Hello, " + name;
    }


}
