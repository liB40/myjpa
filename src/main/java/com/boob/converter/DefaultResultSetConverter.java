package com.boob.converter;

import com.boob.model.EntityAndCondition;

import java.sql.ResultSet;

/**
 * 默认查询结果转换器
 */
public class DefaultResultSetConverter implements ResultSetConverter {

    /**
     * 实体类信息和条件
     */
    private EntityAndCondition entityAndCondition;

    protected DefaultResultSetConverter() {

    }

    @Override
    public void setEntityAndCondition(EntityAndCondition entityAndCondition) {
        this.entityAndCondition = entityAndCondition;
    }

    @Override
    public EntityAndCondition getEntityAndCondition() {
        return this.entityAndCondition;
    }

    @Override
    public Object convert(ResultSet resultSet) {
        return null;
    }
}
