/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.ui.wizards.droppk;

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
import com.wcs.netbeans.liquiface.model.ForeignKeyConstraint;
import com.wcs.netbeans.liquiface.model.PrimaryKeyConstraint;
import com.wcs.netbeans.liquiface.model.Table;
import com.wcs.netbeans.liquiface.ui.wizards.AbstractValidatingConfirmWizardAction;
import java.util.List;
import liquibase.change.Change;
import liquibase.change.core.DropPrimaryKeyChange;

/**
 *
 * @author botond
 */
public class DropPrimaryKeyWizardAction extends AbstractValidatingConfirmWizardAction {

    private Table table;
    private PrimaryKeyConstraint pk;
    private String errorMessage = "";

    public DropPrimaryKeyWizardAction(Table table) {
        this.table = table;
        this.pk = table.getPrimaryKeyConstraint();
    }

    @Override
    protected String getTitle() {
        return "Drop Primary Key";
    }

    @Override
    protected String getConfirmMessage() {
        return "Do you really want to drop the primary key?";
    }

    @Override
    protected Change createChange() {
        DropPrimaryKeyChange dropPrimaryKey = new DropPrimaryKeyChange();
        dropPrimaryKey.setTableName(table.getName());
        dropPrimaryKey.setConstraintName(pk.getName());
        return dropPrimaryKey;
    }

    @Override
    protected boolean isValid() {
        List<ForeignKeyConstraint> fkList = ModelFacade.getInstance().getReferencingForeignKeys(table.getName(), pk.getColumns());
        if(!fkList.isEmpty()) {
            errorMessage = "The primary key can't be dropped, because the following foreign key constraints reference it: \n";
            for (ForeignKeyConstraint fk : fkList) {
                errorMessage += "- " + fk.getName() + " (by " + fk.getBaseTable().getName() + " table)\n";
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
