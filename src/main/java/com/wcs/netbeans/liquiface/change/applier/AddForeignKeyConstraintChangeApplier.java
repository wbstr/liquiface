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
import com.wcs.netbeans.liquiface.model.ForeignKeyConstraint;
import com.wcs.netbeans.liquiface.model.Model;
import com.wcs.netbeans.liquiface.model.Table;
import java.util.List;
import liquibase.change.Change;
import liquibase.change.core.AddForeignKeyConstraintChange;
import liquibase.util.StringUtils;

/**
 *
 * @author tveki
 */
@ApplyChange(AddForeignKeyConstraintChange.class)
public class AddForeignKeyConstraintChangeApplier implements ChangeApplier {

    @Override
    public void apply(Model model, Change c) {
        AddForeignKeyConstraintChange change = (AddForeignKeyConstraintChange) c;
        
        Table table = model.getTableByName(change.getBaseTableName());        
        
        ForeignKeyConstraint constraint = new ForeignKeyConstraint(change.getConstraintName());
        
        constraint.setBaseTable(table);
        
        Table referencedTable = model.getTableByName(change.getReferencedTableName());
        
        constraint.setReferencedTable(referencedTable);
        
        List<String> baseColumnNames = StringUtils.splitAndTrim(change.getBaseColumnNames(), ",");
        for (String baseColumnName : baseColumnNames){
            Column baseColumn = table.getColumnByName(baseColumnName);
            constraint.addBaseColumn(baseColumn);
        }
        
        List<String> referencedColumnNames = StringUtils.splitAndTrim(change.getReferencedColumnNames(), ",");
        for (String referencedColumnName : referencedColumnNames){
            Column referencedColumn = referencedTable.getColumnByName(referencedColumnName);
            constraint.addReferencedColumn(referencedColumn);
        }
        
        table.addForeignKeyConstraint(constraint);
    } 
    
}
