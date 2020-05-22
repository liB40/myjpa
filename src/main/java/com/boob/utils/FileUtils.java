package com.boob.utils;

import java.io.File;
import java.util.Objects;

/**
 * 文件工具类
 */
public class FileUtils {

    /**
     * 文件分割符
     */
    public static final String FILE_SPECTATOR = "/";

    /**
     * 类路径
     */
    public static final String CLASS_PATH = Objects.requireNonNull(FileUtils.class.getClassLoader().getResource("./")).getPath();

    /**
     * 获取类路径下文件路径
     *
     * @param location
     * @return
     */
    public static String getPathWithClassPath(String location) {
        return andPath(CLASS_PATH, location);
    }

    /**
     * 获取类路径下文件
     *
     * @param location
     * @return
     */
    public static File getFileWithClassPath(String location) {
        return new File(andPath(CLASS_PATH, location));
    }

    /**
     * 根据路径获取文件
     *
     * @param location
     * @return
     */
    public static File getFile(String location) {
        return new File(location);
    }

    /**
     * 合并路径
     *
     * @param paths
     * @return
     */
    public static String andPath(String... paths) {
        StringBuilder sb = new StringBuilder();
        for (String path : paths) {
            sb.append(path).append(FILE_SPECTATOR);
        }
        return sb.toString().replace(FILE_SPECTATOR + FILE_SPECTATOR, FILE_SPECTATOR);
    }

    /**
     * 路径转java 类名
     *
     * @param path
     * @return
     */
    public static String pathToJava(String path) {
        return path.replace(FILE_SPECTATOR, ClassUtils.JAVA_SPECTATOR);
    }

    /**
     * java 类名转 路径
     *
     * @param javaPath
     * @return
     */
    public static String javaToPath(String javaPath) {
        return javaPath.replace(ClassUtils.JAVA_SPECTATOR, FILE_SPECTATOR);
    }

}
