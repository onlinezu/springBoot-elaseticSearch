package com.zu.springboot.elasticsearch.service.impl;

import com.zu.springboot.elasticsearch.service.OperationForDocService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Slf4j
@Service
public class OperationForDocServiceImpl implements OperationForDocService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public void createDoc() {
        IndexRequest indexRequest = new IndexRequest("produce");
        // 指定id,反之则默认生成
        // source是填充数据
        indexRequest.id("1").
                source("{\n" +
                        "  \"title\":\"kibana创建\",\n" +
                        "  \"price\":11.23,\n" +
                        "  \"description\":\"这个就是随便的描述一下而已\",\n" +
                        "  \"create_at\":\"20220105\"\n" +
                        "}", XContentType.JSON);
        try {
            // 参数1：索引请求对象
            // 参数2：请求配置对象
            // 以上对象的声明等操作的源头都基于此
           IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
           log.info("在对应索引下创建文档的结果为：" + indexResponse.status());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDoc() {
        try {
            // ctrl+p 看参数提示
            // 参数1：index名称  参数2：文档id
            UpdateRequest updateRequest = new UpdateRequest("produce","1");
            updateRequest.doc("{\n" +
                    "    \"title\":\"springBoot修改的值\"\n" +
                    "  }", XContentType.JSON);
            // 参数1：请求更新对象
            // 参数2：请求配置对象
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            log.info("修改文档的结果为:" + updateResponse.status());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDoc() {
        DeleteRequest deleteRequest = new DeleteRequest("produce", "1");
        try {
            // 参数1：请求删除对象
            // 参数2：请求配置对象
            DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            log.info("删除produce索引下的文档结果为：" + deleteResponse.status());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void queryDocById(String id) {
        GetRequest getRequest = new GetRequest("produce", "1");
        try {
            // 参数1：查询请求对象
            // 参数2：请求配置对象
            // 返回值：查询响应对象
            GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            log.info("根据索引、文档id查询到的文档map结果为：" + getResponse.getSource());
            log.info("根据索引、文档id查询到的文档mapAsString结果为：" + getResponse.getSourceAsString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
