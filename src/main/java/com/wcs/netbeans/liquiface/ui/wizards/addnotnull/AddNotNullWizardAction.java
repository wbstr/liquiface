/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.ui.wizards.addnotnull;

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


import com.wcs.netbeans.liquiface.eventbus.event.RedrawColumnSceneEvent;
import com.wcs.netbeans.liquiface.eventbus.event.RedrawSceneEvent;
import com.wcs.netbeans.liquiface.eventbus.event.RedrawTableSceneEvent;
import com.wcs.netbeans.liquiface.model.Column;
import com.wcs.netbeans.liquiface.model.Table;
import com.wcs.netbeans.liquiface.ui.wizards.AbstractConfirmChangeWizardAction;
import liquibase.change.Change;
import liquibase.change.core.AddNotNullConstraintChange;

/**
 *
 * @author botond
 */
public class AddNotNullWizardAction extends AbstractConfirmChangeWizardAction {

    private Table table;
    private Column column;

    public AddNotNullWizardAction(Table table, Column column) {
        this.table = table;
        this.column = column;
    }

    @Override
    protected String getTitle() {
        return "Add Not Null Constraint";
    }

    @Override
    protected String getConfirmMessage() {
        return "Do you really want to add a not null constraint to " + column.getName() + "?";
    }

    @Override
    protected Change createChange() {
        AddNotNullConstraintChange notNullConstraintChange = new AddNotNullConstraintChange();
        notNullConstraintChange.setTableName(table.getName());
        notNullConstraintChange.setColumnName(column.getName());
        return notNullConstraintChange;
    }

    @Override
    protected RedrawSceneEvent getRedrawSceneEvent() {
        return new RedrawColumnSceneEvent(table.getId(), column.getId());
    }

}
