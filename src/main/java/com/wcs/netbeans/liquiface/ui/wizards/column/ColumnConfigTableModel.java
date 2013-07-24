/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.ui.wizards.column;

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


import com.wcs.netbeans.liquiface.model.util.ColumnConfigUtil;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;
import liquibase.change.ColumnConfig;
import org.openide.util.ImageUtilities;

/**
 *
 * @author botond
 */
public class ColumnConfigTableModel extends AbstractTableModel {

    private enum Columns {
        NAME("Name", String.class),
        TYPE("Type", String.class),
        AUTOINCREMENT("AutoIncrement", Boolean.class),
        UNIQUE("Unique", Boolean.class),
        NOT_NULL("Not Null", Boolean.class),
        DELETE("Delete", JButton.class);

        private String columnName;
        private Class columnClass;
        private Columns(String columnName, Class columnClass) {
            this.columnName = columnName;
            this.columnClass = columnClass;
        }
        public String getColumnName() {
            return columnName;
        }
        public Class getColumnClass() {
            return columnClass;
        }
    }

    private List<ColumnConfig> columnConfigs = new ArrayList<ColumnConfig>();

    @Override
    public String getColumnName(int column) {
        return Columns.values()[column].getColumnName();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Columns.values()[columnIndex].getColumnClass();
    }

    @Override
    public int getRowCount() {
        return columnConfigs.size();
    }

    @Override
    public int getColumnCount() {
        return Columns.values().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ColumnConfig columnConfig = getRow(rowIndex);
        return getColumnValue(columnConfig, columnIndex);
    }

    private ColumnConfig getRow(int rowIndex) {
        return columnConfigs.get(rowIndex);
    }

    private Object getColumnValue(ColumnConfig row, int columnIndex) {
        switch(Columns.values()[columnIndex]) {
            case NAME:
                return row.getName();
            case TYPE:
                return row.getType();
            case AUTOINCREMENT:
                return row.isAutoIncrement();
            case UNIQUE:
                return getNullSafeRowUnique(row);
            case NOT_NULL:
                return getNullSafeRowNotNull(row);
            case DELETE:
                return getDeleteRowButton(row);
            default:
                return null;
        }
    }

    /*
     * ColumnConfig.isUnique adhat vissza null-t, de a táblázat a checkbox miatt azt nem szereti
     */
    private boolean getNullSafeRowUnique(ColumnConfig row) {
        return ColumnConfigUtil.isUnique(row);
    }

    /*
     * ColumnConfig.isNullable adhat vissza null-t, de a táblázat a checkbox miatt azt nem szereti
     */
    private boolean getNullSafeRowNotNull(ColumnConfig row) {
        return ColumnConfigUtil.isNotNull(row);
    }

    private JButton getDeleteRowButton(final ColumnConfig row) {
        final JButton deleteButton = new JButton();
        deleteButton.setText("");
        deleteButton.setIcon(getDeleteIcon());
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                columnConfigs.remove(row);
                fireTableDataChanged();
            }
        });
        return deleteButton;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return Columns.DELETE.equals(Columns.values()[columnIndex]);
    }

    public List<ColumnConfig> getColumnConfigs() {
        return Collections.unmodifiableList(columnConfigs);
    }

    public void addRow(ColumnConfig row) {
        columnConfigs.add(row);
        fireTableDataChanged();
    }

    private Icon getDeleteIcon() {
        Image image = ImageUtilities.loadImage("icons/wizard/delete_row_16x16px.png");
        return new ImageIcon(image);
    }

}
