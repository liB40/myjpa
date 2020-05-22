package com.boob.enums;

/**
 * xml中属性的名字枚举类
 */
public enum XmlPropertiesNameEnum {

    SHOW_SQL("show_sql"),
    AUTO_DATABASE("auto_database"),
    NAME_RULE("name_rule"),
    ;

    private String name;

    private XmlPropertiesNameEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * 根据名字获取
     *
     * @param name
     * @return
     */
    public static XmlPropertiesNameEnum getEnumByName(String name) {
        for (XmlPropertiesNameEnum value : XmlPropertiesNameEnum.values()) {
            if (value.getName().equals(name)) {
                return value;
            }
        }
        return null;
    }
}
