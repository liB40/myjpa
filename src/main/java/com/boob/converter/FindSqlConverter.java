package com.boob.converter;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询Sql转换器
 */
public class FindSqlConverter extends AbstractSqlConverter implements SqlConverter {

    protected FindSqlConverter() {

    }

    @Override
    public void checkId() {
        if (condition.getParam() == null) {
            throw new IllegalArgumentException("id 属性为空,无法查询");
        }
    }

    /**
     * 根据table 名和 id 获取
     */
    @Override
    public String getSqlStatement() {
        String tableName = entity.getTableName();
        String idColumn = entity.getId();
        return "select * from " + tableName + " where " + idColumn + " = ?";
    }

    @Override
    public List<Object> getParams() {
        ArrayList<Object> params = new ArrayList<>();
        params.add(condition.getParam());
        return params;
    }
}
