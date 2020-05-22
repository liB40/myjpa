package com.boob.factory;

import com.boob.configuration.ConnectionPool;
import com.boob.configuration.PersistenceUnit;
import com.boob.converter.ConverterFactory;
import com.boob.converter.NameConverter;
import com.boob.enums.XmlPropertiesNameEnum;
import com.boob.manager.DefaultEntityManager;
import com.boob.manager.EntityManager;
import com.boob.model.Dao;
import com.boob.model.Entity;
import com.boob.scanner.DaoScanner;
import com.boob.scanner.EntityScanner;
import com.boob.scanner.FileScanner;
import com.sun.xml.internal.bind.v2.TODO;
import lombok.Data;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * EntityManagerFactory默认实现类
 */
@Data
public class DefaultEntityManagerFactory implements EntityManagerFactory {

    /**
     * 单个持久层单元
     */
    private PersistenceUnit persistenceUnit;

    /**
     * Class为实体类字节码
     * 保存所有entity的集合
     */
    private Map<Class, Entity> entityMap = new HashMap<>();

    /**
     * entity扫描器
     */
    private EntityScanner entityScanner = new EntityScanner();

    /**
     * Class为dao字节码
     * 保存所有dao的集合
     */
    private Map<Class, Dao> daoMap = new HashMap<>();

    /**
     * dao扫描器
     */
    private DaoScanner daoScanner = new DaoScanner();

    /**
     * 连接池
     */
    private ConnectionPool connectionPool;

    public DefaultEntityManagerFactory() {
    }

    public DefaultEntityManagerFactory(PersistenceUnit persistenceUnit) {
        this.persistenceUnit = persistenceUnit;
        init();
    }


    @Override
    public EntityManager createEntityManager() {
        return new DefaultEntityManager(this);
    }

    /**
     * 初始化
     */
    private void init() {

        setConnectionPool(ConnectionPool.getInstance(persistenceUnit.getDataSource()));

        String entityScan = persistenceUnit.getEntityScan();
        String daoScan = persistenceUnit.getDaoScan();

        //获取命名方式
        String nameRule = persistenceUnit.getPropertyMap().get(XmlPropertiesNameEnum.NAME_RULE).getValue();
        NameConverter nameConverter = ConverterFactory.getNameConverterByName(nameRule);
        //设置名称转换器
        entityScanner.setNameConverter(nameConverter);
        daoScanner.setNameConverter(nameConverter);

        //设置包名
        entityScanner.setPackageName(entityScan);
        daoScanner.setPackageName(daoScan);

        scanFile(entityScan, entityScanner, entityMap);
        scanFile(daoScan, daoScanner, daoMap);


        // TODO 检查Dao中的Id 属性和Entity中的是不是对的上

//        checkId();
    }


    /**
     * 扫描文件
     *
     * @param modelScan   文件路径
     * @param fileScanner 扫描器
     * @param map         容器
     */
    private <V> void scanFile(String modelScan, FileScanner<V> fileScanner, Map<Class, V> map) {

        fileScanner.scan(modelScan, map);
    }

    /**
     * 获取连接
     *
     * @return
     */
    @Override
    public Connection getConnection() {
        return this.connectionPool.getConnection();
    }

    /**
     * 归还连接
     *
     * @return
     */
    @Override
    public void putConnection(Connection connection) {
        this.connectionPool.putConnection(connection);
    }
}
