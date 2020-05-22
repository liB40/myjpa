package com.boob.converter;

import com.boob.model.Sql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MergeSqlConverter extends AbstractSqlConverter implements SqlConverter {

    protected MergeSqlConverter() {

    }

    /**
     * 使用entity和参数,生成sql
     *
     * @return
     */
    @Override
    public Sql getSql() {
        Sql sql = new Sql();
        ArrayList<Object> params = new ArrayList<>();
        Object idParam = null;
        StringBuilder sb = new StringBuilder("update ")
                .append(entity.getTableName())
                .append(" set ");
        Field[] fields = this.entity.getClazz().getDeclaredFields();
        for (Field field : fields) {

            //开放权限
            field.setAccessible(true);
            //获取属性值
            Object value = null;
            try {
                value = field.get(condition.getParam());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            //id属性记录下来
            if (field.getName().equals(entity.getId())) {
                idParam = value;
                continue;
            }
            if (value == null) {
                sb.append(entity.getColumns().get(field.getName()))
                        .append(" = null ,");
            } else {
                sb.append(entity.getColumns().get(field.getName()))
                        .append(" = ? ,");
                //不为空则加入参数数组
                params.add(value);
            }
        }
        //删除最后一个逗号
        sb.deleteCharAt(sb.length() - 1);
        sb.append("where ")
                .append(entity.getId()).append(" = ?");

        params.add(idParam);

        sql.setSql(sb.toString());
        sql.setParams(params);
        return sql;
    }

    @Override
    public String getSqlStatement() {
        return null;
    }

    @Override
    public List<Object> getParams() {
        return null;
    }
}
