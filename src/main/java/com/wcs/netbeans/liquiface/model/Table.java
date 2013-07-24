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
public class Table extends Named {
    
    private int id;
    private List<Column> columns;
    
    private PrimaryKeyConstraint primaryKeyConstraint;
    private List<ForeignKeyConstraint> foreignKeyConstraints;
    private List<UniqueConstraint> uniqueConstraints;
    
    public Table(int id){
        super();
        this.id = id;
        this.columns = new ArrayList<Column>();
        this.foreignKeyConstraints = new ArrayList<ForeignKeyConstraint>();
        this.uniqueConstraints = new ArrayList<UniqueConstraint>();
    }
    
    public Table(String name, int id){
        this(id);
        setName(name);
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void addColumn(Column column){
        columns.add(column);
    }
    
    public void removeColumn(Column column){
        columns.remove(column);
    }
    
    public Column getColumnByName(String name){
        for (Column column : columns){
           if (column.getName().equals(name)){
               return column;
           }
        }
        return null;
    }
    
    public Column getColumnById(Integer id){
        for (Column column : columns){
           if (id.intValue() == column.getId()){
               return column;
           }
        }
        return null;
    }
    
    public ForeignKeyConstraint getForeignConstraintByName(String name){
        for (ForeignKeyConstraint foreignKeyConstraint : foreignKeyConstraints){
            if (foreignKeyConstraint.getName().equals(name)){
                return foreignKeyConstraint;
            }
        }
        return null;
    }

    public List<ForeignKeyConstraint> getForeignKeyConstraints() {
        return foreignKeyConstraints;
    }

    public PrimaryKeyConstraint getPrimaryKeyConstraint() {
        return primaryKeyConstraint;
    }

    public void setPrimaryKeyConstraint(PrimaryKeyConstraint primaryKeyConstraint) {
        this.primaryKeyConstraint = primaryKeyConstraint;
    }  
    
    public void addForeignKeyConstraint(ForeignKeyConstraint constraint){
        foreignKeyConstraints.add(constraint);
    }

    public void removeForeignKeyConstraint(ForeignKeyConstraint constraint) {
        foreignKeyConstraints.remove(constraint);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<UniqueConstraint> getUniqueConstraints() {
        return uniqueConstraints;
    }
    
    public void addUniqueConstraint(UniqueConstraint constraint){
        uniqueConstraints.add(constraint);
    }

    public void removeUniqueConstraint(UniqueConstraint constraint) {
        uniqueConstraints.remove(constraint);
    }    
    
    public UniqueConstraint getUniqueConstraintByName(String name){
        for (UniqueConstraint uniqueConstraint : uniqueConstraints){
            if (uniqueConstraint.getName().equals(name)){
                return uniqueConstraint;
            }
        }
        return null;
    }  
    
    public List<Constraint> getUsingConstraintsForColumn(Column column) {
        List<Constraint> constraints = new ArrayList<Constraint>();
        if(getPrimaryKeyConstraint() != null
                && getPrimaryKeyConstraint().getColumns() != null
                && getPrimaryKeyConstraint().getColumns().contains(column)) {
            constraints.add(getPrimaryKeyConstraint());
        }
        for (UniqueConstraint uc : getUniqueConstraints()) {
            if (uc.getColumns().contains(column)) {
                constraints.add(uc);
            }
        }
        for (ForeignKeyConstraint fk : getForeignKeyConstraints()) {
            if (fk.getBaseColumns().contains(column)) {
                constraints.add(fk);
            }
        }
        return constraints;
    }
    
    public boolean isForeignKey(Column column){
        for (ForeignKeyConstraint foreignKeyConstraint : getForeignKeyConstraints()) {
            if(foreignKeyConstraint.getBaseColumns().contains(column)){
                return true;
            }
        }
        return false;
    }    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Table other = (Table) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        return hash;
    }
    
}
