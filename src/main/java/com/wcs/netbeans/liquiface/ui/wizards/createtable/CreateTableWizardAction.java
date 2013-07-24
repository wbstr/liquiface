/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.ui.wizards.createtable;

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

import static com.wcs.netbeans.liquiface.ui.wizards.createtable.CreateTableWizardConstants.*;

import com.wcs.netbeans.liquiface.eventbus.LiquifaceEventBus;
import com.wcs.netbeans.liquiface.eventbus.event.LiquibaseChangeEvent;
import com.wcs.netbeans.liquiface.eventbus.event.RedrawGlobalSceneEvent;
import com.wcs.netbeans.liquiface.eventbus.event.SynchronizeFiltersWithNewTableEvent;
import com.wcs.netbeans.liquiface.facade.ModelFacade;
import com.wcs.netbeans.liquiface.model.Table;
import com.wcs.netbeans.liquiface.ui.wizards.AbstractWizardAction;
import java.util.ArrayList;
import java.util.List;
import liquibase.change.Change;
import liquibase.change.ColumnConfig;
import liquibase.change.core.CreateTableChange;
import org.openide.WizardDescriptor;
import org.openide.WizardDescriptor.Panel;

public final class CreateTableWizardAction extends AbstractWizardAction {

    @Override
    protected List<Panel<WizardDescriptor>> getWizardPanels() {
        List<Panel<WizardDescriptor>> panels = new ArrayList<Panel<WizardDescriptor>>();
        panels.add(new CreateTableWizardPanel1());
        return panels;
    }

    @Override
    protected String getTitle() {
        return "Create Table";
    }

    protected Change createChange(WizardDescriptor wiz) {
        String tableName = (String) wiz.getProperty(TABLE_NAME);
        List<ColumnConfig> columnConfigs = (List<ColumnConfig>) wiz.getProperty(COLUMN_CONFIGS);

        CreateTableChange createTableChange = new CreateTableChange();
        createTableChange.setTableName(tableName);

        for (ColumnConfig columnConfig : columnConfigs) {
            createTableChange.addColumn(columnConfig);
        }

        return createTableChange;
    }

    @Override
    protected void onFinishWizard(WizardDescriptor wiz) {
        Change change = createChange(wiz);
        LiquifaceEventBus.getInstance().post(new LiquibaseChangeEvent(change));

        String tableName = (String) wiz.getProperty(CreateTableWizardConstants.TABLE_NAME);
        Table table = ModelFacade.getInstance().getTableByName(tableName);
        if (table != null) {
            LiquifaceEventBus.getInstance().post(new SynchronizeFiltersWithNewTableEvent(table));
        }

        LiquifaceEventBus.getInstance().post(new RedrawGlobalSceneEvent());
    }
}
