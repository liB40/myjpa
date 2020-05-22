package com.boob.parser;

import org.dom4j.Element;

import java.util.List;

/**
 * xml元素解析
 *
 * @param <T>
 */
public interface XmlElementParser<T> extends Parser<T> {


    /**
     * 通过xml元素解析
     *
     * @param element
     * @return
     */
    T parse(Element element);

    /**
     * 通过xml元素解析得到集合
     *
     * @param elements
     * @return
     */
    List<T> parseList(List<Element> elements);

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
