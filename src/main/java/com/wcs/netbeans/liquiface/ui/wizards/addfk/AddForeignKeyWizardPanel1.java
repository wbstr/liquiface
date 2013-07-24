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

import com.wcs.netbeans.liquiface.model.Column;
import com.wcs.netbeans.liquiface.ui.wizards.AbstractWizardPanel;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;

public class AddForeignKeyWizardPanel1 extends AbstractWizardPanel {

    /**
     * The visual component that displays this panel. If you need to access the component from this class, just use
     * getComponent().
     */
    private AddForeignKeyVisualPanel1 component;
    private String tableName;

    public AddForeignKeyWizardPanel1(String tableName) {
        this.tableName = tableName;
    }

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public AddForeignKeyVisualPanel1 getComponent() {
        if (component == null) {
            component = new AddForeignKeyVisualPanel1(tableName);
        }
        return component;
    }
    
    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty(AddForeignKeyWizardConstants.FOREIGN_KEY_NAME, getComponent().getFkNameField().getText());
        wiz.putProperty(AddForeignKeyWizardConstants.REFERENCED_TABLE_NAME, (String) getComponent().getReferencedTableField().getSelectedItem());
        wiz.putProperty(AddForeignKeyWizardConstants.REFERENCES_UNIQUE_CONSTRAINT, getComponent().isReferencesUniqueColumn());
        
        Map<String, String> columnsMap = new HashMap<String, String>();
        JTable columnTable = getComponent().getColumnsTable();
        for (int i = 0; i < columnTable.getRowCount(); i++) {
            String referencedColumn = (String) columnTable.getValueAt(i, 0);
            String localColumn = (String) columnTable.getValueAt(i, 1);
            columnsMap.put(referencedColumn, localColumn);
        }
        wiz.putProperty(AddForeignKeyWizardConstants.COLUMN_MAP, columnsMap);
    }

    @Override
    public void validate() throws WizardValidationException {
        String fkName = getComponent().getFkNameField().getText();
        if (fkName.equals("")){
            throw new WizardValidationException(null, "The name is required!", null);
        }
        
        String referencedTableName = (String) getComponent().getReferencedTableField().getSelectedItem();
        if (referencedTableName.equals("")) {
            throw new WizardValidationException(null, "Referenced table is required!", null);
        }
        
        String referencedConstraintName = (String) getComponent().getReferencedConstraintField().getSelectedItem();
        if (referencedConstraintName.equals("")) {
            throw new WizardValidationException(null, "Referenced constraint is required!", null);
        }
        
        JTable columnTable = getComponent().getColumnsTable();
        for (int i = 0; i < columnTable.getRowCount(); i++) {
            Object localColumn = columnTable.getValueAt(i, 1);
            if (localColumn == null) {
                throw new WizardValidationException(null, "All local columns are required!", null);
            }
        }
    }
}
