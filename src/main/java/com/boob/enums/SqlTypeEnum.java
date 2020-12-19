package com.boob.enums;

/**
 * @author jangbao
 * sql类型枚举类
 */
public enum SqlTypeEnum {

    /**
     * 查询
     */
    FIND,
    /**
     * 保存
     */
    PERSIST,
    /**
     * 修改
     */
    MERGE,
    /**
     * 删除
     */
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
