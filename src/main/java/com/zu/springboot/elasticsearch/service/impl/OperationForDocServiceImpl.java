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
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.event.WindowFocusListener;
import java.io.IOException;
@Slf4j
@Service
public class OperationForDocServiceImpl implements OperationForDocService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    // 创建文档
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

    // 更新文档
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

    // 删除文档
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

    // 按照文档ID查询某一个文档
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

    @Override
    public void queryAll() {

        // 不定长的参数-索引
        SearchRequest searchRequest = new SearchRequest("produce");
        // 但是指定完索引之后，查询条件是否需要补充
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 查询全部
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(sourceBuilder);
        try {
            // 参数1：查询所有请求对象
            // 参数2：请求配置对象
            // 返回值就是SearchResponse
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            log.info("整个文档的数据量为：" + searchResponse.getHits().getTotalHits().value);
            log.info("文档的最大得分为：" + searchResponse.getHits().getMaxScore());
            // 获取文档每一条数据
            SearchHit[] hitsArray = searchResponse.getHits().getHits();
            for (SearchHit hit : hitsArray) {
                String id = hit.getId();
                log.info("遍历Id结果：" + id);
                log.info("遍历数据结果为：" + hit.getSourceAsString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 实现关键词查询-term
    @Override
    public void queryByTerm() {
        SearchRequest searchRequest = new SearchRequest("produce");
        // 但是指定完索引之后，查询条件是否需要补充
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 查询全部
        sourceBuilder.query(QueryBuilders.termQuery("description","这"));
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            log.info("文档的条数是：" + searchResponse.getHits().getTotalHits().value);
            log.info("文档的得分是" + searchResponse.getHits().getMaxScore());
            SearchHit[] hitsArray = searchResponse.getHits().getHits();
            for (SearchHit documentFields : hitsArray) {
                log.info("遍历获取的文档Id为：" + documentFields.getId());
                log.info("遍历获取的字段结果为：" + documentFields.getSourceAsString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
