package com.zu.springboot.elasticsearch.controller;

import com.zu.springboot.elasticsearch.service.CreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/operation")
public class CreateController {

    @Autowired
    private CreateService createService;

    // 创建Index与Mapping
    @RequestMapping(value = "/createIndexAndMapping")
    public void createMethod(){
        createService.create();
    }

    // 删除索引
    @RequestMapping(value = "/delete")
    public void deleteMethod(){
        createService.delete();
    }
}
