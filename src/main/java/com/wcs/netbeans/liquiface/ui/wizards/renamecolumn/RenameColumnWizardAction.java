/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.ui.wizards.renamecolumn;

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
import com.wcs.netbeans.liquiface.model.Column;
import com.wcs.netbeans.liquiface.model.Table;
import com.wcs.netbeans.liquiface.ui.wizards.AbstractChangeWizardAction;
import java.util.ArrayList;
import java.util.List;
import liquibase.change.Change;
import liquibase.change.core.RenameColumnChange;
import org.openide.WizardDescriptor;

public final class RenameColumnWizardAction extends AbstractChangeWizardAction {

    private Table table;
    private Column column;

    public RenameColumnWizardAction(Table table, Column column) {
        this.table = table;
        this.column = column;
    }

    @Override
    protected List<WizardDescriptor.Panel<WizardDescriptor>> getWizardPanels() {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<WizardDescriptor.Panel<WizardDescriptor>>();
        panels.add(new RenameColumnWizardPanel1(column.getName()));
        return panels;
    }

    @Override
    protected String getTitle() {
        return "Rename Column";
    }

    @Override
    protected Change createChange(WizardDescriptor wiz) {
        String newColumnName = (String) wiz.getProperty(RenameColumnWizardConstants.NEW_COLUMN_NAME);
        RenameColumnChange renameColumnChange = new RenameColumnChange();
        renameColumnChange.setTableName(table.getName());
        renameColumnChange.setOldColumnName(column.getName());
        renameColumnChange.setNewColumnName(newColumnName);
        renameColumnChange.setColumnDataType(column.getType());

        return renameColumnChange;
    }

    @Override
    protected RedrawSceneEvent getRedrawSceneEvent() {
        return new RedrawColumnSceneEvent(table.getId(), column.getId());
    }
}
