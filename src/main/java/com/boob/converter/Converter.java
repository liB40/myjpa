package com.boob.converter;

/**
 * 转换器
 */
public interface Converter<O, N> {

    /**
     * 把 O对象转换为N对象
     *
     * @param o
     * @return
     */
    N convert(O o);


}
