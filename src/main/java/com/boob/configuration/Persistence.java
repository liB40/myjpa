package com.boob.configuration;

import com.boob.factory.DefaultEntityManagerFactory;
import com.boob.factory.EntityManagerFactory;
import com.boob.parser.ParserFactory;
import com.boob.parser.PersistenceDomParser;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * persistence总配置类
 */
@Data
public class Persistence {

    /**
     * 日志信息
     */
    private static final Logger LOG = LoggerFactory.getLogger(Persistence.class);

    /**
     * persistenceUnitMap 存放了所有的persistenceUnit
     */
    private Map<String, PersistenceUnit> persistenceUnitMap = new HashMap<>();

    /**
     * 缓存
     */
    private static Persistence cache;

    /**
     * 通过 unitName 创建entityManagerFactory
     *
     * @param unitName
     * @return
     */
    public static EntityManagerFactory createEntityManagerFactory(String unitName) {

        if (cache == null) {
            cache = ParserFactory.getParser(PersistenceDomParser.class).parse();
        }

        /**
         * 设置配置
         */
        PersistenceUnit persistenceUnit = cache.getPersistenceUnitMap().get(unitName);
        return new DefaultEntityManagerFactory(persistenceUnit);

    }
}
