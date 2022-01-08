package com.zu.springboot.elasticsearch.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zu.springboot.elasticsearch.eo.EO;
import com.zu.springboot.elasticsearch.service.EoService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.util.DateUtil.now;

@Slf4j
@Service
public class EoServiceImpl implements EoService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    // 通过实体类转换并写入elasticSearch
    @Override
    public void eoToEs() {
        EO eo = new EO();
        eo.setCreate_at(now());
        eo.setDescription("牛奶");
        eo.setId(9);
        eo.setPrice(new BigDecimal(20));
        eo.setTitle("牛奶");

        IndexRequest indexRequest = new IndexRequest("produce");
        try {
            indexRequest.id(eo.getId().toString()).
                source(new ObjectMapper().writeValueAsString(eo), XContentType.JSON);
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            log.info("通过实体类创建文档的响应结果：" + indexResponse.status());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 从elasticSearch中获取到数据并转换为实体对象
    @Override
    public void esToEo() {
        SearchRequest searchRequest = new SearchRequest("produce");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchAllQuery())
        .from(0).size(5);
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            log.info("获取文档的总条数：" + searchResponse.getHits().getTotalHits().value);
            log.info("获取文档的总得分：" + searchResponse.getHits().getMaxScore());

            SearchHit[] searchHitsArray = searchResponse.getHits().getHits();

            List<EO> eoList = new ArrayList<EO>();
            for (SearchHit searchHit : searchHitsArray) {
                log.info("实际获得的文档数据：" + searchHit.getSourceAsString());
                // 对象转换
                EO eo = new ObjectMapper().readValue(searchHit.getSourceAsString(), EO.class);
                eoList.add(eo);
            }
            for (EO eo : eoList) {
                log.info("标题为：" + eo.getTitle());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
