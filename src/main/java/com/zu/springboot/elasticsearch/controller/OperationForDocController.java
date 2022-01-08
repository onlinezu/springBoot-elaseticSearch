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

    // 根据关键词查询
    @RequestMapping(value = "/queryByType", method = RequestMethod.POST)
    public void queryByType(){
        operationForDocService.queryByType();
    }

    // 过滤查询
    // query 关键字查询更适合于精确查询，同时也会计算文档的得分，也会根据文档得分进行返回
    // filter 用于大量的数据中筛选相关记录，不会进行得分计算，对于相对常使用的filter query结果会进行缓存
    // 如果同时使用了filter和query，filter的执行优先级更高
    @RequestMapping(value = "/filterQuery", method = RequestMethod.POST)
    public void filterQuery(){
        operationForDocService.filterQuery();
    }
}
