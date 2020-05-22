package com.boob.scanner;

import com.boob.annotation.Column;
import com.boob.annotation.Id;
import com.boob.annotation.Table;
import com.boob.converter.NameConverter;
import com.boob.model.Entity;
import com.boob.utils.ClassUtils;
import lombok.Data;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * entity扫描器
 */
@Data
public class EntityScanner extends AbstractFileScanner<Entity> implements FileScanner<Entity> {


    public EntityScanner() {
    }

    public EntityScanner(NameConverter nameConverter, String packageName) {
        this.nameConverter = nameConverter;
        this.packageName = packageName;
    }

    @Override
    protected boolean check(Class clazz) {
        //上面有没有加Entity注解
        return clazz.isAnnotationPresent(com.boob.annotation.Entity.class);
    }

    @Override
    protected Entity scanModel(Class clazz) {
        Entity entity = new Entity();

        String tableName = scanTable(clazz);
        Field[] fields = clazz.getDeclaredFields();
        Map<String, String> columns = scanColumns(fields);
        String idName = scanId(fields);

        entity.setClazz(clazz);
        entity.setId(idName);
        entity.setColumns(columns);
        entity.setTableName(tableName);
        return entity;
    }

    /**
     * 扫描id
     *
     * @param fields
     * @return
     */
    private String scanId(Field[] fields) {
        boolean hasId = false;
        String idName = null;
        for (Field field : fields) {
            //判断id
            boolean isId = field.isAnnotationPresent(Id.class);
            if (isId) {
                //如果已经有id了
                if (hasId) {
                    throw new IllegalArgumentException(classCache + " 不能同时含有两个id字段");
                }
                idName = field.getName();
                hasId = true;
            }
        }
        return idName;
    }

    /**
     * 扫描所有column信息
     *
     * @param fields
     */
    private Map<String, String> scanColumns(Field[] fields) {
        HashMap<String, String> columnMap = new HashMap<>();

        for (Field field : fields) {
            String fieldName = field.getName();
            String columnName = scanColumn(field);
            //如果有重复字段名
            if (columnMap.containsValue(columnName)) {
                throw new IllegalArgumentException(classCache + "中的" + columnName + "字段出现重复");
            }
            columnMap.put(fieldName, columnName);
        }
        return columnMap;
    }

    /**
     * 扫描单个column信息
     *
     * @param field
     */
    private String scanColumn(Field field) {
        String columnName = field.getName();
        Column column = field.getAnnotation(Column.class);
        //获取name
        if (column != null && !column.name().equals("")) {
            columnName = column.name();
        }
        return nameConverter.convert(columnName);
    }

    /**
     * 扫描table信息
     */
    private String scanTable(Class clazz) {

        String tableName = clazz.getSimpleName();
        Table table = (Table) clazz.getAnnotation(Table.class);
        //获取name
        if (table != null && !table.name().equals("")) {
            tableName = table.name();
        }
        return nameConverter.convert(tableName);
    }

}
