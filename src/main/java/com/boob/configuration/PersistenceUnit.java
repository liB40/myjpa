package com.boob.configuration;

import com.boob.enums.XmlPropertiesNameEnum;
import lombok.Data;

import java.util.Map;

/**
 * 单个persistenceUnit配置类
 */
@Data
public class PersistenceUnit {

    private String name;

    private String transactionType;

    private DataSource dataSource;

    private Map<XmlPropertiesNameEnum, Property> propertyMap;

    private String entityScan;

    private String daoScan;
}
