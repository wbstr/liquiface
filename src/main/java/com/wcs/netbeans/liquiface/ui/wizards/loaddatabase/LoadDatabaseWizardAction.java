/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.ui.wizards.loaddatabase;

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
import com.wcs.netbeans.liquiface.eventbus.event.RefreshToolbarModelCaptionEvent;
import com.wcs.netbeans.liquiface.eventbus.event.ReloadModelFromDatabaseEvent;
import com.wcs.netbeans.liquiface.eventbus.event.ResetFiltersEvent;
import com.wcs.netbeans.liquiface.eventbus.event.ResetModelClearChangesEvent;
import com.wcs.netbeans.liquiface.ui.wizards.AbstractWizardAction;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.netbeans.api.db.explorer.DatabaseConnection;
import org.openide.WizardDescriptor;
import org.openide.WizardDescriptor.Panel;
import org.openide.util.Exceptions;

public final class LoadDatabaseWizardAction extends AbstractWizardAction {

    @Override
    protected List<Panel<WizardDescriptor>> getWizardPanels() {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<WizardDescriptor.Panel<WizardDescriptor>>();
        panels.add(new LoadDatabaseWizardPanel1());
        return panels;
    }

    @Override
    protected String getTitle() {
        return "Load model from database";
    }

    @Override
    protected void onFinishWizard(final WizardDescriptor wiz) {
        DatabaseConnection databaseConnection = (DatabaseConnection) wiz.getProperty(LoadDatabaseWizardConstants.SELECTED_DATABASE);
        LiquifaceEventBus.getInstance().post(
                new ResetModelClearChangesEvent(),
                new RedrawGlobalSceneEvent());
        reloadModelAsynchronously(databaseConnection);
        LiquifaceEventBus.getInstance().post(
                new ResetFiltersEvent(),
                new RedrawGlobalSceneEvent(),
                new RefreshToolbarModelCaptionEvent(databaseConnection.getName())
                );
    }

    private void reloadModelAsynchronously(final DatabaseConnection databaseConnection) {
        final Future future = Executors.newFixedThreadPool(1).submit(new Callable() {
            @Override
            public Object call() throws Exception {
                LiquifaceEventBus.getInstance().post(new ReloadModelFromDatabaseEvent(databaseConnection));
                return null;
            }
        });

        try {
            future.get();
        } catch (InterruptedException ex) {
            Exceptions.printStackTrace(ex);
        } catch (ExecutionException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
