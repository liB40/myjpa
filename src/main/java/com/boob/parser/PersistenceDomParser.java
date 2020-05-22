package com.boob.parser;

import com.boob.configuration.Persistence;
import com.boob.configuration.PersistenceUnit;
import com.boob.utils.XmlUtils;
import org.dom4j.Element;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * EntityManagerFactory解析器
 */
public class PersistenceDomParser implements FileParser<Persistence> {


    /**
     * 默认配置文件路径
     */
    private static final String DEFAULT_LOCATION = "persistence.xml";

    /**
     * 根节点名称
     */
    private static final String ROOT_NAME = "persistence";

    /**
     * persistenceUnit名称
     */
    private static final String PERSISTENCE_UNIT_NAME = "persistence-unit";

    /**
     * 默认路径解析
     *
     * @return
     */
    @Override
    public Persistence parse() {
        return parse(DEFAULT_LOCATION);
    }

    /**
     * 解析文件生成EntityManagerFactory配置类
     *
     * @param location 文件路径
     * @return
     */
    @Override
    public Persistence parse(String location) {
        Element root = XmlUtils.getRootElementWithClassPath(location);
        return parseRoot(root);
    }

    /**
     * 解析根节点
     *
     * @param root
     */
    private Persistence parseRoot(Element root) {

        Persistence persistence = new Persistence();
        if (!ROOT_NAME.equals(root.getName())) {
            throw new RuntimeException("配置文件有误,请配置根节点为 persistence");
        }
        List<Element> elements = root.elements(PERSISTENCE_UNIT_NAME);
        Map<String, PersistenceUnit> persistenceUnitMap = parsePersistenceUnits(elements);
        persistence.setPersistenceUnitMap(persistenceUnitMap);
        return persistence;
    }

    /**
     * 解析所有 persistenceUnit
     *
     * @param elements
     * @return
     */
    private Map<String, PersistenceUnit> parsePersistenceUnits(List<Element> elements) {
        if (elements == null) {
            throw new IllegalArgumentException("persistence-unit 配置不能为空");
        }
        List<PersistenceUnit> persistenceUnits = ParserFactory.getParser(PersistenceUnitXmlElementParser.class).parseList(elements);

        return persistenceUnits.stream().collect(Collectors.toMap(PersistenceUnit::getName, p -> p));
    }

    //构造方法受保护
    protected PersistenceDomParser() {

    }
}
