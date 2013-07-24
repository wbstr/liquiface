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
public class ForeignKeyConstraint extends Constraint {

    private List<Column> baseColumns;
    private List<Column> referencedColumns;
    private Table referencedTable;
    private Table baseTable;

    public ForeignKeyConstraint(String name) {
        super(name);
        baseColumns = new ArrayList<Column>();
        referencedColumns = new ArrayList<Column>();
    }

    public ForeignKeyConstraint() {
        this(null);
    }
    
    public List<Column> getBaseColumns() {
        return baseColumns;
    }

    public List<Column> getReferencedColumns() {
        return referencedColumns;
    }

    public Table getReferencedTable() {
        return referencedTable;
    }

    public void setReferencedTable(Table referencedTable) {
        this.referencedTable = referencedTable;
    }

    public boolean addBaseColumn(Column c) {
        return baseColumns.add(c);
    }

    public boolean addReferencedColumn(Column c) {
        return referencedColumns.add(c);
    }  

    public Table getBaseTable() {
        return baseTable;
    }

    public void setBaseTable(Table baseTable) {
        this.baseTable = baseTable;
    }
    
}
