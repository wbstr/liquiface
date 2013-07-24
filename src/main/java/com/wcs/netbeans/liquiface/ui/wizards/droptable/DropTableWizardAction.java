/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.ui.wizards.droptable;

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


import com.wcs.netbeans.liquiface.eventbus.LiquifaceEventBus;
import com.wcs.netbeans.liquiface.eventbus.event.RedrawGlobalSceneEvent;
import com.wcs.netbeans.liquiface.eventbus.event.RedrawSceneEvent;
import com.wcs.netbeans.liquiface.eventbus.event.SynchronizeFiltersEvent;
import com.wcs.netbeans.liquiface.facade.ModelFacade;
import com.wcs.netbeans.liquiface.model.ForeignKeyConstraint;
import com.wcs.netbeans.liquiface.model.Table;
import com.wcs.netbeans.liquiface.model.UniqueConstraint;
import com.wcs.netbeans.liquiface.ui.wizards.AbstractValidatingConfirmWizardAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import liquibase.change.Change;
import liquibase.change.core.DropTableChange;

/**
 *
 * @author botond
 */
public class DropTableWizardAction extends AbstractValidatingConfirmWizardAction {

    private Table table;
    private String errorMessage = "";

    public DropTableWizardAction(Table table) {
        this.table = table;
    }

    @Override
    protected String getTitle() {
        return "Drop table";
    }

    @Override
    protected String getConfirmMessage() {
        return "Do you really want to drop " + table.getName() + "?";
    }

    @Override
    protected Change createChange() {
        DropTableChange dropTable = new DropTableChange();
        dropTable.setTableName(table.getName());
        return dropTable;
    }

    @Override
    protected boolean isValid() {
        //getting foreign key references
        List<ForeignKeyConstraint> pkReferences;
        if (table.getPrimaryKeyConstraint() != null) {
            pkReferences = ModelFacade.getInstance().getReferencingForeignKeys(
                table.getName(),
                table.getPrimaryKeyConstraint().getColumns());
        } else {
            pkReferences = new ArrayList<ForeignKeyConstraint>();
        }

        //getting unique constraint references
        Map<String, List<ForeignKeyConstraint>> ucReferenceMap = new HashMap<String, List<ForeignKeyConstraint>>();
        for (UniqueConstraint uc : table.getUniqueConstraints()) {
            List<ForeignKeyConstraint> ucReferences = ModelFacade.getInstance().getReferencingForeignKeys(
                    table.getName(),
                    uc.getColumns());
            if (!ucReferences.isEmpty()) {
                ucReferenceMap.put(uc.getName(), ucReferences);
            }
        }

        //generating errormessage
        if (!pkReferences.isEmpty() || !ucReferenceMap.isEmpty()) {
            errorMessage = table.getName() + " can't be dropped because\n";
            if (!pkReferences.isEmpty()) {
                errorMessage += "- the primary key constraint is referenced by the following foreign key constraints:\n";
                for (ForeignKeyConstraint fk : pkReferences) {
                    errorMessage += "  * " + fk.getName() + " (by " + fk.getBaseTable().getName() + " table)\n";
                }
            }
            if (!ucReferenceMap.isEmpty()) {
                for (String ucName : ucReferenceMap.keySet()) {
                    errorMessage += "- the " + ucName + " unique key constraint is referenced by the following foreign key constraints:\n";
                    for (ForeignKeyConstraint fk : ucReferenceMap.get(ucName)) {
                        errorMessage += "  * " + fk.getName() + " (by " + fk.getBaseTable().getName() + " table)\n";
                    }
                }
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
    protected void onConfirmation() {
        super.onConfirmation();
        LiquifaceEventBus.getInstance().post(new SynchronizeFiltersEvent());
    }

    @Override
    protected RedrawSceneEvent getRedrawSceneEvent() {
        return new RedrawGlobalSceneEvent();
    }
}
