package com.boob.scanner;

import com.boob.converter.NameConverter;
import com.boob.utils.ClassUtils;
import com.boob.utils.FileUtils;
import lombok.Data;

import java.io.File;
import java.util.Map;

/**
 * 文件扫描抽象层
 *
 * @param <T>
 */
@Data
public abstract class AbstractFileScanner<T> implements FileScanner<T> {

    /**
     * 名称转换器
     */
    protected NameConverter nameConverter;

    /**
     * 包名
     */
    protected String packageName;

    /**
     * 字节码缓存
     */
    protected Class classCache;

    @Override
    public void scan(String path, Map<Class, T> map) {
        File entityFile = FileUtils.getFileWithClassPath(FileUtils.javaToPath(path));
        if (!entityFile.isDirectory()) {
            throw new IllegalArgumentException(path + "不是文件夹");
        }

        scan(entityFile, map);
    }

    @Override
    public void scan(File modelFile, Map<Class, T> map) {
        File[] files = modelFile.listFiles();

        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isFile()) {
                scanAndPut(file, map);
            }
            //递归扫描
            scan(file, map);
        }
    }

    @Override
    public void scanAndPut(File file, Map<Class, T> map) {
        T t = scan(file);

        //该类不是T
        if (t == null) {
            return;
        }
        //先从缓存中拿
        Class clazz = getClassCache();
        if (clazz == null) {
            clazz = ClassUtils.getClassByPackageAndName(getPackageName(), ClassUtils.getClassNameByFileName(file.getName()));
        }
        map.put(clazz, t);
    }


    @Override
    public T scan(File file) {
        Class clazz = getClassAndSetCache(file);
        boolean isModel = check(clazz);
        if (!isModel) {
            return null;
        }
        T model = scanModel(clazz);
        setClassCache(null);
        return model;
    }

    /**
     * 检查字节码是否符合标准
     *
     * @param clazz
     * @return
     */
    protected abstract boolean check(Class clazz);

    /**
     * 通过文件名获取class并且放入缓存
     *
     * @param file
     * @return
     */
    protected Class getClassAndSetCache(File file) {
        Class clazz = ClassUtils.getClassByPackageAndName(getPackageName(), ClassUtils.getClassNameByFileName(file.getName()));
        //把字节码放入缓存
        setClassCache(clazz);
        return clazz;
    }

    /**
     * 扫描对应的模型
     *
     * @param clazz
     * @return
     */
    protected abstract T scanModel(Class clazz);


}
