package com.boob.converter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class RemoveSqlConverter extends AbstractSqlConverter implements SqlConverter {


    protected RemoveSqlConverter() {

    }

    @Override
    public List<Object> getParams() {
        ArrayList<Object> params = new ArrayList<>();
        Object param = condition.getParam();
        Class clazz = entity.getClazz();
        try {
            Field idField = clazz.getDeclaredField(entity.getId());
            idField.setAccessible(true);
            params.add(idField.get(param));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            LOG.warn(e.getMessage());
        }
        return params;
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
}
