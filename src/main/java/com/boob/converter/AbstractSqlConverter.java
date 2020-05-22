package com.boob.converter;

import com.boob.model.Condition;
import com.boob.model.Entity;
import com.boob.model.EntityAndCondition;
import com.boob.model.Sql;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;

/**
 * SqlConverter抽象层
 */
public abstract class AbstractSqlConverter implements SqlConverter {

    /**
     * 日志类
     */
    protected static final Logger LOG = LoggerFactory.getLogger(AbstractSqlConverter.class);
    /**
     * 实体类信息
     */
    protected Entity entity;

    /**
     * 条件信息
     */
    protected Condition condition;

    @Override
    public Sql convert(EntityAndCondition entityAndCondition) {

        //给属性赋值
        this.condition = entityAndCondition.getCondition();
        this.entity = entityAndCondition.getEntity();

        //检查id
        checkId();
        //获取sql
        return getSql();
    }

    /**
     * 检查id属性,如为空,删查改不能继续进行,将会抛出异常
     * 默认检查对象中的id属性
     *
     * @return
     */
    public void checkId() {
        Object param = condition.getParam();
        try {
            //获取声明的id字段
            Field field = param.getClass().getDeclaredField(entity.getId());
            field.setAccessible(true);
            //获取id字段中的值
            Object value = field.get(param);
            if (value == null) {
                throw new IllegalArgumentException("id 属性为空,无法操作");
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException("id 属性为空,无法操作");
        }
    }


    /**
     * 查询删除根据id生成Sql
     *
     * @return
     */
    public Sql getSql() {
        Sql sql = new Sql();
        String sqlStatement = getSqlStatement();
        List<Object> params = getParams();

        sql.setSql(sqlStatement);
        sql.setParams(params);
        return sql;
    }


    /**
     * 获取参数
     *
     * @return
     */
    public abstract List<Object> getParams();

    /**
     * 获取sql语句
     *
     * @return
     */
    public abstract String getSqlStatement();
}
