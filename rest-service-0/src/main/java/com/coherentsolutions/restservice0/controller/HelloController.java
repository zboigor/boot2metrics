package com.coherentsolutions.restservice0.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HelloController {

    private final DiscoveryClient discoveryClient;

    @Autowired
    public HelloController(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Rest service 0";
    }

    @GetMapping("/service-instances")
    public List<ServiceInstance> serviceInstancesByApplicationName() {
        List<ServiceInstance> instances = new ArrayList<>();

        for (String service: discoveryClient.getServices()) {
            instances.addAll(discoveryClient.getInstances(service));
        }
        return instances;
    }
}
