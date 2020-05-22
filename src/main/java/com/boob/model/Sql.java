package com.boob.model;

import lombok.Data;

import java.util.List;

/**
 * sql类
 */
@Data
public class Sql {

    /**
     * sql语句
     */
    private String sql;

    /**
     * 对应的参数
     */
    private List<Object> params;

}
