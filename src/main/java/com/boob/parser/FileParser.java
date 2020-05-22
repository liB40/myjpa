package com.boob.parser;

/**
 * 文件解析工具
 *
 * @param <T>
 */
public interface FileParser<T> extends Parser<T> {

    /**
     * 通过文件解析
     *
     * @param location 文件路径
     * @return
     */
    T parse(String location);

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
