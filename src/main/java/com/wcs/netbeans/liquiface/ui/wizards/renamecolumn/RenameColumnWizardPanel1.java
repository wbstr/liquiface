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

import com.wcs.netbeans.liquiface.ui.wizards.AbstractWizardPanel;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;

public class RenameColumnWizardPanel1 extends AbstractWizardPanel{

    /**
     * The visual component that displays this panel. If you need to access the component from this class, just use
     * getComponent().
     */
    private RenameColumnVisualPanel1 component;
    private String columnName;

    public RenameColumnWizardPanel1(String columnName) {
        this.columnName = columnName;
    }

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public RenameColumnVisualPanel1 getComponent() {
        if (component == null) {
            component = new RenameColumnVisualPanel1(columnName);
        }
        return component;
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty(RenameColumnWizardConstants.NEW_COLUMN_NAME, getComponent().getNewColumnNameField().getText());
    }

    @Override
    public void validate() throws WizardValidationException {
        String columnName = component.getNewColumnNameField().getText();
        if (columnName.equals("")){
            throw new WizardValidationException(null, "New name is required!", null);
        }
    }
}
