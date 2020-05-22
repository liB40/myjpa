package com.boob.parser;

import com.boob.configuration.DataSource;
import com.boob.configuration.PersistenceUnit;
import com.boob.configuration.Property;
import com.boob.enums.XmlPropertiesNameEnum;

import com.boob.utils.XmlUtils;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 使用Element解析生成PersistenceUnit
 */
public class PersistenceUnitXmlElementParser implements XmlElementParser<PersistenceUnit> {


    /**
     * 名字
     */
    private static final String NAME = "name";
    /**
     * 事务类型
     */
    private static final String TRANSACTION_TYPE = "transaction-type";
    /**
     * 数据库名字
     */
    private static final String DATASOURCE_NAME = "datasource";
    /**
     * 所有属性名字
     */
    private static final String PROPERTIES_NAME = "properties";
    /**
     * 单个属性名字
     */
    private static final String PROPERTY_NAME = "property";
    /**
     * 要扫描的entity包
     */
    private static final String ENTITY_SCAN = "entity-scan";
    /**
     * 要扫描的dao包
     */
    private static final String DAO_SCAN = "dao-scan";

    /**
     * 解析多个persistenceUnit
     *
     * @param elements
     * @return
     */
    @Override
    public List<PersistenceUnit> parseList(List<Element> elements) {
        ArrayList<PersistenceUnit> persistenceUnits = new ArrayList<>();
        for (Element element : elements) {
            PersistenceUnit persistenceUnit = parse(element);
            persistenceUnits.add(persistenceUnit);
        }
        return persistenceUnits;
    }

    /**
     * 解析单个persistenceUnit
     *
     * @param element
     * @return
     */
    @Override
    public PersistenceUnit parse(Element element) {
        PersistenceUnit persistenceUnit = new PersistenceUnit();

        DataSourceXmlElementParser dataSourceXmlElementParser = ParserFactory.getParser(DataSourceXmlElementParser.class);
        PropertyXmlElementParser propertyXmlElementParser = ParserFactory.getParser(PropertyXmlElementParser.class);

        String name = XmlUtils.getNotNullValue(element, NAME);
        String txType = XmlUtils.getNotNullValue(element, TRANSACTION_TYPE);
        DataSource dataSource = dataSourceXmlElementParser.parse(element.element(DATASOURCE_NAME));
        List<Property> properties = propertyXmlElementParser.parseList(element.element(PROPERTIES_NAME).elements(PROPERTY_NAME));
        String entityScan = XmlUtils.getNotNullValueByChildElement(element, ENTITY_SCAN);
        String daoScan = XmlUtils.getNotNullValueByChildElement(element, DAO_SCAN);
        Map<XmlPropertiesNameEnum, Property> propertyMap = properties.stream().collect(Collectors.toMap(p -> XmlPropertiesNameEnum.getEnumByName(p.getName()), p -> p));

        persistenceUnit.setDataSource(dataSource);
        persistenceUnit.setPropertyMap(propertyMap);
        persistenceUnit.setName(name);
        persistenceUnit.setTransactionType(txType);
        persistenceUnit.setEntityScan(entityScan);
        persistenceUnit.setDaoScan(daoScan);

        return persistenceUnit;
    }

    //构造方法受保护
    protected PersistenceUnitXmlElementParser() {

    }

}
