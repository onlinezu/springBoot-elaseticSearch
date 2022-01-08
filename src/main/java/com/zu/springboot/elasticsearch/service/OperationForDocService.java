package com.zu.springboot.elasticsearch.service;

public interface OperationForDocService {

    // 创建文档
    void createDoc();

    // 修改文档
    void updateDoc();

    // 删除文档
    void deleteDoc();

    // 根据文档ID查询
    void queryDocById(String id);

    // 查询所有文档
    void queryAll();

    // 根据关键词查询
    void queryByType();

    // 过滤查询
    void filterQuery();
}
