/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.ui.wizards.modifydatatype;

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
import liquibase.change.core.ModifyDataTypeChange;
import org.openide.WizardDescriptor;

public final class ModifyDataTypeWizardAction extends AbstractChangeWizardAction {

    private Table table;
    private Column column;

    public ModifyDataTypeWizardAction(Table table, Column column) {
        this.table = table;
        this.column = column;
    }

    @Override
    protected List<WizardDescriptor.Panel<WizardDescriptor>> getWizardPanels() {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<WizardDescriptor.Panel<WizardDescriptor>>();
        panels.add(new ModifyDataTypeWizardPanel1(column.getName(), column.getType()));
        return panels;
    }

    @Override
    protected String getTitle() {
        return "Modify column data type";
    }

    @Override
    protected Change createChange(WizardDescriptor wiz) {
        String newDataType = (String) wiz.getProperty(ModifyDataTypeWizardConstants.NEW_DATA_TYPE);        

        ModifyDataTypeChange change = new ModifyDataTypeChange();
        change.setTableName(table.getName());
        change.setColumnName(column.getName());
        change.setNewDataType(newDataType);
        return change;
    }

    @Override
    protected RedrawSceneEvent getRedrawSceneEvent() {
        return new RedrawColumnSceneEvent(table.getId(), column.getId());
    }
}
