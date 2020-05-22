package com.boob.converter;

import com.boob.model.Sql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PersistSqlConverter extends AbstractSqlConverter implements SqlConverter {

    protected PersistSqlConverter() {

    }


    /**
     * 使用entity和参数,生成Sql
     *
     * @return
     */
    @Override
    public Sql getSql() {
        Sql sql = new Sql();
        List<Object> params = new ArrayList<>();
        Object param = condition.getParam();

        StringBuilder sb = new StringBuilder("insert into " + entity.getTableName() + "(");
        int count = 0;
        Map<String, String> columns = entity.getColumns();
        Field[] fields = entity.getClazz().getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(param);
                if (value != null) {
                    //设置参数值
                    params.add(value);
                    sb.append(columns.get(field.getName()))
                            .append(",");
                    count++;
                }
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(field + "不属于" + param.getClass());
            }
        }

        //删除最后一个逗号
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")").append(" values(");
        for (int i = 0; i < count; i++) {
            sb.append("?,");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");

        sql.setSql(sb.toString());
        sql.setParams(params);
        return sql;
    }

    @Override
    public void checkId() {

    }

    @Override
    public List<Object> getParams() {
        return null;
    }

    @Override
    public String getSqlStatement() {
        return null;
    }

}
