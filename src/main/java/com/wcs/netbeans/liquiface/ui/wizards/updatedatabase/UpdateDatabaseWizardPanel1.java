/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.ui.wizards.updatedatabase;

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
import com.wcs.netbeans.liquiface.ui.wizards.panel.DatabaseChooserPanel;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;

public class UpdateDatabaseWizardPanel1 extends AbstractWizardPanel {

   
    private DatabaseChooserPanel component;

    @Override
    public DatabaseChooserPanel getComponent() {
        if (component == null) {
            component = new DatabaseChooserPanel();
        }
        return component;
    }

    @Override
    public void validate() throws WizardValidationException {
        if (component.getSelectedDatabase() == null){
            throw new WizardValidationException(null, "Select a database!", null);
        }
    }

    @Override
    public void storeSettings(WizardDescriptor data) {
        data.putProperty(UpdateDatabaseWizardConstants.SELECTED_DATABASE, component.getSelectedDatabase());        
    }


}
