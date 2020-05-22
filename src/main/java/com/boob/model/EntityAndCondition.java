package com.boob.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 实体类和执行条件
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityAndCondition {

    /**
     * 实体类
     */
    private Entity entity;

    /**
     * 执行条件
     */
    private Condition condition;

}
