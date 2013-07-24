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

import com.wcs.netbeans.liquiface.database.DatabaseUpdater;
import com.wcs.netbeans.liquiface.ui.wizards.AbstractWizardAction;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.db.explorer.DatabaseConnection;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.WizardDescriptor;
import org.openide.WizardDescriptor.Panel;

public final class UpdateDatabaseWizardAction extends AbstractWizardAction {

    @Override
    protected List<Panel<WizardDescriptor>> getWizardPanels() {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<WizardDescriptor.Panel<WizardDescriptor>>();
        panels.add(new UpdateDatabaseWizardPanel1());
        return panels;
    }

    @Override
    protected String getTitle() {
        return "Update database";
    }

    @Override
    protected void onFinishWizard(final WizardDescriptor wiz) {
        DatabaseConnection databaseConnection = (DatabaseConnection) wiz.getProperty(UpdateDatabaseWizardConstants.SELECTED_DATABASE);        
        DatabaseUpdater updater = new DatabaseUpdater();
        String message = null;
        try {
            updater.updateDatabase(databaseConnection);
            message = "Database " + databaseConnection.getName() + " updated!";
        } catch (Exception ex) {
            Logger.getLogger(UpdateDatabaseWizardAction.class.getName()).log(Level.INFO, null, ex);
            message = ex.getMessage();
        }
        NotifyDescriptor nd = new NotifyDescriptor.Message(message, NotifyDescriptor.INFORMATION_MESSAGE);
        DialogDisplayer.getDefault().notify(nd);
    }

    
}
