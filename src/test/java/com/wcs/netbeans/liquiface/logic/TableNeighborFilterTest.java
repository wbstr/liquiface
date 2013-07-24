/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.logic;

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

import com.wcs.netbeans.liquiface.model.ForeignKeyConstraint;
import com.wcs.netbeans.liquiface.model.Table;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;

import com.wcs.netbeans.liquiface.logic.TableNeighborFilter.TableNeighborFilterType;
import org.junit.BeforeClass;

/**
 *
 * @author athalay
 */
public class TableNeighborFilterTest {
    
    Table table1 = new Table(1);
    
    public TableNeighborFilterTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testSomeMethod() {
        List<Table> allTables = createTestTables();
        Table centerTable = table1;
        int distance = 3;
        TableNeighborFilter logika = new TableNeighborFilter(allTables, centerTable, distance, TableNeighborFilterType.BOTH);
        
        List<Table> filteredTables = logika.getFilteredTables();
        assertEquals(6, filteredTables.size());
    }
    
    @Test
    public void testSomeMethod2() {
        List<Table> allTables = createTestTables();
        Table centerTable = table1;
        int distance = 2;
        TableNeighborFilter logika = new TableNeighborFilter(allTables, centerTable, distance, TableNeighborFilterType.BOTH);
        
        List<Table> filteredTables = logika.getFilteredTables();
        assertEquals(5, filteredTables.size());
    }
    
    @Test
    public void testSomeMethod3() {
        List<Table> allTables = createTestTables();
        Table centerTable = table1;
        int distance = 1;
        TableNeighborFilter logika = new TableNeighborFilter(allTables, centerTable, distance, TableNeighborFilterType.BOTH);
        
        List<Table> filteredTables = logika.getFilteredTables();
        assertEquals(3, filteredTables.size());
    }
    
    @Test
    public void testSomeMethod4() {
        List<Table> allTables = createTestTables();
        Table centerTable = table1;
        int distance = 0;
        TableNeighborFilter logika = new TableNeighborFilter(allTables, centerTable, distance, TableNeighborFilterType.BOTH);
        
        List<Table> filteredTables = logika.getFilteredTables();
        assertEquals(1, filteredTables.size());
    }
    
    private List<Table> createTestTables(){        
        
        Table table2 = new Table(2);
        Table table3 = new Table(3);
        Table table4 = new Table(4);
        Table table5 = new Table(5);
        Table table6 = new Table(6);
        
        ForeignKeyConstraint fk1 = createForeignKey(table1, table3);
        ForeignKeyConstraint fk2 = createForeignKey(table1, table2);
        ForeignKeyConstraint fk3 = createForeignKey(table3, table4);
        ForeignKeyConstraint fk4 = createForeignKey(table2, table5);
        ForeignKeyConstraint fk5 = createForeignKey(table5, table6);
        
        table1.addForeignKeyConstraint(fk1);
        table1.addForeignKeyConstraint(fk2);
        table3.addForeignKeyConstraint(fk3);
        table2.addForeignKeyConstraint(fk4);
        table5.addForeignKeyConstraint(fk5);
        
        List<Table> tables = new ArrayList<Table>();
        tables.add(table1);
        tables.add(table2);
        tables.add(table3);
        tables.add(table4);
        tables.add(table5);
        tables.add(table6);
        return tables;
    }
    
    private ForeignKeyConstraint createForeignKey(Table from, Table to){
        ForeignKeyConstraint fk = new ForeignKeyConstraint();
        fk.setBaseTable(from);
        fk.setReferencedTable(to);
        return fk;
    } 
}
