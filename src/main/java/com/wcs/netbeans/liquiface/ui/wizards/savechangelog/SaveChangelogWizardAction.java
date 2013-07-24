/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.ui.wizards.savechangelog;

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

import com.wcs.netbeans.liquiface.change.store.ChangeSetMode;
import com.wcs.netbeans.liquiface.facade.ModelFacade;
import com.wcs.netbeans.liquiface.ui.wizards.AbstractWizardAction;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.WizardDescriptor;
import org.openide.WizardDescriptor.Panel;

/**
 *
 * @author tveki
 */
public class SaveChangelogWizardAction extends AbstractWizardAction {

    @Override
    protected List<Panel<WizardDescriptor>> getWizardPanels() {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<WizardDescriptor.Panel<WizardDescriptor>>();
        panels.add(new SaveChangelogWizardPanel1());
        panels.add(new SaveChangelogWizardPanel2());
        panels.add(new SaveChangelogWizardPanel3());
        return panels;
    }

    @Override
    protected String getTitle() {
        return "Save changelog";
    }

    @Override
    protected void onFinishWizard(WizardDescriptor wiz) {    
        ChangeSetMode changeSetMode = (ChangeSetMode) wiz.getProperty(SaveChangelogWizardConstants.CHANGESET_MODE);
        File changelogFile = (File) wiz.getProperty(SaveChangelogWizardConstants.CHANGELOG_FILE);
        System.out.format("Write changesets to file: %s with changeset mode %s%n",
                changelogFile.getAbsolutePath(), changeSetMode.name());
        ModelFacade.getInstance().writeChangeSetsToFile(changelogFile, changeSetMode);
        
        String message = "Changesets written to file " + changelogFile.getAbsolutePath();
        NotifyDescriptor nd = new NotifyDescriptor.Message(message, NotifyDescriptor.INFORMATION_MESSAGE);
        DialogDisplayer.getDefault().notify(nd);
    }   
    
}
