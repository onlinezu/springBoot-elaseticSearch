package com.zu.springboot.elasticsearch.controller;

import com.zu.springboot.elasticsearch.service.CreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/create")
public class CreateController {

    @Autowired
    private CreateService createService;

    @RequestMapping(value = "/indexAndMapping")
    public void createMethod(){
        createService.create();
    }
}
