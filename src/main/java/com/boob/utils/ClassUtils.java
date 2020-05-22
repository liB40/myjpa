package com.boob.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 反射工具类
 */
public class ClassUtils {


    /**
     * 泛型左括号
     */
    public static final String GENERIC_LEFT = "<";

    /**
     * 泛型右括号
     */
    public static final String GENERIC_RIGHT = ">";

    /**
     * 泛型右括号
     */
    public static final String JAVA_SPECTATOR = ".";

    /**
     * java后缀
     */
    public static final String JAVA_SUFFIX = ".java";

    /**
     * class后缀
     */
    public static final String CLASS_SUFFIX = ".class";


    /**
     * 根据类名获取字节码
     *
     * @param className
     * @return
     */
    public static Class getClassByName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(className + "不存在");
        }
    }

    /**
     * 根据包名,类名获取字节码
     *
     * @param names
     * @return
     */
    public static Class getClassByPackageAndName(String... names) {
        StringBuilder sb = new StringBuilder();
        for (String name : names) {
            sb.append(name)
                    .append(JAVA_SPECTATOR);
        }
        sb.deleteCharAt(sb.length() - 1);
        String className = sb.toString().replace(JAVA_SPECTATOR + JAVA_SPECTATOR, JAVA_SPECTATOR);
        return getClassByName(className);
    }

    /**
     * 根据文件名获取类名
     *
     * @param fileName
     * @return
     */
    public static String getClassNameByFileName(String fileName) {
        return fileName.replace(CLASS_SUFFIX, "")
                .replace(JAVA_SUFFIX, "");
    }


    /**
     * 根据文件名获取字节码
     *
     * @param fileName
     * @return
     */
    public static Class getClassByFileName(String fileName) {
        return getClassByName(getClassNameByFileName(fileName));
    }

    /**
     * 找出object对象中值不为null的属性
     *
     * @param fields
     * @param object
     * @return
     */
    public static List<Field> getFieldsIfNotNull(Field[] fields, Object object) {
        ArrayList<Field> notNullFields = new ArrayList<>();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(object);
                if (value != null) {
                    notNullFields.add(field);
                }
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(field + "不属于" + object.getClass());
            }
        }
        return notNullFields;
    }

    /**
     * 检查class 是否是实现了interfaceClass接口
     *
     * @param clazz
     * @param interfaceClass
     * @return
     */
    public static boolean checkImplementInterface(Class clazz, Class interfaceClass) {
        Class[] interfaces = clazz.getInterfaces();
        for (Class anInterface : interfaces) {
            if (anInterface == interfaceClass) {
                return true;
            }
        }
        return false;
    }


    /**
     * 拿到class 实现的interfaceClass泛型
     *
     * @param clazz
     * @param interfaceClass
     * @return
     */
    public static List<String> getInterfaceGeneric(Class clazz, Class interfaceClass) {
        Type genericInterface = getGenericInterface(clazz, interfaceClass);
        if (genericInterface == null) {
            return null;
        }

        String typeName = genericInterface.getTypeName();
        //取左边界
        int left = typeName.indexOf(GENERIC_LEFT);
        //取右边界
        int right = typeName.indexOf(GENERIC_RIGHT);

        if (left == -1 || right == -1) {
            return null;
        }

        String generics = typeName.substring(left + 1, right).replace(" ", "");
        String[] genericNames = generics.split(",");

        return Arrays.asList(genericNames);
    }

    /**
     * 拿到class 实现的带泛型的interfaceClass
     *
     * @param clazz
     * @param interfaceClass
     * @return
     */
    public static Type getGenericInterface(Class clazz, Class interfaceClass) {
        Type[] genericInterfaces = clazz.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface.getTypeName().contains(interfaceClass.getName())) {
                return genericInterface;
            }
        }
        return null;
    }

}
