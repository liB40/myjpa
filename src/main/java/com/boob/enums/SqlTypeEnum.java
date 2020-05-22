package com.boob.enums;

/**
 * sql类型枚举类
 */
public enum SqlTypeEnum {

    FIND,
    PERSIST,
    MERGE,
    REMOVE,
    ;

    /**
     * sql是不是读类型
     *
     * @return
     */
    public boolean isRead() {
        return this == FIND;
    }

    /**
     * sql是不是写类型
     *
     * @return
     */
    public boolean isWrite() {
        return this != FIND;
    }
}
