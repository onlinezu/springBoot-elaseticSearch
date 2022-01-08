package com.zu.springboot.elasticsearch.service;

public interface EoService {

    // 通过实体类转换并写入elasticSearch
    void eoToEs();

    // 从elasticSearch中获取到数据并转换为实体对象
    void esToEo();
}
