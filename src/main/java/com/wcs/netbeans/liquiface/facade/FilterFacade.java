/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.facade;

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
import com.wcs.netbeans.liquiface.eventbus.event.ResetFiltersEvent;
import com.wcs.netbeans.liquiface.eventbus.event.SynchronizeFiltersWithNewTableEvent;
import com.wcs.netbeans.liquiface.model.Table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author athalay
 */
public class FilterFacade {

    //<editor-fold defaultstate="collapsed" desc="singleton stuff">
    private static FilterFacade instance;

    public static FilterFacade getInstance(){
        if(instance == null){
            instance = new FilterFacade();
            LiquifaceEventBus.getInstance().register(instance);
        }
        return instance;
    }

    private FilterFacade(){
    }
    //</editor-fold>

    private boolean foreignKeysVisible = false;

    private List<Table> filteredTables = new ArrayList<Table>();

    public boolean isForeignKeysVisible(){
        return foreignKeysVisible;
    }

    public void setForeignKeysVisible(boolean foreignKeysVisible) {
        this.foreignKeysVisible = foreignKeysVisible;
    }

    public List<Table> getFilteredTables() {
        return filteredTables;
    }

    public void setFilteredTables(List<Table> filteredTables) {
        this.filteredTables = filteredTables;
    }

    @Subscribe
    public void resetFilters(ResetFiltersEvent event) {
        setFilteredTables(new ArrayList<Table>());
    }

    @Subscribe
    public void synchronizeNewTable(SynchronizeFiltersWithNewTableEvent event) {
        if (filteredTables != null && !filteredTables.isEmpty()) {
            filteredTables.add(event.getNewTable());
        }
    }
}
