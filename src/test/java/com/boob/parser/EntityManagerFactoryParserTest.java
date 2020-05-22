package com.boob.parser;

import com.boob.configuration.Persistence;
import org.junit.Test;


public class EntityManagerFactoryParserTest {

    @Test
    public void parseTest() {
        Persistence persistence = new PersistenceDomParser().parse();
        System.out.println(persistence);
    }
}
