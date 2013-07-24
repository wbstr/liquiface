/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.change.applier;

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

import com.wcs.netbeans.liquiface.model.Column;
import com.wcs.netbeans.liquiface.model.Model;
import com.wcs.netbeans.liquiface.model.PrimaryKeyConstraint;
import com.wcs.netbeans.liquiface.model.Table;
import com.wcs.netbeans.liquiface.model.factory.ColumnFactory;
import com.wcs.netbeans.liquiface.model.factory.PrimaryKeyConstraintFactory;
import com.wcs.netbeans.liquiface.model.factory.UniqueConstraintFactory;
import com.wcs.netbeans.liquiface.model.util.ColumnConfigUtil;
import java.util.ArrayList;
import java.util.List;
import liquibase.change.Change;
import liquibase.change.ColumnConfig;
import liquibase.change.core.CreateTableChange;

/**
 *
 * @author tveki
 */
@ApplyChange(CreateTableChange.class)
public class CreateTableChangeApplier implements ChangeApplier {

    @Override
    public void apply(Model model, Change c) {
        CreateTableChange change = (CreateTableChange) c;
        
        Table table = new Table(change.getTableName(), model.nextId());
        
        List<Column> primaryKeyColumns = new ArrayList<Column>();
        
        for (ColumnConfig config : change.getColumns()){
            Column column = ColumnFactory.createColumn(config, model.nextId());
            table.addColumn(column);
            if (ColumnConfigUtil.isUnique(config)){
                table.addUniqueConstraint(UniqueConstraintFactory.createUniqueConstraint(table, column));
            }
            if (config.isPrimaryKey()){
                primaryKeyColumns.add(column);
            }
        }
        
        if (!primaryKeyColumns.isEmpty()){            
            PrimaryKeyConstraint constraint = PrimaryKeyConstraintFactory
                        .createPrimaryKeyConstraint("PK_" + table.getName().toUpperCase(),
                                                    primaryKeyColumns);            
            table.setPrimaryKeyConstraint(constraint);
        }
        
        model.addTable(table);
    }
    
}
