package com.boob.parser;

import java.lang.reflect.Method;

/**
 * 类解析接口
 *
 * @param <T> 生成的结果
 */
public interface ClassParser<T> extends Parser<T> {

    /**
     * 通过配置类解析
     *
     * @param clazz
     * @param method
     * @return
     */
    T parse(Class clazz, Method method);

    /**
     * 默认解析
     *
     * @return
     */
    @Override
    default T parse() {
        return null;
    }


}
