/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.logic;

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

import com.wcs.netbeans.liquiface.model.ForeignKeyConstraint;
import com.wcs.netbeans.liquiface.model.Table;
import java.util.*;

/**
 *
 * @author athalay
 */
public class TableNeighborFilter {
    List<Table> allTables;
    Table root;
    int maxDistance;
    TableNeighborFilterType type;
    
    Map<Table, Integer> distanceMap = new HashMap<Table, Integer>();

    public TableNeighborFilter(List<Table> allTables,Table centerTable, int distance, TableNeighborFilterType type) {
        this.allTables = allTables;
        this.root = centerTable;
        this.maxDistance = distance;
        this.type = type;
        filterTables();
    }
    
    private void filterTables(){
        initDistanceMap();
        for (int i = 0; i < maxDistance; i++) {
            handleNeighbors(i);
        }
    }
    
    private void initDistanceMap(){
        distanceMap.put(root, 0);
    }
    
    private void handleNeighbors(int level){
        List<Table> parents = new ArrayList<Table>();
        for (Map.Entry<Table, Integer> entry : distanceMap.entrySet()) {
            Table table = entry.getKey();
            int distance = entry.getValue().intValue();
            if(level==distance){
                parents.add(table);
            }
        }
        
        List<Table> tables = new ArrayList<Table>();
        if (TableNeighborFilterType.OUTGOING.equals(type)
                || TableNeighborFilterType.BOTH.equals(type)) {
            tables.addAll(getOutgoingForeignKeys(parents));
        }
        if (TableNeighborFilterType.INCOMING.equals(type)
                || TableNeighborFilterType.BOTH.equals(type)) {
            tables.addAll(getIncomingForeignKeys());
        }
        
        for (Table table : tables) {
            if(!distanceMap.containsKey(table)){
                distanceMap.put(table, level+1); 
            }
        }
    }
    
    private List<Table> getOutgoingForeignKeys(List<Table> parents){
        List<Table> results = new ArrayList<Table>();
        for (Table parent : parents) {
            for (ForeignKeyConstraint fk : parent.getForeignKeyConstraints()) {
                results.add(fk.getReferencedTable());
            }
        }
        return results;
    }
    
    private List<Table> getIncomingForeignKeys(){
        List<Table> results = new ArrayList<Table>();
        for (Table table : allTables) {
            for (ForeignKeyConstraint fk : table.getForeignKeyConstraints()) {
                if(distanceMap.containsKey(fk.getReferencedTable())){
                    results.add(table);
                }
            }
        }
        return results;
    }
    
    public List<Table> getFilteredTables(){
        List<Table> tables = new ArrayList<Table>();
        tables.addAll(distanceMap.keySet());
        return tables;
    }
    
    public enum TableNeighborFilterType {
        OUTGOING,
        INCOMING,
        BOTH;
    }
}
