/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.ui.wizards.loadchangelogfile;

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

import com.wcs.netbeans.liquiface.eventbus.LiquifaceEventBus;
import com.wcs.netbeans.liquiface.eventbus.event.RedrawGlobalSceneEvent;
import com.wcs.netbeans.liquiface.eventbus.event.RedrawSceneEvent;
import com.wcs.netbeans.liquiface.eventbus.event.RefreshToolbarModelCaptionEvent;
import com.wcs.netbeans.liquiface.eventbus.event.ReloadModelFromChangeLogFileEvent;
import com.wcs.netbeans.liquiface.eventbus.event.ResetFiltersEvent;
import com.wcs.netbeans.liquiface.eventbus.event.ResetModelClearChangesEvent;
import com.wcs.netbeans.liquiface.ui.wizards.AbstractWizardAction;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.openide.WizardDescriptor;
import org.openide.WizardDescriptor.Panel;

public final class LoadChangeLogFileWizardAction extends AbstractWizardAction {

    @Override
    protected List<Panel<WizardDescriptor>> getWizardPanels() {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<WizardDescriptor.Panel<WizardDescriptor>>();
        panels.add(new LoadChangeLogFileWizardPanel1());
        return panels;
    }

    @Override
    protected String getTitle() {
        return "Load model from changelog file";
    }

    @Override
    protected void onFinishWizard(WizardDescriptor wiz) {
        File changeLogFile = (File) wiz.getProperty(LoadChangeLogFileWizardConstants.SELECTED_CHANGELOG_FILE);
        LiquifaceEventBus.getInstance().post(
                new ResetModelClearChangesEvent(),
                new RedrawGlobalSceneEvent(),
                new ReloadModelFromChangeLogFileEvent(changeLogFile),
                new ResetFiltersEvent(),
                new RedrawGlobalSceneEvent(),
                new RefreshToolbarModelCaptionEvent(changeLogFile.getAbsolutePath())
                );
    }
}
