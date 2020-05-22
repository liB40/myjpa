package com.boob.parser;

import com.boob.parser.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 解析器工厂
 */
public class ParserFactory {

    /**
     * 解析器工厂
     */
    private static final Map<String, Parser> PARSER_MAP = new HashMap<>();

    static {
        PARSER_MAP.put(PersistenceUnitXmlElementParser.class.getName(),
                new PersistenceUnitXmlElementParser());
        PARSER_MAP.put(DataSourceXmlElementParser.class.getName(),
                new DataSourceXmlElementParser());
        PARSER_MAP.put(PropertyXmlElementParser.class.getName(),
                new PropertyXmlElementParser());
        PARSER_MAP.put(PersistenceDomParser.class.getName(),
                new PersistenceDomParser());
    }

    /**
     * 根据解析器字节码获取解析器
     *
     * @param clazz
     * @return
     */
    public static <T> T getParser(Class<T> clazz) {
        Parser parser = PARSER_MAP.get(clazz.getName());
        if (parser == null) {
            throw new IllegalArgumentException("解析器不存在");
        }
        return (T) parser;
    }

}
