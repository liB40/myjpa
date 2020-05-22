package com.boob.parser;

/**
 * 解析器接口
 *
 * @param <T> 将文件解析为对应的配置类
 */
public interface Parser<T> {

    /**
     * 默认解析
     *
     * @return
     */
    T parse();


    /**
     * 通过其他方式解析
     *
     * @param obj 对象
     * @return
     */
    default T parse(Object obj) {
        return null;
    }

}
