/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.ui.swing.popup;

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
import com.wcs.netbeans.liquiface.eventbus.event.RefreshToolbarToggleButtonsEvent;
import com.wcs.netbeans.liquiface.eventbus.event.RefreshToolbarToggleButtonsEvent.ToolbarButton;
import com.wcs.netbeans.liquiface.eventbus.event.ResetFiltersEvent;
import com.wcs.netbeans.liquiface.eventbus.event.ResetModelClearChangesEvent;
import com.wcs.netbeans.liquiface.facade.FilterFacade;
import com.wcs.netbeans.liquiface.facade.SceneFacade;
import com.wcs.netbeans.liquiface.ui.about.ShowAboutPanelAction;
import com.wcs.netbeans.liquiface.ui.wizards.createtable.CreateTableWizardAction;
import com.wcs.netbeans.liquiface.ui.wizards.loadchangelogfile.LoadChangeLogFileWizardAction;
import com.wcs.netbeans.liquiface.ui.wizards.loaddatabase.LoadDatabaseWizardAction;
import com.wcs.netbeans.liquiface.ui.wizards.savechangelog.SaveChangelogWizardAction;
import com.wcs.netbeans.liquiface.ui.wizards.updatedatabase.UpdateDatabaseWizardAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;

/**
 *
 * @author botond
 */
public class CanvasPopupMenu extends AbstractPopupMenu {

    public CanvasPopupMenu() {
        super();
        add(createCreateTableItem());
        add(new Separator());
        add(createNewFileItem());
        add(createLoadFromFileItem());
        add(createSaveChangelogItem());
        add(new Separator());
        add(createLoadFromDatabaseItem());
        add(createUpdateDatabaseItem());
        add(new Separator());
        add(createToggleForeignKeysItem());
        add(createToggleDirectRouterItem());
        add(createToggleAnimationsItem());
        add(new Separator());
        add(createAboutInfoItem());
    }

    private JMenuItem createCreateTableItem() {
        JMenuItem item = createMenuItem("Create table");
        item.setActionCommand("create_table");
        item.addActionListener(new CreateTableWizardAction());
        return item;
    }

    private JMenuItem createNewFileItem() {
        JMenuItem item = createMenuItem("Create new model");
        item.setActionCommand("new");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LiquifaceEventBus.getInstance().post(
                        new ResetModelClearChangesEvent(),
                        new ResetFiltersEvent(),
                        new RedrawGlobalSceneEvent(),
                        new RefreshToolbarModelCaptionEvent(null));
            }
        });
        return item;
    }

    private JMenuItem createLoadFromFileItem() {
        JMenuItem item = createMenuItem("Load from file");
        item.setActionCommand("load_file");
        item.addActionListener(new LoadChangeLogFileWizardAction());
        return item;
    }

    private JMenuItem createSaveChangelogItem() {
        JMenuItem item = createMenuItem("Save changelog");
        item.setActionCommand("save_changelog");
        item.addActionListener(new SaveChangelogWizardAction());
        return item;
    }

    private JMenuItem createUpdateDatabaseItem() {
        JMenuItem item = createMenuItem("Update database with changes");
        item.setActionCommand("update_database");
        item.addActionListener(new UpdateDatabaseWizardAction());
        return item;
    }

    private JMenuItem createLoadFromDatabaseItem() {
        JMenuItem item = createMenuItem("Load model from database");
        item.setActionCommand("load_from_database");
        item.addActionListener(new LoadDatabaseWizardAction());
        return item;
    }

    private JCheckBoxMenuItem createToggleForeignKeysItem() {
        final JCheckBoxMenuItem item = new JCheckBoxMenuItem("Show foreign keys");
        item.setSelected(FilterFacade.getInstance().isForeignKeysVisible());
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FilterFacade.getInstance().setForeignKeysVisible(item.isSelected());
                SceneFacade.getInstance().setRectangularLayout(!item.isSelected());
                LiquifaceEventBus.getInstance().post(
                        new RefreshToolbarToggleButtonsEvent(ToolbarButton.FOREIGN_KEYS, item.isSelected()));
            }
        });
        return item;
    }

    private JCheckBoxMenuItem createToggleDirectRouterItem() {
        final JCheckBoxMenuItem item = new JCheckBoxMenuItem("Use direct foreign key routing");
        item.setSelected(SceneFacade.getInstance().isDirectRouterUsed());
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(item.isSelected()){
                    SceneFacade.getInstance().setDirectRouter();
                } else {
                    SceneFacade.getInstance().setOrthogonalRouter();
                }
                LiquifaceEventBus.getInstance().post(
                        new RedrawGlobalSceneEvent(),
                        new RefreshToolbarToggleButtonsEvent(ToolbarButton.DIRECT_ROUTER, item.isSelected()));
            }
        });
        return item;
    }

    private JCheckBoxMenuItem createToggleAnimationsItem() {
        final JCheckBoxMenuItem item = new JCheckBoxMenuItem("Show animations");
        item.setSelected(SceneFacade.getInstance().isAnimated());
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SceneFacade.getInstance().setAnimated(item.isSelected());
                LiquifaceEventBus.getInstance().post(
                        new RedrawGlobalSceneEvent(),
                        new RefreshToolbarToggleButtonsEvent(ToolbarButton.ANIMATIONS, item.isSelected()));
            }
        });
        return item;
    }

    private JMenuItem createAboutInfoItem() {
        JMenuItem item = createMenuItem("About");
        item.setActionCommand("about");
        item.addActionListener(new ShowAboutPanelAction());
        return item;
    }

}
