/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.ui.wizards.dropunique;

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
import liquibase.change.core.DropUniqueConstraintChange;
import org.openide.WizardDescriptor;
import org.openide.WizardDescriptor.Panel;

// An example action demonstrating how the wizard could be called from within
// your code. You can move the code below wherever you need, or register an action:
// @ActionID(category="...", id="com.wcs.netbeans.liquiface.ui.wizards.dropunique.DropUniqueConstraintWizardAction")
// @ActionRegistration(displayName="Open DropUniqueConstraint Wizard")
// @ActionReference(path="Menu/Tools", position=...)
public final class DropUniqueConstraintWizardAction extends AbstractChangeWizardAction{

    private Table table;

    public DropUniqueConstraintWizardAction(Table table) {
        this.table = table;
    }

    @Override
    protected List<Panel<WizardDescriptor>> getWizardPanels() {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<WizardDescriptor.Panel<WizardDescriptor>>();
        panels.add(new DropUniqueConstraintWizardPanel1(table.getName()));
        return panels;
    }

    @Override
    protected String getTitle() {
        return "Drop unique constraint";
    }

    @Override
    protected Change createChange(WizardDescriptor wiz) {
        String ucName = (String) wiz.getProperty(DropUniqueConstraintWizardConstants.CONSTRAINT_NAME);
        DropUniqueConstraintChange dropUnique = new DropUniqueConstraintChange();
        dropUnique.setTableName(table.getName());
        dropUnique.setConstraintName(ucName);
        return dropUnique;
    }

    @Override
    protected RedrawSceneEvent getRedrawSceneEvent() {
        return new RedrawTableSceneEvent(table.getId());
    }
}
