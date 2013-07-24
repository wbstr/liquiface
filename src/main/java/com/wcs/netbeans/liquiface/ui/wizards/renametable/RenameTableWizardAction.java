/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.ui.wizards.renametable;

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
import liquibase.change.core.RenameTableChange;
import org.openide.WizardDescriptor;

public final class RenameTableWizardAction extends AbstractChangeWizardAction {

    private Table table;

    public RenameTableWizardAction(Table table) {
        this.table = table;
    }

    @Override
    protected List<WizardDescriptor.Panel<WizardDescriptor>> getWizardPanels() {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<WizardDescriptor.Panel<WizardDescriptor>>();
        panels.add(new RenameTableWizardPanel1());
        return panels;
    }

    @Override
    protected String getTitle() {
        return "Rename Table";
    }

    @Override
    protected Change createChange(WizardDescriptor wiz) {
        String newTableName = (String) wiz.getProperty(RenameTableWizardConstants.NEW_TABLE_NAME);
        RenameTableChange renameTableChange = new RenameTableChange();
        renameTableChange.setOldTableName(table.getName());
        renameTableChange.setNewTableName(newTableName);

        return renameTableChange;
    }

    @Override
    protected RedrawSceneEvent getRedrawSceneEvent() {
        return new RedrawTableSceneEvent(table.getId());
    }
}
