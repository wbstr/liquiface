/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.change.applier.provider;

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
import com.wcs.netbeans.liquiface.change.applier.ChangeApplier;
import com.wcs.netbeans.liquiface.change.applier.CreateTableChangeApplier;
import com.wcs.netbeans.liquiface.change.applier.DropColumnChangeApplier;
import com.wcs.netbeans.liquiface.change.applier.DropTableChangeApplier;
import liquibase.change.Change;
import liquibase.change.core.AddColumnChange;
import liquibase.change.core.CreateTableChange;
import liquibase.change.core.DropColumnChange;
import liquibase.change.core.DropTableChange;
import liquibase.change.core.MergeColumnChange;
import liquibase.change.core.StopChange;
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
public class LiquibaseChangeApplierProviderTest {
    
    LiquibaseChangeApplierProvider provider = new LiquibaseChangeApplierProvider();
    
    public LiquibaseChangeApplierProviderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {        
    }
    
    @AfterClass
    public static void tearDownClass() {        
    }
    
    @Before
    public void setUp() {
        provider = new LiquibaseChangeApplierProvider();
    }
    
    @After
    public void tearDown() {
        provider = null;
    }
    
    @Test
    public void testExistingAppliers() throws Exception {       
        assertApplierForChange(AddColumnChangeApplier.class, AddColumnChange.class);
        assertApplierForChange(DropColumnChangeApplier.class, DropColumnChange.class);
        assertApplierForChange(CreateTableChangeApplier.class, CreateTableChange.class);
        assertApplierForChange(DropTableChangeApplier.class, DropTableChange.class);
    }
    
    @Test
    public void testNonExistingAppliers() throws Exception {       
        //assume we don't implement appliers for this changes...
        assertNull(provider.getApplier(MergeColumnChange.class));
        assertNull(provider.getApplier(StopChange.class));
    }    
    
    private void assertApplierForChange(Class<? extends ChangeApplier> expectedApplierClass, Class<? extends Change> changeClass) throws Exception {        
        ChangeApplier applier = provider.getApplier(changeClass);        
        assertNotNull(applier);
        assertEquals(expectedApplierClass, applier.getClass());        
    }    
}