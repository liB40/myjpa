package com.boob.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 执行条件
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Condition {

    /**
     * 参数
     */
    private Object param;

    /**
     * 返回值
     */
    private Object returnValue;

    public Condition(Object param, Class clazz) {
        this.param = param;
        try {
            this.returnValue = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException(clazz + " 无法创建实例");
        }
    }

}
