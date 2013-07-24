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
import com.wcs.netbeans.liquiface.model.Table;
import com.wcs.netbeans.liquiface.ui.wizards.addcolumns.AddColumnsWizardAction;
import com.wcs.netbeans.liquiface.ui.wizards.addfk.AddForeignKeyWizardAction;
import com.wcs.netbeans.liquiface.ui.wizards.addpk.AddPrimaryKeyWizardAction;
import com.wcs.netbeans.liquiface.ui.wizards.addunique.AddUniqueConstraintWizardAction;
import com.wcs.netbeans.liquiface.ui.wizards.dropfk.DropForeignKeyWizardAction;
import com.wcs.netbeans.liquiface.ui.wizards.droppk.DropPrimaryKeyWizardAction;
import com.wcs.netbeans.liquiface.ui.wizards.droptable.DropTableWizardAction;
import com.wcs.netbeans.liquiface.ui.wizards.dropunique.DropUniqueConstraintWizardAction;
import com.wcs.netbeans.liquiface.ui.wizards.renametable.RenameTableWizardAction;
import javax.swing.JMenuItem;

/**
 *
 * @author botond
 */
public class TablePopupMenu extends AbstractPopupMenu{

    private Table table;

    public TablePopupMenu(int tableId) {
        super();
        this.table = ModelFacade.getInstance().getTableById(tableId);
        createTableMenu();
    }

    private void createTableMenu(){
        add(createAddColumnsItem());
        add(createAddPrimaryKeyItem());
        add(createDropPrimaryKeyItem());
        add(createAddUniqueConstraintItem());
        add(createDropUniqueConstraintItem());
        add(createAddForeignKeyItem());
        add(createDropForeignKeyItem());
        add(createRenameTableItem());
        add(createDropTableItem());
    }

    private JMenuItem createAddColumnsItem(){
        JMenuItem item = createMenuItem("Add columns");
        item.setActionCommand("add_columns");
        item.addActionListener(new AddColumnsWizardAction(table));
        return item;
    }

    private JMenuItem createAddPrimaryKeyItem(){
        JMenuItem item = createMenuItem("Add primary key");
        item.setActionCommand("add_primary_key");
        item.addActionListener(new AddPrimaryKeyWizardAction(table));
        item.setEnabled(table.getPrimaryKeyConstraint() == null);
        return item;
    }

    private JMenuItem createDropPrimaryKeyItem(){
        JMenuItem item = createMenuItem("Drop primary key");
        item.setActionCommand("drop_primary_key");
        item.addActionListener(new DropPrimaryKeyWizardAction(table));
        item.setEnabled(table.getPrimaryKeyConstraint() != null);
        return item;
    }

    private JMenuItem createAddUniqueConstraintItem(){
        JMenuItem item = createMenuItem("Add unique constraint");
        item.setActionCommand("add_unique");
        item.addActionListener(new AddUniqueConstraintWizardAction(table));
        return item;
    }

    private JMenuItem createDropUniqueConstraintItem(){
        JMenuItem item = createMenuItem("Drop unique constraint");
        item.setActionCommand("drop_unique");
        item.addActionListener(new DropUniqueConstraintWizardAction(table));
        item.setEnabled(!table.getUniqueConstraints().isEmpty());
        return item;
    }

    private JMenuItem createAddForeignKeyItem(){
        JMenuItem item = createMenuItem("Add foreign key");
        item.setActionCommand("add_foreign_key");
        item.addActionListener(new AddForeignKeyWizardAction(table));
        return item;
    }

    private JMenuItem createDropForeignKeyItem(){
        JMenuItem item = createMenuItem("Drop foreign key");
        item.setActionCommand("drop_foreign_key");
        item.addActionListener(new DropForeignKeyWizardAction(table));
        item.setEnabled(!table.getForeignKeyConstraints().isEmpty());
        return item;
    }

    private JMenuItem createRenameTableItem(){
        JMenuItem item = createMenuItem("Rename table");
        item.setActionCommand("rename_table");
        item.addActionListener(new RenameTableWizardAction(table));
        return item;
    }

    private JMenuItem createDropTableItem(){
        JMenuItem item = createMenuItem("Drop table");
        item.setActionCommand("drop_table");
        item.addActionListener(new DropTableWizardAction(table));
        return item;
    }
}
