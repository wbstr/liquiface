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

import com.wcs.netbeans.liquiface.facade.ModelFacade;
import com.wcs.netbeans.liquiface.model.Column;
import com.wcs.netbeans.liquiface.model.Table;
import com.wcs.netbeans.liquiface.ui.wizards.addnotnull.AddNotNullWizardAction;
import com.wcs.netbeans.liquiface.ui.wizards.dropcolumn.DropColumnWizardAction;
import com.wcs.netbeans.liquiface.ui.wizards.dropnotnull.DropNotNullWizardAction;
import com.wcs.netbeans.liquiface.ui.wizards.modifydatatype.ModifyDataTypeWizardAction;
import com.wcs.netbeans.liquiface.ui.wizards.renamecolumn.RenameColumnWizardAction;
import javax.swing.JMenuItem;

/**
 *
 * @author athalay
 */
public class ColumnPopupMenu extends AbstractPopupMenu {

    private Table table;
    private Column column;

    public ColumnPopupMenu(int tableId, Integer columnId) {
        super();
        this.column = ModelFacade.getInstance().getTableById(tableId).getColumnById(columnId);
        this.table = ModelFacade.getInstance().getTableById(tableId);
        createColumnMenu();
    }

    private void createColumnMenu() {
        add(createRenameColumnItem());
        add(createDropColumnItem());
        add(createAddNotNullItem());
        add(createDropNotNullItem());
        add(createModifyColumnDataTypeItem());
    }

    private JMenuItem createRenameColumnItem() {
        JMenuItem item = createMenuItem("Rename column");
        item.setActionCommand("rename_column");
        item.addActionListener(new RenameColumnWizardAction(table, column));
        return item;
    }

    private JMenuItem createModifyColumnDataTypeItem() {
        JMenuItem item = createMenuItem("Modify data type");
        item.setActionCommand("modify_data_type");
        item.addActionListener(new ModifyDataTypeWizardAction(table, column));
        return item;
    }

    private JMenuItem createDropColumnItem() {
        JMenuItem item = createMenuItem("Drop column");
        item.setActionCommand("drop_column");
        item.addActionListener(new DropColumnWizardAction(table, column));
        return item;
    }

    private JMenuItem createAddNotNullItem() {
        JMenuItem item = createMenuItem("Add not null constraint");
        item.setActionCommand("add_not_null");
        item.addActionListener(new AddNotNullWizardAction(table, column));
        item.setEnabled(column.isNullable());
        return item;
    }

    private JMenuItem createDropNotNullItem() {
        JMenuItem item = createMenuItem("Drop not null constraint");
        item.setActionCommand("drop_not_null");
        item.addActionListener(new DropNotNullWizardAction(table, column));
        item.setEnabled(!column.isNullable());
        return item;
    }
}
