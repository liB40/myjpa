### xml 配置

```xml
<persistence>
    <!--需要配置persistence-unit
        持久化单元:
            name:名称
            transaction-type:事务管理的方式 (暂不支持)
                JTA:分布式事务管理
                RESOURCE_LOCAL:本地事务管理
    -->
    <persistence-unit name="myJpa" transaction-type="RESOURCE_LOCAL">
        <!--数据库信息-->
        <datasource>
            <url value="url"></url>
            <driver value="driver"></driver>
            <username value="username"></username>
            <password value="password"></password>
        </datasource>

        <properties>
            <!--是否展示sql(暂不支持)-->
            <property name="show_sql" value="true"></property>
            <!--自动生成数据库表(暂不支持)
                create :  创建表(已有该表时先删除然后创建新表)
                update :  创建表(已有该表时不会创建表)
                none   :  不创建表
            -->
            <property name="auto_database" value="update"></property>
            <!--名称转换规则
                hump   :  转为驼峰命名
            -->
            <property name="name_rule" value="hump"></property>
        </properties>

        <!--默认从类路径下开始找-->
        <!--实体类包-->
        <entity-scan value="model.entity"></entity-scan>
        <!--dao层包(暂不支持)-->
        <dao-scan value="model.dao"></dao-scan>

    </persistence-unit>

</persistence>

```

### 使用

1. 原生的jpa 使用
```java
    @Test
    public void findTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJpa");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
    
        entityTransaction.begin();
        User user = entityManager.find(User.class, 1);
        System.out.println(user);
        entityTransaction.commit();
        entityManager.close();
    }
```

2. 使用Dao 操作
```java
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJpa");
    EntityManager em = emf.createEntityManager();
    UserDao userDao = new DaoProxy(em).getInstance(UserDao.class);
    User user = userDao.findById(1);
    System.out.println(user);
```