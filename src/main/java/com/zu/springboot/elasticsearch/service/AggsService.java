package com.zu.springboot.elasticsearch.service;

public interface AggsService {

    // 基于字段进行分组聚合
    void aggsByTerm();

    // 求和、平均、最大、最小
    void aggsByReMix();
}
