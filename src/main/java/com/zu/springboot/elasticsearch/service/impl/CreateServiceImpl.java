package com.zu.springboot.elasticsearch.service.impl;

import com.zu.springboot.elasticsearch.service.CreateService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class CreateServiceImpl implements CreateService {

    // 注入操作es客户端
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public void create() {
        // 创建索引
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("produce");

        // 创建映射
        // 参数1.指定映射
        // 参数2.直接调用XContentType.JSON枚举
        createIndexRequest.mapping("{\n" +
            "    \"properties\": {\n" +
            "      \"id\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"title\":{\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"price\":{\n" +
            "        \"type\":\"double\"\n" +
            "      },\n" +
            "      \"create_at\":{\n" +
            "        \"type\":\"date\"\n" +
            "      },\n" +
            "      \"description\":{\n" +
            "        \"type\": \"text\"\n" +
            "      }\n" +
            "    }\n" +
            "  }", XContentType.JSON);
        try {
            // 调用索引创建方法
            // 1.参数1创建索引的请求对象--createIndexRequest
            // 2.请求配置对象--requestOptions
            // 实际上第一行创建索引的源头就在这
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);

            log.info("使用客户端创建对象结果:" + createIndexResponse.isAcknowledged());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete() {
        try {
            // 参数1：删除索引对象  参数2：请求配置对象
            AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().
                    delete(new DeleteIndexRequest("produce"),RequestOptions.DEFAULT);

            log.info("删除索引结果为：" + acknowledgedResponse.isAcknowledged());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
