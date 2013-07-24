/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.ui.wizards.addcolumns;

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
import com.wcs.netbeans.liquiface.model.Table;
import com.wcs.netbeans.liquiface.ui.wizards.AbstractChangeWizardAction;
import java.util.ArrayList;
import java.util.List;
import liquibase.change.Change;
import liquibase.change.ColumnConfig;
import liquibase.change.core.AddColumnChange;
import org.openide.WizardDescriptor;

// An example action demonstrating how the wizard could be called from within
// your code. You can move the code below wherever you need, or register an action:
// @ActionID(category="...", id="com.wcs.netbeans.liquiface.ui.wizards.addcolumns.AddColumnsWizardAction")
// @ActionRegistration(displayName="Open AddColumns Wizard")
// @ActionReference(path="Menu/Tools", position=...)
public final class AddColumnsWizardAction extends AbstractChangeWizardAction {

    private Table table;

    public AddColumnsWizardAction(Table table) {
        this.table = table;
    }

    @Override
    protected String getTitle() {
        return "Add columns";
    }

    @Override
    protected List<WizardDescriptor.Panel<WizardDescriptor>> getWizardPanels() {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<WizardDescriptor.Panel<WizardDescriptor>>();
        panels.add(new AddColumnsWizardPanel1());
        return panels;
    }

    @Override
    protected Change createChange(WizardDescriptor wiz) {
        List<ColumnConfig> columnConfigs = (List<ColumnConfig>) wiz.getProperty(AddColumnsWizardConstants.COLUMN_CONFIGS);
        AddColumnChange addColumnChange = new AddColumnChange();
        addColumnChange.setTableName(table.getName());
        for (ColumnConfig columnConfig : columnConfigs) {
            addColumnChange.addColumn(columnConfig);
        }
        return addColumnChange;
    }

    @Override
    protected RedrawSceneEvent getRedrawSceneEvent() {
        return new RedrawTableSceneEvent(table.getId());
    }
}
