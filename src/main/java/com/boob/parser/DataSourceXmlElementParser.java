package com.boob.parser;

import com.boob.configuration.DataSource;

import com.boob.utils.XmlUtils;
import org.dom4j.Element;

import java.util.List;

/**
 * 数据源解析器
 */
public class DataSourceXmlElementParser implements XmlElementParser<DataSource> {


    /**
     * URL
     */
    private static final String URL = "url";
    /**
     * driver
     */
    private static final String DRIVER = "driver";
    /**
     * username
     */
    private static final String USERNAME = "username";
    /**
     * password
     */
    private static final String PASSWORD = "password";

    @Override
    public List<DataSource> parseList(List<Element> elements) {
        return null;
    }

    @Override
    public DataSource parse(Element element) {

        DataSource dataSource = new DataSource();
        String url = XmlUtils.getNotNullValueByChildElement(element, URL);
        String driver = XmlUtils.getNotNullValueByChildElement(element, DRIVER);
        String username = XmlUtils.getNotNullValueByChildElement(element, USERNAME);
        String password = XmlUtils.getNotNullValueByChildElement(element, PASSWORD);

        return dataSource.setUrl(url)
                .setDriver(driver)
                .setUsername(username)
                .setPassword(password);
    }

    //构造方法受保护
    protected DataSourceXmlElementParser() {

    }
}
