package com.boob.model;

import lombok.Data;

import java.util.Map;

/**
 * entity实体类
 */
@Data
public class Entity {

    /**
     * 对应数据库表名
     */
    private String tableName;

    /**
     * 对应字节码
     */
    private Class clazz;

    /**
     * id java字段名
     */
    private String id;

    /**
     * 各java字段名以及对应的数据库字段名
     */
    private Map<String, String> columns;

}
