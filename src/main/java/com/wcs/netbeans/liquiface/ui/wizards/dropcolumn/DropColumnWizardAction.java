/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.ui.wizards.dropcolumn;

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


import com.wcs.netbeans.liquiface.eventbus.event.RedrawSceneEvent;
import com.wcs.netbeans.liquiface.eventbus.event.RedrawTableSceneEvent;
import com.wcs.netbeans.liquiface.facade.ModelFacade;
import com.wcs.netbeans.liquiface.model.Column;
import com.wcs.netbeans.liquiface.model.Constraint;
import com.wcs.netbeans.liquiface.model.Table;
import com.wcs.netbeans.liquiface.ui.wizards.AbstractValidatingConfirmWizardAction;
import java.util.List;
import liquibase.change.Change;
import liquibase.change.core.DropColumnChange;

/**
 *
 * @author botond
 */
public class DropColumnWizardAction extends AbstractValidatingConfirmWizardAction {

    private Table table;
    private Column column;
    private String errorMessage = "";

    public DropColumnWizardAction(Table table, Column column) {
        this.table = table;
        this.column = column;
    }

    @Override
    protected String getTitle() {
        return "Drop table";
    }

    @Override
    protected String getConfirmMessage() {
        return "Do you really want to drop " + column.getName() + "?";
    }

    @Override
    protected Change createChange() {
         DropColumnChange dropColumnChange = new DropColumnChange();
         dropColumnChange.setTableName(table.getName());
         dropColumnChange.setColumnName(column.getName());
         return dropColumnChange;
    }

    @Override
    protected boolean isValid() {
        List<Constraint> constraintList = table.getUsingConstraintsForColumn(column);
        if (!constraintList.isEmpty()) {
            errorMessage = "Column can't be dropped, because it is used by the following constraints:\n";
            for (Constraint c : constraintList) {
                errorMessage += "- " + c.getName() + "\n";
            }
            return false;
        }
        return true;
    }

    @Override
    protected String getErrorMessage() {
        return errorMessage;
    }

    @Override
    protected RedrawSceneEvent getRedrawSceneEvent() {
        return new RedrawTableSceneEvent(table.getId());
    }

}
