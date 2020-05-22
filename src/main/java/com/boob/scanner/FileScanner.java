package com.boob.scanner;


import java.io.File;
import java.util.Map;

public interface FileScanner<T> {


    /**
     * 扫描文件得到结果
     *
     * @param file
     * @return
     */
    T scan(File file);


    /**
     * 通过路径扫描得到结果,放入map
     *
     * @param path
     * @param map
     */
    void scan(String path, Map<Class, T> map);


    /**
     * 通过文件夹扫描得到结果,放入容器
     *
     * @param file 文件
     * @param map  存储容器
     */
    void scan(File file, Map<Class, T> map);

    /**
     * 扫描文件得到结果,并放入map容器
     *
     * @param file
     * @param map
     */
    void scanAndPut(File file, Map<Class, T> map);


}
