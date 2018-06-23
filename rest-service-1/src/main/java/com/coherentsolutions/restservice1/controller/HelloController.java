package com.coherentsolutions.restservice1.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/r")
@Slf4j
public class HelloController {

    private final DiscoveryClient discoveryClient;

    @Autowired
    public HelloController(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from rest-service-1";
    }

    @GetMapping("/task")
    public String task(@RequestParam("t") Integer t) {
        try {
            Thread.sleep(t * 1000);
        } catch (InterruptedException e) {
            log.error("Can't sleep", e);
        }
        return String.format("Slept successfully %d seconds in rest-service-1", t);
    }

    @GetMapping("/service-instances")
    public List<ServiceInstance> serviceInstancesByApplicationName() {
        List<ServiceInstance> instances = new ArrayList<>();

        for (String service : discoveryClient.getServices()) {
            instances.addAll(discoveryClient.getInstances(service));
        }
        return instances;
    }
}
