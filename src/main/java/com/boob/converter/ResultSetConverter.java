package com.boob.converter;

import com.boob.model.EntityAndCondition;

import java.sql.ResultSet;

/**
 * 查询结果转换器
 */
public interface ResultSetConverter extends Converter<ResultSet, Object> {

    void setEntityAndCondition(EntityAndCondition entityAndCondition);

    EntityAndCondition getEntityAndCondition();
}
