package com.boob.factory;

import com.boob.configuration.Persistence;
import org.junit.Test;

public class DefaultEntityManagerFactoryTest {

    @Test
    public void scanEntityTest() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJpa");
        System.out.println(emf.getEntityMap());
        System.out.println(emf.getDaoMap());
    }

}