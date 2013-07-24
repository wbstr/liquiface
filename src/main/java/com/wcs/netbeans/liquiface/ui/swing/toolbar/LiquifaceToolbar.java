/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.ui.swing.toolbar;

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


import com.google.common.eventbus.Subscribe;
import com.wcs.netbeans.liquiface.eventbus.LiquifaceEventBus;
import com.wcs.netbeans.liquiface.eventbus.event.RedrawGlobalSceneEvent;
import com.wcs.netbeans.liquiface.eventbus.event.RefreshToolbarModelCaptionEvent;
import com.wcs.netbeans.liquiface.eventbus.event.RefreshToolbarToggleButtonsEvent;
import static com.wcs.netbeans.liquiface.eventbus.event.RefreshToolbarToggleButtonsEvent.ToolbarButton.*;
import com.wcs.netbeans.liquiface.eventbus.event.ResetFiltersEvent;
import com.wcs.netbeans.liquiface.eventbus.event.ResetModelClearChangesEvent;
import com.wcs.netbeans.liquiface.facade.FilterFacade;
import com.wcs.netbeans.liquiface.facade.SceneFacade;
import com.wcs.netbeans.liquiface.ui.about.ShowAboutPanelAction;
import com.wcs.netbeans.liquiface.ui.wizards.appendchangelog.AppendToChangelogFileWizardAction;
import com.wcs.netbeans.liquiface.ui.wizards.loadchangelogfile.LoadChangeLogFileWizardAction;
import com.wcs.netbeans.liquiface.ui.wizards.loaddatabase.LoadDatabaseWizardAction;
import com.wcs.netbeans.liquiface.ui.wizards.savechangelog.SaveChangelogWizardAction;
import com.wcs.netbeans.liquiface.ui.wizards.updatedatabase.UpdateDatabaseWizardAction;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import org.openide.util.ImageUtilities;

/**
 *
 * @author botond
 */
public class LiquifaceToolbar extends JToolBar {

    //<editor-fold defaultstate="collapsed" desc="singleton stuff">
    private static LiquifaceToolbar instance;

    public static LiquifaceToolbar getInstance(){
        if(instance == null){
            instance = new LiquifaceToolbar();
            LiquifaceEventBus.getInstance().register(instance);
        }
        return instance;
    }
    //</editor-fold>

    private JToggleButton foreignKeysButton;
    private JToggleButton directRouterButton;
    private JToggleButton animationsButton;
    private JLabel changelogSourceLabel;

    private LiquifaceToolbar() {
        add(createNewFileButton());
        add(createLoadFromFileButton());
        add(createSaveChangelogButton());
        add(createAppendToChangelogButton());
        add(new Separator());
        add(createLoadFromDatabaseButton());
        add(createUpdateDatabaseButton());
        add(new Separator());
        foreignKeysButton = createToggleForeignKeysButton();
        add(foreignKeysButton);
        directRouterButton = createToggleDirectRouterButton();
        add(directRouterButton);
        animationsButton = createToggleAnimationsButton();
        add(animationsButton);
        add(new Separator());
        add(createAboutInfoButton());
        add(new Separator());
        changelogSourceLabel = new JLabel();
        changelogSourceLabel.setMinimumSize(new Dimension(0, 0));
        add(changelogSourceLabel);
    }

    private JButton createSaveChangelogButton() {
        JButton button = createButton("save_16x16px.png");
        button.setToolTipText("Save changelog");
        button.setActionCommand("save_changelog");
        button.addActionListener(new SaveChangelogWizardAction());
        return button;
    }

    private JButton createUpdateDatabaseButton() {
        JButton button = createButton("database_run_16x16px.png");
        button.setToolTipText("Update database");
        button.setActionCommand("update_database");
        button.addActionListener(new UpdateDatabaseWizardAction());
        return button;
    }

    private JButton createAppendToChangelogButton() {
        JButton button = createButton("append_16x16px.png");
        button.setToolTipText("Append to changelog");
        button.setActionCommand("append_to_changelog");
        button.addActionListener(new AppendToChangelogFileWizardAction());
        return button;
    }

    private JButton createLoadFromDatabaseButton() {
        JButton button = createButton("database_connect_16x16px.png");
        button.setToolTipText("Load model from database");
        button.setActionCommand("load_database");
        button.addActionListener(new LoadDatabaseWizardAction());
        return button;
    }

    private JButton createLoadFromFileButton() {
        JButton button = createButton("open_16x16px.png");
        button.setToolTipText("Load model from file");
        button.setActionCommand("load_file");
        button.addActionListener(new LoadChangeLogFileWizardAction());
        return button;
    }

    private JButton createNewFileButton() {
        JButton button = createButton("new_16x16px.png");
        button.setToolTipText("Create new");
        button.setActionCommand("new");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LiquifaceEventBus.getInstance().post(
                        new ResetModelClearChangesEvent(),
                        new ResetFiltersEvent(),
                        new RedrawGlobalSceneEvent());
                refreshToolbar(new RefreshToolbarModelCaptionEvent(null));
            }
        });
        return button;
    }

    private JToggleButton createToggleForeignKeysButton() {
        final JToggleButton button = createToggleButton(
                "foreign_key_not_visible_16x16px.png",
                "foreign_key_visible_16x16px.png");
        button.setToolTipText("Show/hide foreign keys");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FilterFacade.getInstance().setForeignKeysVisible(button.isSelected());
                SceneFacade.getInstance().setRectangularLayout(!button.isSelected());
            }
        });
        return button;
    }

    private JToggleButton createToggleAnimationsButton() {
        final JToggleButton button = createToggleButton(
                "table_animation_not_visible_16x16px.png",
                "table_animation_visible_16x16px.png");
        button.setToolTipText("Animation ON/OFF");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SceneFacade.getInstance().setAnimated(button.isSelected());
                LiquifaceEventBus.getInstance().post(new RedrawGlobalSceneEvent());
            }
        });
        return button;
    }

    private JToggleButton createToggleDirectRouterButton() {
        final JToggleButton button = createToggleButton("orthogonal_router_16x16px.png", "direct_router_16x16px.png");
        button.setToolTipText("Toggle Direct/Orthogonal foreign key routing");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(button.isSelected()){
                    SceneFacade.getInstance().setDirectRouter();
                } else {
                    SceneFacade.getInstance().setOrthogonalRouter();
                }
                LiquifaceEventBus.getInstance().post(new RedrawGlobalSceneEvent());
            }
        });
        return button;
    }

    private JButton createAboutInfoButton() {
        JButton button = createButton("about_16x16px.png");
        button.setToolTipText("About");
        button.setActionCommand("about");
        button.addActionListener(new ShowAboutPanelAction());
        return button;
    }

    @Subscribe
    public void refreshToolbar(RefreshToolbarModelCaptionEvent event) {
        if (event.getModelSourceCaption() != null){
            changelogSourceLabel.setText("Model loaded from: " +event.getModelSourceCaption());
            changelogSourceLabel.setVisible(true);
        }
        else {
            changelogSourceLabel.setText("");
            changelogSourceLabel.setVisible(false);
        }
    }

    @Subscribe
    public void refreshToolbar(RefreshToolbarToggleButtonsEvent event) {
        switch (event.getToolbarButton()) {
            case FOREIGN_KEYS:
                foreignKeysButton.setSelected(event.isSelected());
                break;
            case DIRECT_ROUTER:
                directRouterButton.setSelected(event.isSelected());
                break;
            case ANIMATIONS:
                animationsButton.setSelected(event.isSelected());
                break;
        }
    }


    //<editor-fold defaultstate="collapsed" desc="helpers">
    private JButton createButton(String iconName) {
        Icon icon = getIcon(iconName);
        JButton button = new JButton(icon);
        button.setFocusPainted(false);
        return button;
    }

    private JToggleButton createToggleButton(String iconName, String selectedIconName) {
        Icon icon = getIcon(iconName);
        Icon pressedIcon = getIcon(selectedIconName);
        JToggleButton button = new JToggleButton(icon);
        button.setSelectedIcon(pressedIcon);
        button.setFocusPainted(false);
        return button;
    }

    private Icon getIcon(String iconName) {
        Image image = ImageUtilities.loadImage("icons/toolbar/" + iconName);
        return new ImageIcon(image);
    }
    //</editor-fold>

}
