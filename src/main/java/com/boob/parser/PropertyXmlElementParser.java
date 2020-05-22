package com.boob.parser;

import com.boob.configuration.Property;
import com.boob.enums.XmlPropertiesNameEnum;
import org.dom4j.Element;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Property属性解析器
 */
public class PropertyXmlElementParser implements XmlElementParser<Property> {


    /**
     * 合法的properties属性和值
     */
    private static final Map<XmlPropertiesNameEnum, Set<String>> PROPERTIES_MAP = new HashMap<>();

    /**
     * 默认属性值集合
     */
    private static final Map<XmlPropertiesNameEnum, String> DEFAULT_PROPERTIES_MAP = new HashMap<>();

    static {

        //给属性设置默认值
        DEFAULT_PROPERTIES_MAP.put(XmlPropertiesNameEnum.SHOW_SQL, "true");
        DEFAULT_PROPERTIES_MAP.put(XmlPropertiesNameEnum.AUTO_DATABASE, "none");
        DEFAULT_PROPERTIES_MAP.put(XmlPropertiesNameEnum.NAME_RULE, "hump");

        HashSet<String> booleanSet = new HashSet<>(Arrays.asList("true", "false"));

        /**
         * 是否展示sql
         */
        PROPERTIES_MAP.put(XmlPropertiesNameEnum.SHOW_SQL, booleanSet);

        /**
         * 自动生成数据库属性
         */
        PROPERTIES_MAP.put(XmlPropertiesNameEnum.AUTO_DATABASE, new HashSet<>(Arrays.asList("create", "update", "none")));

        /**
         * 是否驼峰命名
         */
        PROPERTIES_MAP.put(XmlPropertiesNameEnum.NAME_RULE, new HashSet<>(Arrays.asList("hump")));

    }

    /**
     * 属性名
     */
    private static final String NAME = "name";
    /**
     * 属性值
     */
    private static final String VALUE = "value";

    /**
     * 解析得到集合
     *
     * @param elements
     * @return
     */
    @Override
    public List<Property> parseList(List<Element> elements) {
        List<Property> properties = new ArrayList<>();
        for (Element e : elements) {
            Property property = parse(e);
            properties.add(property);
        }
        setDefaultProperties(properties);
        return properties;
    }

    /**
     * 给properties设置默认值
     *
     * @param properties
     */
    private void setDefaultProperties(List<Property> properties) {
        Map<XmlPropertiesNameEnum, String> propertiesMap = properties.stream().collect(Collectors.toMap(p -> XmlPropertiesNameEnum.getEnumByName(p.getName()), Property::getValue));
        for (XmlPropertiesNameEnum propertiesNameEnum : DEFAULT_PROPERTIES_MAP.keySet()) {
            if (propertiesMap.containsKey(propertiesNameEnum)) {
                continue;
            }
            properties.add(new Property(propertiesNameEnum.getName(), DEFAULT_PROPERTIES_MAP.get(propertiesNameEnum)));
        }

    }

    /**
     * 解析得到属性
     *
     * @param element
     * @return
     */
    @Override
    public Property parse(Element element) {
        Property property = new Property();
        String name = element.attribute(NAME).getValue();
        String value = element.attribute(VALUE).getValue();

        property.setName(name);
        property.setValue(value);

        //检验合法性
        verifyProperty(property);
        return property;
    }

    /**
     * 检验property的合法性
     *
     * @param property
     * @return
     */
    private void verifyProperty(Property property) {
        String name = property.getName();
        String value = property.getValue();

        XmlPropertiesNameEnum propertiesNameEnum = XmlPropertiesNameEnum.getEnumByName(name);
        if (!PROPERTIES_MAP.containsKey(propertiesNameEnum)) {
            throw new IllegalArgumentException("属性 " + name + " 非法");
        }
        Set<String> values = PROPERTIES_MAP.get(propertiesNameEnum);
        if (!values.contains(value)) {
            throw new IllegalArgumentException("属性 " + name + " 存在非法值 " + value);
        }
    }

    //构造方法受保护
    protected PropertyXmlElementParser() {

    }
}
