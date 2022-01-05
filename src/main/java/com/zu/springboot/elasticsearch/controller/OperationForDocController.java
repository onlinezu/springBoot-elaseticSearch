package com.zu.springboot.elasticsearch.controller;


import com.zu.springboot.elasticsearch.service.OperationForDocService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/opera4doc")
public class OperationForDocController {

    @Autowired
    private OperationForDocService operationForDocService;

    @RequestMapping(value = "/createDoc", method = RequestMethod.POST)
    public void createDoc(){
        operationForDocService.createDoc();
    }

    @RequestMapping(value = "/updateDoc", method = RequestMethod.POST)
    public void updateDoc(){
        operationForDocService.updateDoc();
    }

    @RequestMapping(value = "/deleteDoc", method = RequestMethod.POST)
    public void deleteDoc(){
        operationForDocService.deleteDoc();
    }

    @RequestMapping(value = "/queryDocById", method = RequestMethod.POST)
    public void queryDocById(@RequestBody Map<Object, String> map){

        String id = map.get("id");
        log.info("id：" + id);
        operationForDocService.queryDocById(id);
    }
}
