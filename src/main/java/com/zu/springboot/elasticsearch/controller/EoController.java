package com.zu.springboot.elasticsearch.controller;

import com.zu.springboot.elasticsearch.service.EoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/eoOperation")
public class EoController {

    @Autowired
    private EoService eoService;

    // 通过实体类转换并写入elasticSearch
    @RequestMapping(value = "/eoToEs")
    public void eoToEs(){
        eoService.eoToEs();
    }

    // 从elasticSearch中获取到数据并转换为实体对象
    @RequestMapping(value = "/esToEo")
    public void esToEo(){
        eoService.esToEo();
    }
}
