package com.boob.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库和java类型转换
 */
public class TypeHandler {

    /**
     * 日志类
     */
    private static final Logger LOG = LoggerFactory.getLogger(TypeHandler.class);


    /**
     * 设置ps的参数
     *
     * @param ps    PreparedStatement
     * @param index 设置参数的位置
     * @param param 参数
     */
    public static void handleParam(PreparedStatement ps, int index, Object param) throws SQLException {
        //获取java 类型
        Class<?> clazz = param.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            ps.setInt(index, (Integer) param);
        } else if (clazz == String.class || clazz == char.class) {
            ps.setString(index, (String) param);
        } else if (clazz == float.class || clazz == Float.class) {
            ps.setFloat(index, (Float) param);
        } else if (clazz == long.class || clazz == Long.class) {
            ps.setLong(index, (Long) param);
        } else if (clazz == double.class || clazz == Double.class) {
            ps.setDouble(index, (Double) param);
        } else {
            //TODO 完善其他的类型转换
        }
    }


    /**
     * 进行结果集的类型转换
     */
    public static Object handleResultSet(ResultSet rs, Class clazz, String columnName) throws SQLException {
        Object object = null;
        if (clazz == int.class || clazz == Integer.class) {
            object = rs.getInt(columnName);
        } else if (clazz == String.class || clazz == char.class) {
            object = rs.getString(columnName);
        } else if (clazz == float.class || clazz == Float.class) {
            object = rs.getFloat(columnName);
        } else if (clazz == long.class || clazz == Long.class) {
            object = rs.getLong(columnName);
        } else if (clazz == double.class || clazz == Double.class) {
            object = rs.getDouble(columnName);
        } else {
            //TODO 完善其他的类型转换
        }
        return object;
    }
}
