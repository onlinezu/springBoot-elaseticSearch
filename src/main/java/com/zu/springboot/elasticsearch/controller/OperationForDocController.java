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

    // 创建文档
    @RequestMapping(value = "/createDoc", method = RequestMethod.POST)
    public void createDoc(){
        operationForDocService.createDoc();
    }

    // 更新文档
    @RequestMapping(value = "/updateDoc", method = RequestMethod.POST)
    public void updateDoc(){
        operationForDocService.updateDoc();
    }

    // 删除文档
    @RequestMapping(value = "/deleteDoc", method = RequestMethod.POST)
    public void deleteDoc(){
        operationForDocService.deleteDoc();
    }

    // 按照文档ID查询
    @RequestMapping(value = "/queryDocById", method = RequestMethod.POST)
    public void queryDocById(@RequestBody Map<Object, String> map){

        String id = map.get("id");
        log.info("id：" + id);
        operationForDocService.queryDocById(id);
    }

    // 查询所有文档
    @RequestMapping(value = "/queryAll", method = RequestMethod.POST)
    public void queryAll(){
        operationForDocService.queryAll();
    }

    @RequestMapping(value = "/queryByTerm", method = RequestMethod.POST)
    public void queryByTerm(){
        operationForDocService.queryByTerm();
    }
}
