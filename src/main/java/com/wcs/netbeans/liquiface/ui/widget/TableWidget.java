/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.ui.widget;

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

import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.visual.vmd.VMDColorScheme;
import org.netbeans.api.visual.vmd.VMDNodeWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author athalay
 */
public class TableWidget extends VMDNodeWidget {
    
    private Integer tableId;

    public TableWidget(Scene scene) {
        super(scene);
    }

    public TableWidget(Scene scene, VMDColorScheme scheme) {
        super(scene, scheme);
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }
    
    public List<ColumnWidget> getColumnWidgets(){
        List<ColumnWidget> resultList = new ArrayList<ColumnWidget>();
        for (Widget child : getChildren()) {
            if(child instanceof ColumnWidget){
                resultList.add((ColumnWidget) child);
            }
        }
        return resultList;
    }
    
    public List<Integer> getColumnWidgetsIds(){
        List<Integer> resultList = new ArrayList<Integer>();
        for (Widget child : getChildren()) {
            if(child instanceof ColumnWidget){
                resultList.add(((ColumnWidget) child).getColumnId());
            }
        }
        return resultList;
    }
}
