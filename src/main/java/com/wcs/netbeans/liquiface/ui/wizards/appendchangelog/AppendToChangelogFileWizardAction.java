/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.ui.wizards.appendchangelog;

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
import com.wcs.netbeans.liquiface.util.XmlMerger;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.WizardDescriptor;
import org.openide.WizardDescriptor.Panel;

public final class AppendToChangelogFileWizardAction extends AbstractWizardAction {

    @Override
    protected List<Panel<WizardDescriptor>> getWizardPanels() {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<WizardDescriptor.Panel<WizardDescriptor>>();
        panels.add(new AppendToChangelogFileWizardPanel1());
        panels.add(new AppendToChangelogFileWizardPanel2());
        panels.add(new AppendToChangelogFileWizardPanel3());
        return panels;
    }

    @Override
    protected String getTitle() {
        return "Append to changelog file";
    }

    @Override
    protected void onFinishWizard(WizardDescriptor wiz) {
        File originalChangelog = (File) wiz.getProperty(AppendToChangelogFileWizardConstants.CHANGELOG_FILE);
        ChangeSetMode changeSetMode = (ChangeSetMode) wiz.getProperty(AppendToChangelogFileWizardConstants.CHANGESET_MODE);
        
        File copyOfOriginalChangelog = createTempChangelog();
        File changelogWithNewChanges = createTempChangelog();
        
        copyFile(originalChangelog, copyOfOriginalChangelog);
        writeChangeSetsToFile(changelogWithNewChanges, changeSetMode);
        
        String message = null;
        
        XmlMerger merger = new XmlMerger();
        
        try {
            merger.merge(originalChangelog, copyOfOriginalChangelog, changelogWithNewChanges);
            message = "Changesets appended to " +originalChangelog.getAbsolutePath();
        } catch (Exception ex) {
            LOG().log(Level.INFO, null, ex);
            message = ex.getMessage();
        }        
         
        NotifyDescriptor nd = new NotifyDescriptor.Message(message, NotifyDescriptor.INFORMATION_MESSAGE);
        DialogDisplayer.getDefault().notify(nd);
    }
    
    private void copyFile(File source, File target){
        try {
            FileUtils.copyFile(source, target);
        } catch (IOException ex) {
            LOG().log(Level.SEVERE, null, ex);
        }
    }
    
    private void writeChangeSetsToFile(File changelog, ChangeSetMode changeSetMode){
        ModelFacade.getInstance().writeChangeSetsToFile(changelog, changeSetMode);
    }
    
    private File createTempChangelog(){
        File changelog = null;
        try {
            changelog = File.createTempFile("changelog", ".xml");
            changelog.deleteOnExit();
        } catch (IOException ex) {
            LOG().log(Level.SEVERE, null, ex);
        }
        
        return changelog;
    }
    
    private static Logger LOG(){
        return Logger.getLogger(AppendToChangelogFileWizardAction.class.getName());
    }
    
}
