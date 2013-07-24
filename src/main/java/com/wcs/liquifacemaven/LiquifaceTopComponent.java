/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.liquifacemaven;

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

import com.wcs.liquifacemaven.component.TableNameFilter;
import com.wcs.liquifacemaven.component.TableRelationFilter;
import com.wcs.netbeans.liquiface.eventbus.LiquifaceEventBus;
import com.wcs.netbeans.liquiface.facade.SceneFacade;
import com.wcs.netbeans.liquiface.facade.TopComponentFacade;
import com.wcs.netbeans.liquiface.ui.swing.toolbar.LiquifaceToolbar;
import com.wcs.netbeans.liquiface.ui.widget.LiquifaceGraphScene;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//com.wcs.liquifacemaven//Liquiface//EN",
autostore = false)
@TopComponent.Description(
        preferredID = "LiquifaceTopComponent",
        iconBase="icons/liquiface_icon_small.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
    )
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "com.wcs.liquifacemaven.LiquifaceTopComponent")
@ActionReference(path = "Menu/Window", position = 1000)
@TopComponent.OpenActionRegistration(displayName = "#CTL_LiquifaceAction",
preferredID = "LiquifaceTopComponent")
@Messages({
    "CTL_LiquifaceAction=Liquiface",
    "CTL_LiquifaceTopComponent=Liquiface",
    "HINT_LiquifaceTopComponent=GUI for Liquibase"
})
public final class LiquifaceTopComponent extends TopComponent {

    private LiquifaceGraphScene scene = new LiquifaceGraphScene();

    public LiquifaceTopComponent() {
        initComponents();
        jScrollPane1.setViewportView( scene.createView() );
        setName(Bundle.CTL_LiquifaceTopComponent());
        setToolTipText(Bundle.HINT_LiquifaceTopComponent());
        SceneFacade.getInstance().init(scene);
        jSplitPane1.setDividerLocation(20);
        jSplitPane1.setDividerSize(0);
        filterPanel.setMinimumSize(new Dimension(20, 0));
        filterPanel.setMaximumSize(new Dimension(20, Short.MAX_VALUE));

        final JTabbedPane jTabbedPane = new JTabbedPane();
        final JToggleButton filterToggler = new JToggleButton();
        filterToggler.setAlignmentX(0.5f);
        filterToggler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (filterToggler.isSelected()) {
                    int newWidth = jTabbedPane.getWidth() + filterToggler.getWidth();
                    jSplitPane1.setDividerLocation(newWidth);
                    filterPanel.setMinimumSize(new Dimension(newWidth, 0));
                    filterPanel.setMaximumSize(new Dimension(newWidth, Short.MAX_VALUE));
                } else {
                    jSplitPane1.setDividerLocation(20);
                    filterPanel.setMinimumSize(new Dimension(20, 0));
                    filterPanel.setMaximumSize(new Dimension(20, Short.MAX_VALUE));
                }
            }
        });

        javax.swing.GroupLayout filterButtonPanelLayout = new javax.swing.GroupLayout(filterButtonPanel);
        filterButtonPanel.setLayout(filterButtonPanelLayout);
        filterButtonPanelLayout.setHorizontalGroup(
            filterButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(filterToggler, GroupLayout.Alignment.CENTER, 0, 20, Short.MAX_VALUE)
        );
        filterButtonPanelLayout.setVerticalGroup(
            filterButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(filterToggler, GroupLayout.Alignment.CENTER, 0, 481, Short.MAX_VALUE)
        );

        TableNameFilter tableNameFilter = new TableNameFilter();
        LiquifaceEventBus.getInstance().register(tableNameFilter);
        jTabbedPane.add("NameFilter", tableNameFilter);
        TableRelationFilter tableRelationFilter = new TableRelationFilter();
        LiquifaceEventBus.getInstance().register(tableRelationFilter);
        jTabbedPane.add("RelationFilter", tableRelationFilter);

        javax.swing.GroupLayout filterPanelLayout = new javax.swing.GroupLayout(filterPanel);
        filterPanel.setLayout(filterPanelLayout);
        filterPanelLayout.setHorizontalGroup(
            filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filterPanelLayout.createSequentialGroup()
                .addComponent(filterButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(226, 226, 226))
        );
        filterPanelLayout.setVerticalGroup(
            filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(filterButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this
     * method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = LiquifaceToolbar.getInstance();
        jSplitPane1 = new javax.swing.JSplitPane();
        filterPanel = new javax.swing.JPanel();
        filterButtonPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        javax.swing.GroupLayout filterButtonPanelLayout = new javax.swing.GroupLayout(filterButtonPanel);
        filterButtonPanel.setLayout(filterButtonPanelLayout);
        filterButtonPanelLayout.setHorizontalGroup(
            filterButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        filterButtonPanelLayout.setVerticalGroup(
            filterButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 706, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout filterPanelLayout = new javax.swing.GroupLayout(filterPanel);
        filterPanel.setLayout(filterPanelLayout);
        filterPanelLayout.setHorizontalGroup(
            filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filterPanelLayout.createSequentialGroup()
                .addComponent(filterButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(226, 226, 226))
        );
        filterPanelLayout.setVerticalGroup(
            filterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(filterButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(filterPanel);
        jSplitPane1.setRightComponent(jScrollPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1151, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSplitPane1))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel filterButtonPanel;
    private javax.swing.JPanel filterPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        TopComponentFacade.getInstance().setLiquifaceTopComponent(this);
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }
}
