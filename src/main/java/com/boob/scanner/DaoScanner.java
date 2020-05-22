package com.boob.scanner;

import com.boob.converter.NameConverter;
import com.boob.model.Dao;
import com.boob.repository.JpaRepository;
import com.boob.utils.ClassUtils;
import lombok.Data;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * dao扫描器
 */
@Data
public class DaoScanner extends AbstractFileScanner<Dao> implements FileScanner<Dao> {

    /**
     * 名称转换器
     */
    private NameConverter nameConverter;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 需要实现的interface
     */
    private static final Class ASSIGN_INTERFACE = JpaRepository.class;

    public DaoScanner() {
    }

    public DaoScanner(NameConverter nameConverter, String packageName) {
        this.nameConverter = nameConverter;
        this.packageName = packageName;
    }


    @Override
    protected boolean check(Class clazz) {
        //是否实现了 JpaRepository 接口
        return ClassUtils.checkImplementInterface(clazz, ASSIGN_INTERFACE);
    }

    @Override
    protected Dao scanModel(Class clazz) {
        Dao dao = new Dao();

        List<String> generics = ClassUtils.getInterfaceGeneric(clazz, ASSIGN_INTERFACE);
        if (generics == null) {
            throw new IllegalArgumentException("请声明 " + clazz.getName() + " 实现的 JpaRepository 接口上的泛型类");
        }

        Class entityClass = ClassUtils.getClassByName(generics.get(0));
        Class idClass = ClassUtils.getClassByName(generics.get(1));

        dao.setClazz(clazz);
        dao.setEntityClass(entityClass);
        dao.setIdClass(idClass);
        return dao;
    }

}
