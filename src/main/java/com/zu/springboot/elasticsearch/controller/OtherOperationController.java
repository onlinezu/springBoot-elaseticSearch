package com.zu.springboot.elasticsearch.controller;

import com.zu.springboot.elasticsearch.service.OtherOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/otherOperation")
public class OtherOperationController {

    @Autowired
    private OtherOperationService otherOperationService;

    // 分页
    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public void paging(){
        otherOperationService.paging();
    }
}
