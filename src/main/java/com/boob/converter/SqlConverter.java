package com.boob.converter;


import com.boob.model.EntityAndCondition;
import com.boob.model.Sql;

/**
 * sql 转换器,把Entity转换为sql语句
 */
public interface SqlConverter extends Converter<EntityAndCondition, Sql> {


}
