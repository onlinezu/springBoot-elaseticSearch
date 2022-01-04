package com.zu.springboot.elasticsearch.config;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

    @Value("${ElasticSearch.address}")
    private String address;

    @Value("${ElasticSearch.port}")
    private String port;

    @Bean
    @Override
    // 6版本里面是有9200 9300两个端口的，9300是TCP协议进行连接，在es7版本里更加推荐Rest的方式，也就是使用9200端口进行使用
    // 9300除了与Java之间进行通信之外，还有用于集群之间的心跳监测，所以在Docker部署时，依然开放了9300
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder().
                connectedTo(address + ":" + port).build();
        return RestClients.create(clientConfiguration).rest();
    }
}
