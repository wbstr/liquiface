/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.ui.wizards.addfk;

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

import com.wcs.netbeans.liquiface.facade.ModelFacade;
import com.wcs.netbeans.liquiface.model.Column;
import com.wcs.netbeans.liquiface.model.PrimaryKeyConstraint;
import com.wcs.netbeans.liquiface.model.Table;
import com.wcs.netbeans.liquiface.model.UniqueConstraint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public final class AddForeignKeyVisualPanel1 extends JPanel {
    
    private String tableName;
    private boolean referencesUniqueColumn = false;

    /**
     * Creates new form AddForeignKeyVisualPanel1
     */
    public AddForeignKeyVisualPanel1(String tableName) {
        initComponents();
        this.tableName = tableName;
        List<Table> tables = ModelFacade.getInstance().getTables();
        List<String> tableNames = new ArrayList<String>();
        tableNames.add("");
        for (Table table : tables) {
            tableNames.add(table.getName());
        }
        referencedTableField.setModel(new DefaultComboBoxModel(tableNames.toArray()));
        referencedTableField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String referencedTableName = (String) referencedTableField.getSelectedItem();
                if (!"".equals(referencedTableName)) {
                    List<String> constraintNames = new ArrayList<String>();
                    constraintNames.add("");
                    
                    Table referencedTable = ModelFacade.getInstance().getTableByName(referencedTableName);
                    PrimaryKeyConstraint pk = referencedTable.getPrimaryKeyConstraint();
                    
                    if (pk != null) {
                        constraintNames.add(pk.getName());
                    }
                    
                    for (UniqueConstraint uniqueConstraint : referencedTable.getUniqueConstraints()) {
                        constraintNames.add(uniqueConstraint.getName());
                    }
                    
                    referencedConstraintField.setModel(new DefaultComboBoxModel(constraintNames.toArray()));
                    
                    columnsTable.setModel(new AddForeignKeyTableModel(
                        new Object[][] {},
                        new String [] {"Referenced column", "Local column"}
                    ));
                }
            }
        });
        
        referencedConstraintField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String referencedTableName = (String) referencedTableField.getSelectedItem();
                String referencedConstraintName = (String) referencedConstraintField.getSelectedItem();
                
                if (!"".equals(referencedTableName) && !"".equals(referencedConstraintName)) {
                    Table referencedTable = ModelFacade.getInstance().getTableByName(referencedTableName);
                    PrimaryKeyConstraint pk = referencedTable.getPrimaryKeyConstraint();
                    List<Column> columns = new ArrayList<Column>();
                    
                    if (pk != null && pk.getName().equals(referencedConstraintName)) {
                        columns = pk.getColumns();
                        referencesUniqueColumn = true;
                    } else {
                        for (UniqueConstraint uniqueConstraint : referencedTable.getUniqueConstraints()) {
                            if (uniqueConstraint.getName().equals(referencedConstraintName)) {
                                columns = uniqueConstraint.getColumns();
                                referencesUniqueColumn = true;
                                break;
                            }
                        }
                    }
                    
                    Object[][] rows = new Object[columns.size()][2];
                    for (int i = 0; i < columns.size(); i++) {
                        rows[i] = new Object[] {
                            columns.get(i).getName(),
                            null
                        };
                        
                    }
                    
                    columnsTable.setModel(new AddForeignKeyTableModel(
                        rows,
                        new String [] {"Referenced column", "Local column"}
                    ));
                    
                    columnsTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JComboBox(getCurrentTableAllColumns().toArray())));
                }
            }
        });
    }
    
//    private List<String> getCurrentTableColumns(ColumnType columnType) {
//        Table table = ModelFacade.getInstance().getTableByName(tableName);
//        List<String> columns = new ArrayList<String>();
//        for (Column column : table.getColumns()) {
//            if (column.getColumnType().equals(columnType)) {
//                columns.add(column.getName());
//            }
//        }
//        return columns;
//    }
    
        private List<String> getCurrentTableAllColumns() {
        Table table = ModelFacade.getInstance().getTableByName(tableName);
        List<String> columns = new ArrayList<String>();
        for (Column column : table.getColumns()) {
            columns.add(column.getName());
        }
        return columns;
    }

    @Override
    public String getName() {
        return "Foreign key";
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        fkNameField = new javax.swing.JTextField();
        referencedTableField = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        referencedConstraintField = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        columnsTable = new javax.swing.JTable();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(AddForeignKeyVisualPanel1.class, "AddForeignKeyVisualPanel1.jLabel1.text")); // NOI18N

        fkNameField.setText(org.openide.util.NbBundle.getMessage(AddForeignKeyVisualPanel1.class, "AddForeignKeyVisualPanel1.fkNameField.text")); // NOI18N

        referencedTableField.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(AddForeignKeyVisualPanel1.class, "AddForeignKeyVisualPanel1.jLabel2.text")); // NOI18N

        referencedConstraintField.setModel(new javax.swing.DefaultComboBoxModel(new String[]{}));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(AddForeignKeyVisualPanel1.class, "AddForeignKeyVisualPanel1.jLabel3.text")); // NOI18N

        columnsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Referenced column", "Local column"
            }
        ));
        jScrollPane1.setViewportView(columnsTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(referencedTableField, 0, 161, Short.MAX_VALUE)
                            .addComponent(fkNameField)
                            .addComponent(referencedConstraintField, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(110, 110, 110))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jSeparator1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(fkNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(referencedTableField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(referencedConstraintField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(13, 13, 13)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable columnsTable;
    private javax.swing.JTextField fkNameField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JComboBox referencedConstraintField;
    private javax.swing.JComboBox referencedTableField;
    // End of variables declaration//GEN-END:variables

    public JTable getColumnsTable() {
        return columnsTable;
    }

    public JTextField getFkNameField() {
        return fkNameField;
    }

    public JComboBox getReferencedConstraintField() {
        return referencedConstraintField;
    }

    public JComboBox getReferencedTableField() {
        return referencedTableField;
    }

    public boolean isReferencesUniqueColumn() {
        return referencesUniqueColumn;
    }
}
