/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.facade;

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


/**
 *
 * @author botond
 */
public class ColumnTypeFacade {

    //<editor-fold defaultstate="collapsed" desc="singleton stuff">
    private static ColumnTypeFacade instance;

    public static ColumnTypeFacade getInstance(){
        if(instance == null){
            instance = new ColumnTypeFacade();
        }
        return instance;
    }

    private ColumnTypeFacade(){
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="enum">
    private enum DataTypes {
        NONE(""),
        BIT("BIT"),
        BLOB("BLOB"),
        CHAR("CHAR"),
        CHAR_1("CHAR(1)"),
        CLOB("CLOB"),
        DATE("DATE"),
        DECIMAL("DECIMAL(,)"),
        DOUBLE("DOUBLE"),
        FLOAT("FLOAT"),
        INTEGER("INTEGER"),
        NCHAR("NCHAR"),
        NUMERIC("NUMERIC(,)"),
        REAL("REAL"),
        SMALLINT("SMALLINT"),
        TIME("TIME"),
        TIMESTAMP("TIMESTAMP"),
        VARCHAR("VARCHAR"),
        VARCHAR_255("VARCHAR(255)");

        private String name;

        private DataTypes(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
    //</editor-fold>

    public String[] getColumnTypes() {
        String[] types = new String[DataTypes.values().length];
        int i = 0;
        for (DataTypes dataType : DataTypes.values()) {
            types[i] = dataType.getName();
            i++;
        }
        return types;
    }

}
