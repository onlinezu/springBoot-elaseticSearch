package com.zu.springboot.elasticsearch.service.impl;

import com.zu.springboot.elasticsearch.service.OtherOperationService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
public class OtherOperationServiceImpl implements OtherOperationService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    // 分页 from-起始位置(start = (page - 1) * size)  size-每页记录展示数
    // 排序-sort-使用自己的排序之后，Es中默认的排序失效，并且分数也会显示为NaN
    // 返回指定字段-_source-两个数组include包含字段、exclude排除字段,并不建议两个数组同时赋值，建议只使用其中的一个

    @Override
    public void paging() {
        SearchRequest searchRequest = new SearchRequest("produce");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 创建高亮器
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        // 关闭字段匹配-全字段介可高亮，但是前提是字段类型为text，也就是可以分词
        highlightBuilder.requireFieldMatch(false).
            // 指定字段高亮
            field("description").field("title").
            // 指定前缀标签
            preTags("<span style='color:red'>").
            // 指定后置标签
            postTags("</span>");
        // 使用高亮的话就不使用matchAllQuery()
        sourceBuilder.query(QueryBuilders.termQuery("description","这")).
        // sourceBuilder.query(QueryBuilders.matchAllQuery()).
            from(0).
            size(10).
            sort("price", SortOrder.ASC).
            fetchSource(new String[]{"title"}, new String[]{}).
            highlighter(highlightBuilder);
        searchRequest.source(sourceBuilder);

        try {
            // 使用searchRequest对象进行操作
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            log.info("文档条数是：" + searchResponse.getHits().getTotalHits().value);
            log.info("文档得分是：" + searchResponse.getHits().getMaxScore());

            SearchHit[] searchHitsArray = searchResponse.getHits().getHits();
            for (SearchHit documentFields : searchHitsArray) {
                log.info("遍历文档的ID是：" + documentFields.getId());
                log.info("遍历文档的实际内容是：" + documentFields.getSourceAsString());
                // 获取高亮结果
                Map<String, HighlightField> highlightFieldsMap = documentFields.getHighlightFields();
                if(highlightFieldsMap.containsKey("description")){
                    log.info("获取到的高亮结果是：" + highlightFieldsMap.get("description").fragments()[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
