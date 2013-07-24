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

import java.util.Map;

/**
 *
 * @author tveki
 */
public class Column extends Named{
    
    private int id;
    private ColumnType columnType;
    private boolean autoIncrement = false;
    private boolean primaryKey = false;
    private boolean nullable = true;
    
    public Column(String name, SQLType sqlType, int id){
        super(name);
        this.id = id;
        this.columnType = new ColumnType(sqlType);
    }
    
    public Column(String name, int id){
        this(name, null, id);
    }    
    
    public Column(int id){
        this(null, id);
    }

    public ColumnType getColumnType() {
        return columnType;
    }

    public void setColumnType(ColumnType columnType) {
        this.columnType = columnType;
    }

    public SQLType getSqlType() {
        return columnType.getSqlType();
    }

    public String getProperty(String key) {
        return columnType.getProperty(key);
    }

    public Map<String, String> getProperties() {
        return columnType.getProperties();
    }

    public void addProperty(String key, String value) {
        columnType.addProperty(key, value);
    }

    public String getType() {
        return columnType.getType();
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }
    
    public void setColumnType(SQLType sqlType){
        setColumnType(new ColumnType(sqlType));        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Column other = (Column) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + this.id;
        return hash;
    }
}
