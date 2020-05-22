package com.boob.utils;

import java.util.List;

/**
 * 字符串工具类
 */
public class StringUtils {

    /**
     * 默认分割符
     */
    public static final String DEFAULT_SPECTATOR = ",";

    /**
     * 默认占位符
     */
    public static final String DEFAULT_PLACEHOLDER = "#spe";

    /**
     * 把旧的字符串中的占位符换成数组中的值
     *
     * @param oldString   旧字符串
     * @param names       数组
     * @param placeholder 占位符
     * @return 替换后的字符串
     */
    public static String replaceByList(String oldString, List<String> names, String placeholder) {
        for (int i = 0; i < names.size(); i++) {
            //在分割符后加上 i 标识
            oldString = oldString.replace(placeholder + i, names.get(i));
        }
        return oldString;
    }

    /**
     * 使用默认分割符,占位符操作
     *
     * @param oldString
     * @param names
     * @return
     */
    public static String replaceByList(String oldString, List<String> names) {
        return replaceByList(oldString, names, DEFAULT_PLACEHOLDER);
    }
}
