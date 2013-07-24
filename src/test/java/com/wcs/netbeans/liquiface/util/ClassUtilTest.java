/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.util;

/*
 * #%L
 * Liquiface - GUI for Liquibase
 * %%
 * Copyright (C) 2013 Webstar Csoport Kft.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.wcs.netbeans.liquiface.change.applier.AddColumnChangeApplier;
import com.wcs.netbeans.liquiface.change.applier.CreateTableChangeApplier;
import com.wcs.netbeans.liquiface.change.applier.DropColumnChangeApplier;
import com.wcs.netbeans.liquiface.change.applier.DropTableChangeApplier;
import liquibase.change.core.AddColumnChange;
import liquibase.change.core.CreateTableChange;
import liquibase.change.core.DropColumnChange;
import liquibase.change.core.DropTableChange;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tveki
 */
public class ClassUtilTest {
    
    public ClassUtilTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    @Test
    public void testIfClassesAreInDir() throws Exception {        
        Iterable<Class> result = ClassUtil.getAllClassesRecursivelyFromPackageOf(CreateTableChangeApplier.class);
        assertContainsClass(result, AddColumnChangeApplier.class);
        assertContainsClass(result, DropColumnChangeApplier.class);
        assertContainsClass(result, DropTableChangeApplier.class);
    }
    
    @Test
    public void testIfClassesAreInJar() throws Exception {        
        Iterable<Class> result = ClassUtil.getAllClassesRecursivelyFromPackageOf(CreateTableChange.class);
        assertContainsClass(result, AddColumnChange.class);
        assertContainsClass(result, DropColumnChange.class);
        assertContainsClass(result, DropTableChange.class);
    }    
    
    private void assertContainsClass(Iterable<Class> result, Class clazz){
        boolean contains = false;
        for (Class c : result){
            if (clazz.equals(c)){
                contains = true;
            }
        }
        assertTrue(contains);
    }
}