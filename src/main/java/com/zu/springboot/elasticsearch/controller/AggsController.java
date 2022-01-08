package com.zu.springboot.elasticsearch.controller;

import com.zu.springboot.elasticsearch.service.AggsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/aggsOperation")
// 聚合查询-Aggregation-针对es对于数据做统计分析功能
// text类型是不支持聚合的
public class AggsController {

    @Autowired
    private AggsService aggsService;

    // 基于字段进行分组聚合
    @RequestMapping(value = "/aggsByTerm")
    public void aggsByTerm(){
        aggsService.aggsByTerm();
    }

    // 求和、平均、最大、最小
    @RequestMapping(value = "/aggsByReMix")
    public void aggsByReMix(){
        aggsService.aggsByReMix();
    }
}
