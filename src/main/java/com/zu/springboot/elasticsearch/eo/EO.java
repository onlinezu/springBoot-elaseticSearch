package com.zu.springboot.elasticsearch.eo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EO {

    private Date create_at;

    private String title;

    private BigDecimal price;

    private String description;

    private Integer id;
}
