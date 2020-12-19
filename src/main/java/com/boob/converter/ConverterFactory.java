package com.boob.converter;

import com.boob.enums.SqlTypeEnum;
import com.boob.model.EntityAndCondition;

import java.util.HashMap;
import java.util.Map;

public class ConverterFactory {

    /**
     * 名称转换器map
     */
    private static final Map<String, NameConverter> NAME_CONVERTER_MAP = new HashMap<>();

    /**
     * sql转换器map
     */
    private static final Map<SqlTypeEnum, SqlConverter> SQL_CONVERTER_MAP = new HashMap<>();

    /**
     * 默认resultSet转换器
     */
    private static final ResultSetConverter RESULT_SET_CONVERTER = new DefaultResultSetConverter();

    static {
        NAME_CONVERTER_MAP.put("hump", new HumpNameConverter());

        SQL_CONVERTER_MAP.put(SqlTypeEnum.FIND, new FindSqlConverter());
        SQL_CONVERTER_MAP.put(SqlTypeEnum.PERSIST, new PersistSqlConverter());
        SQL_CONVERTER_MAP.put(SqlTypeEnum.MERGE, new MergeSqlConverter());
        SQL_CONVERTER_MAP.put(SqlTypeEnum.REMOVE, new RemoveSqlConverter());

    }

    /**
     * 获取名称转换器
     *
     * @param name
     * @return
     */
    public static NameConverter getNameConverterByName(String name) {

        return NAME_CONVERTER_MAP.get(name);
    }

    /**
     * 获取sql转换器
     *
     * @param sqlConverterEnum
     * @return
     */
    public static SqlConverter getSqlConverterByType(SqlTypeEnum sqlConverterEnum) {
        return SQL_CONVERTER_MAP.get(sqlConverterEnum);
    }

    /**
     * 获取resultSet转换器
     *
     * @param entityAndCondition 设置实体类信息和条件
     * @return
     */
    public static ResultSetConverter getResultSetConverter(EntityAndCondition entityAndCondition) {
        ResultSetConverter resultSetConverter = RESULT_SET_CONVERTER;
        resultSetConverter.setEntityAndCondition(entityAndCondition);
        return resultSetConverter;

    }
}
