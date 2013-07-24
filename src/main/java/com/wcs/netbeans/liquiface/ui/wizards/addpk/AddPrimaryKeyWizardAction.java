/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.ui.wizards.addpk;

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
import java.util.Arrays;
import java.util.List;
import liquibase.change.Change;
import liquibase.change.core.AddPrimaryKeyChange;
import org.openide.WizardDescriptor;
import org.openide.WizardDescriptor.Panel;

public final class AddPrimaryKeyWizardAction extends AbstractChangeWizardAction {

    private Table table;

    public AddPrimaryKeyWizardAction(Table table) {
        this.table = table;
    }

    @Override
    protected List<Panel<WizardDescriptor>> getWizardPanels() {
        List<Panel<WizardDescriptor>> panels = new ArrayList<Panel<WizardDescriptor>>();
        panels.add(new AddPrimaryKeyWizardPanel1(table.getName()));
        return panels;
    }

    @Override
    protected String getTitle() {
        return "Add primary key constraint";
    }

    @Override
    protected Change createChange(WizardDescriptor wiz) {
        String pkName = (String) wiz.getProperty(AddPrimaryKeyWizardConstants.PRIMARY_KEY_NAME);
        Object[] pkColumns = (Object[]) wiz.getProperty(AddPrimaryKeyWizardConstants.SELECTED_COLUMNS);
        String columnString = Arrays.toString(pkColumns).replace("[", "").replace("]", "");

        AddPrimaryKeyChange addPkChange = new AddPrimaryKeyChange();
        addPkChange.setTableName(table.getName());
        addPkChange.setConstraintName(pkName);
        addPkChange.setColumnNames(columnString);
        return addPkChange;
    }

    @Override
    protected RedrawSceneEvent getRedrawSceneEvent() {
        return new RedrawTableSceneEvent(table.getId());
    }
}
