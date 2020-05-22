package com.boob.model;

import lombok.Data;

/**
 * dao实体类
 */
@Data
public class Dao {

    /**
     * 对应字节码
     */
    private Class clazz;

    /**
     * 实体类字节码
     */
    private Class entityClass;

    /**
     * 实体类id字节码
     */
    private Class idClass;
}
