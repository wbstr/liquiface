/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.liquifacemaven.component;

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
import com.wcs.netbeans.liquiface.eventbus.event.ResetFiltersEvent;
import com.wcs.netbeans.liquiface.eventbus.event.SynchronizeFiltersEvent;
import com.wcs.netbeans.liquiface.eventbus.event.SynchronizeFiltersWithNewTableEvent;
import com.wcs.netbeans.liquiface.facade.FilterFacade;
import com.wcs.netbeans.liquiface.facade.ModelFacade;
import com.wcs.netbeans.liquiface.model.Table;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author botond
 */
public class TableNameFilter extends javax.swing.JPanel {

    private List<String> tableNames;
    private List<String> filteredTableNames;
    private List<String> tableNamesToFilterScene;

    /**
     * Creates new form TableNameFilter
     */
    public TableNameFilter() {
        initComponents();
        resetFilter();
    }

    @Subscribe
    public void resetFilter(ResetFiltersEvent event) {
        resetFilter();
    }

    private void resetFilter() {
        initFilterList();
        initFilterNameField();
        filterNameField.setText("");
        tableNamesToFilterScene = new ArrayList<String>();
        filterList.setListData(tableNamesToFilterScene.toArray());
    }

    @Subscribe
    public void synchronizeFilter(SynchronizeFiltersEvent event) {
        initFilterList();
        doFilterOptions();
        for (String name : tableNamesToFilterScene) {
            if (!tableNames.contains(name)) {
                tableNamesToFilterScene.remove(name);
            }
        }
        if (!tableNamesToFilterScene.isEmpty() && event instanceof SynchronizeFiltersWithNewTableEvent) {
            String newTableName = ((SynchronizeFiltersWithNewTableEvent)event).getNewTable().getName();
            tableNamesToFilterScene.add(newTableName);
        }
        filterList.setListData(tableNamesToFilterScene.toArray());
    }

    private void initFilterNameField() {
        filterNameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }
            @Override
            public void focusLost(FocusEvent e) {
                doFilterOptions();
            }
        });
    }

    private void initFilterList() {
        List<Table> tables = ModelFacade.getInstance().getTables();
        tableNames = new ArrayList<String>();
        for (Table table : tables) {
            tableNames.add(table.getName());
        }
        filterOptionList.setListData(tableNames.toArray());
    }

    private void doFilterOptions() {
        filteredTableNames = new ArrayList<String>();
        String filter = filterNameField.getText();
        if (filter == null || filter.isEmpty()) {
            filterOptionList.setListData(tableNames.toArray());
        } else {
            String filterLowerCase = filter.toLowerCase();
            for (String tableName : tableNames) {
                String tableNameLowerCase = tableName.toLowerCase();
                if (tableNameLowerCase.startsWith(filterLowerCase)) {
                    filteredTableNames.add(tableName);
                }
            }
            filterOptionList.setListData(filteredTableNames.toArray());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        filterNameField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        filterOptionList = new javax.swing.JList();
        addSelectedButton = new javax.swing.JButton();
        addAllButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        filterList = new javax.swing.JList();
        clearSelectedButton = new javax.swing.JButton();
        clearAllButton = new javax.swing.JButton();
        filterButton = new javax.swing.JButton();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(TableNameFilter.class, "TableNameFilter.jLabel1.text")); // NOI18N

        filterNameField.setText(org.openide.util.NbBundle.getMessage(TableNameFilter.class, "TableNameFilter.filterNameField.text")); // NOI18N
        filterNameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterNameFieldActionPerformed(evt);
            }
        });

        filterOptionList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(filterOptionList);

        org.openide.awt.Mnemonics.setLocalizedText(addSelectedButton, org.openide.util.NbBundle.getMessage(TableNameFilter.class, "TableNameFilter.addSelectedButton.text")); // NOI18N
        addSelectedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSelectedButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(addAllButton, org.openide.util.NbBundle.getMessage(TableNameFilter.class, "TableNameFilter.addAllButton.text")); // NOI18N
        addAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAllButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(TableNameFilter.class, "TableNameFilter.jLabel2.text")); // NOI18N

        filterList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(filterList);

        org.openide.awt.Mnemonics.setLocalizedText(clearSelectedButton, org.openide.util.NbBundle.getMessage(TableNameFilter.class, "TableNameFilter.clearSelectedButton.text")); // NOI18N
        clearSelectedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearSelectedButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(clearAllButton, org.openide.util.NbBundle.getMessage(TableNameFilter.class, "TableNameFilter.clearAllButton.text")); // NOI18N
        clearAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearAllButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(filterButton, org.openide.util.NbBundle.getMessage(TableNameFilter.class, "TableNameFilter.filterButton.text")); // NOI18N
        filterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(filterNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(addSelectedButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addAllButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(clearSelectedButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clearAllButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(filterButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filterNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addSelectedButton)
                    .addComponent(addAllButton))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clearSelectedButton)
                    .addComponent(clearAllButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(filterButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void filterNameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterNameFieldActionPerformed
        doFilterOptions();
    }//GEN-LAST:event_filterNameFieldActionPerformed

    private void filterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterButtonActionPerformed
        List<Table> toFilter = new ArrayList<Table>();
        for (String tableName : tableNamesToFilterScene) {
            Table table = ModelFacade.getInstance().getTableByName(tableName);
            toFilter.add(table);
        }
        FilterFacade.getInstance().setFilteredTables(toFilter);
        LiquifaceEventBus.getInstance().post(new RedrawGlobalSceneEvent());
    }//GEN-LAST:event_filterButtonActionPerformed

    private void addSelectedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSelectedButtonActionPerformed
        Object[] selection = filterOptionList.getSelectedValues();
        for (Object object : selection) {
            String tableName = (String) object;
            if (!tableNamesToFilterScene.contains(tableName)) {
                tableNamesToFilterScene.add(tableName);
            }
        }
        filterList.setListData(tableNamesToFilterScene.toArray());
    }//GEN-LAST:event_addSelectedButtonActionPerformed

    private void addAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addAllButtonActionPerformed
        List<String> tableNamesToAdd = (!filterNameField.getText().isEmpty()) ? filteredTableNames : tableNames;
        for (String tableName : tableNamesToAdd) {
            if (!tableNamesToFilterScene.contains(tableName)) {
                tableNamesToFilterScene.add(tableName);
            }
        }
        filterList.setListData(tableNamesToFilterScene.toArray());
    }//GEN-LAST:event_addAllButtonActionPerformed

    private void clearSelectedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearSelectedButtonActionPerformed
        Object[] selection = filterList.getSelectedValues();
        for (Object object : selection) {
            String tableName = (String) object;
            tableNamesToFilterScene.remove(tableName);
        }
        filterList.setListData(tableNamesToFilterScene.toArray());
    }//GEN-LAST:event_clearSelectedButtonActionPerformed

    private void clearAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearAllButtonActionPerformed
        tableNamesToFilterScene = new ArrayList<String>();
        filterList.setListData(tableNamesToFilterScene.toArray());
    }//GEN-LAST:event_clearAllButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addAllButton;
    private javax.swing.JButton addSelectedButton;
    private javax.swing.JButton clearAllButton;
    private javax.swing.JButton clearSelectedButton;
    private javax.swing.JButton filterButton;
    private javax.swing.JList filterList;
    private javax.swing.JTextField filterNameField;
    private javax.swing.JList filterOptionList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
