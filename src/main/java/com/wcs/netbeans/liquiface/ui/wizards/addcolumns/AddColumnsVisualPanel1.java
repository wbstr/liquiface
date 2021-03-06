/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.ui.wizards.addcolumns;

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

import com.wcs.netbeans.liquiface.ui.wizards.column.ColumnConfigTableModel;
import com.wcs.netbeans.liquiface.ui.wizards.column.TableButtonEditor;
import com.wcs.netbeans.liquiface.ui.wizards.column.TableButtonRenderer;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import liquibase.change.ColumnConfig;

public final class AddColumnsVisualPanel1 extends JPanel {

    private ColumnConfigTableModel tableModel = new ColumnConfigTableModel();

    /**
     * Creates new form AddColumnsVisualPanel1
     */
    public AddColumnsVisualPanel1() {
        initComponents();
        columnsTable.setDefaultRenderer(JButton.class, new TableButtonRenderer());
        columnsTable.setDefaultEditor(JButton.class, new TableButtonEditor());
        columnsTable.getColumnModel().getColumn(5).setPreferredWidth(40);
    }

    @Override
    public String getName() {
        return "Add Columns";
    }

    public List<ColumnConfig> getColumnConfigs() {
        return tableModel.getColumnConfigs();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        addColumnPanel1 = new com.wcs.netbeans.liquiface.ui.wizards.column.UiColumnPanel();
        addColumnButton = new javax.swing.JButton();
        addColumnErrorLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        columnsTable = new javax.swing.JTable();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(AddColumnsVisualPanel1.class, "AddColumnsVisualPanel1.jPanel1.border.title"))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(addColumnButton, org.openide.util.NbBundle.getMessage(AddColumnsVisualPanel1.class, "AddColumnsVisualPanel1.addColumnButton.text")); // NOI18N
        addColumnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addColumnButtonActionPerformed(evt);
            }
        });

        addColumnErrorLabel.setForeground(new java.awt.Color(255, 0, 0));
        org.openide.awt.Mnemonics.setLocalizedText(addColumnErrorLabel, org.openide.util.NbBundle.getMessage(AddColumnsVisualPanel1.class, "AddColumnsVisualPanel1.addColumnErrorLabel.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(addColumnErrorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(addColumnButton))
                    .addComponent(addColumnPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(addColumnPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addColumnButton)
                    .addComponent(addColumnErrorLabel))
                .addContainerGap())
        );

        columnsTable.setModel(tableModel);
        columnsTable.setFocusable(false);
        columnsTable.setRowSelectionAllowed(false);
        jScrollPane1.setViewportView(columnsTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addColumnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addColumnButtonActionPerformed
        ColumnConfig columnConfig = addColumnPanel1.getColumnConfig();
        if (columnConfig.getName() == null || columnConfig.getName().isEmpty()) {
            addColumnErrorLabel.setText("Name is required!");
        } else if (columnConfig.getType()== null || columnConfig.getType().isEmpty()) {
            addColumnErrorLabel.setText("Type is required!");
        } else {
            addColumnErrorLabel.setText("");
            tableModel.addRow(columnConfig);
            addColumnPanel1.resetState();
        }
    }//GEN-LAST:event_addColumnButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addColumnButton;
    private javax.swing.JLabel addColumnErrorLabel;
    private com.wcs.netbeans.liquiface.ui.wizards.column.UiColumnPanel addColumnPanel1;
    private javax.swing.JTable columnsTable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
