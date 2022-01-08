package com.zu.springboot.elasticsearch.service.impl;

import com.zu.springboot.elasticsearch.service.AggsService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedDoubleTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.ParsedSum;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class AggsServiceImpl implements AggsService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    // 基于字段进行分组聚合
    @Override
    public void aggsByTerm() {
        SearchRequest searchRequest = new SearchRequest("produce");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 设置聚合操作
        sourceBuilder.aggregation(AggregationBuilders.terms("group_price").field("price"));
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            Aggregations aggregations = searchResponse.getAggregations();
            // double 根据实际返回对象的类型进行判断
            ParsedDoubleTerms terms = aggregations.get("group_price");
            List<? extends Terms.Bucket> buckets = terms.getBuckets();
            for (Terms.Bucket bucket : buckets) {
                log.info(bucket.getKey() + "-------" + bucket.getDocCount());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 求和、平均、最大、最小
    @Override
    public void aggsByReMix() {
        SearchRequest searchRequest = new SearchRequest("produce");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 设置聚合操作
        sourceBuilder.aggregation(AggregationBuilders.sum("sum_price").field("price")).size(0);
        searchRequest.source(sourceBuilder);

        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            Aggregations aggregations = searchResponse.getAggregations();
            // 求和等操作返回的只是一个数字，并不是keu value 形式的返回
            ParsedSum parsedSum = aggregations.get("sum_price");
            log.info("值为：" + parsedSum.getValue());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
