package com.boob.converter;

import java.util.ArrayList;
import java.util.List;

public class RemoveSqlConverter extends AbstractSqlConverter implements SqlConverter {


    protected RemoveSqlConverter() {

    }

    @Override
    public void checkId() {
        if (condition.getParam() == null) {
            throw new IllegalArgumentException("id 属性为空,无法删除");
        }
    }


    /**
     * 根据table 名和 id 获取
     */
    @Override
    public String getSqlStatement() {
        String tableName = entity.getTableName();
        String idColumn = entity.getId();
        return "delete  from " + tableName + " where " + idColumn + " = ?";
    }

    @Override
    public List<Object> getParams() {
        ArrayList<Object> params = new ArrayList<>();
        params.add(condition.getParam());
        return params;
    }
}
