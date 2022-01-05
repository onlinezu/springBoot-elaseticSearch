package com.zu.springboot.elasticsearch.service;

public interface OperationForDocService {

    // 创建文档
    void createDoc();

    // 修改文档
    void updateDoc();

    // 删除文档
    void deleteDoc();

    void queryDocById(String id);
}
