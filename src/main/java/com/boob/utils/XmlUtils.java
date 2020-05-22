package com.boob.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * XML解析工具
 */
public class XmlUtils {

    /**
     * 日志类
     */
    private static final Logger LOG = LoggerFactory.getLogger(XmlUtils.class);

    /**
     * 根据文件加类路径获取Document
     *
     * @param location 文件路径
     * @return 解析后的Document
     */
    public static Document getDocumentWithClassPath(String location) {

        //获取类路径下文件
        File file = FileUtils.getFileWithClassPath(location);
        return getDocument(file);
    }

    /**
     * 根据文件路径获取Document
     *
     * @param location 文件路径
     * @return 解析后的Document
     */
    public static Document getDocument(String location) {

        //获取类路径下文件
        File file = FileUtils.getFile(location);
        return getDocument(file);
    }

    /**
     * 根据文件获取Document
     *
     * @param file 文件
     * @return 解析后的Document
     */
    public static Document getDocument(File file) {
        try {
            SAXReader reader = new SAXReader();
            return reader.read(file);
        } catch (DocumentException e) {
            throw new IllegalArgumentException("解析文件失败,请检查文件路径:" + file.getPath());
        }
    }

    /**
     * 根据文件路径加类路径获取XML根元素
     *
     * @param location 文件路径
     * @return 解析后的XML根元素
     */
    public static Element getRootElementWithClassPath(String location) {
        return getDocumentWithClassPath(location).getRootElement();
    }

    /**
     * 根据文件路径获取XML根元素
     *
     * @param location 文件路径
     * @return 解析后的XML根元素
     */
    public static Element getRootElement(String location) {
        return getDocument(location).getRootElement();
    }

    /**
     * 根据文件路径加类路径获取XML根元素
     *
     * @param file 文件
     * @return 解析后的XML根元素
     */
    public static Element getRootElement(File file) {
        return getDocument(file).getRootElement();
    }

    /**
     * 获取元素中非空属性,默认属性名value
     *
     * @param element
     * @return
     */
    public static String getNotNullValue(Element element) {
        return getNotNullValue(element, "value");
    }

    /**
     * 获取元素中非空attribute属性
     *
     * @param element
     * @return
     */
    public static String getNotNullValue(Element element, String attribute) {
        String value = element.attribute(attribute).getValue();
        if (value == null) {
            throw new IllegalArgumentException(element.getName() + attribute + "不能为空");
        }
        return value;
    }

    /**
     * 获取字元素中的值
     *
     * @param element      父元素
     * @param childElement 字元素名字
     * @return
     */
    public static String getNotNullValueByChildElement(Element element, String childElement) {
        Element child = element.element(childElement);
        if (child == null) {
            throw new RuntimeException("请配置" + childElement);
        }

        return getNotNullValue(child);
    }
}
