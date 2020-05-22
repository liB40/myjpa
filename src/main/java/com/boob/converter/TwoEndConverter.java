package com.boob.converter;

/**
 * 双向转换器
 *
 * @param <O>
 * @param <N>
 */
public interface TwoEndConverter<O, N> extends Converter<O, N> {

    /**
     * 把N对象转换为O对象
     *
     * @param n
     * @return
     */
    O reconvert(N n);
}
