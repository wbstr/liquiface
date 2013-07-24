/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.model;

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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tveki
 */
public class Model {
    
    private List<Table> tables;
    
    private int idSeq;
    
    public Model(){
        this.tables = new ArrayList<Table>();
    }

    public List<Table> getTables() {
        return tables;
    }
    
    public void addTable(Table table){
        tables.add(table);
    }
    
    public Table getTableByName(String name){
        for (Table t : tables){
            if (t.getName().equals(name)){
                return t;
            }
        }
        return null;
    }
    
    public void removeTable(Table table){
        tables.remove(table);
    }
    
    public Table getTableById(int id){
        for (Table t : tables){
            if (t.getId() == id){
                return t;
            }
        }
        return null;
    }    
    
    public int nextId(){
        return ++idSeq;
    }
    
    public int getNumberOfTables(){
        return tables.size();
    }
    
    public List<ForeignKeyConstraint> getReferencingForeignKeys(String tableName, List<Column> columns) {
        List<ForeignKeyConstraint> referencingList = new ArrayList<ForeignKeyConstraint>();
        for (ForeignKeyConstraint fk : getAllForeignKeys()) {
            if (fk.getReferencedTable().getName().equals(tableName)
                    && fk.getReferencedColumns().equals(columns)) {
                referencingList.add(fk);
            }
        }
        return referencingList;
    }
    
    public List<ForeignKeyConstraint> getAllForeignKeys() {
        List<ForeignKeyConstraint> allConstraints = new ArrayList<ForeignKeyConstraint>();
        for (Table table : getTables()) {
            allConstraints.addAll(table.getForeignKeyConstraints());
        }        
        return allConstraints;
    }
    
}
