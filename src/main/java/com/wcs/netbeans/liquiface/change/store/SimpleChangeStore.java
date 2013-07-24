/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.change.store;

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
import java.util.Arrays;
import java.util.List;
import liquibase.change.Change;
import liquibase.changelog.ChangeSet;

/**
 *
 * @author tveki
 */
public class SimpleChangeStore implements ChangeStore {

    private List<ChangeStoreItem> changes;

    public SimpleChangeStore() {
        changes = new ArrayList<ChangeStoreItem>();
    }

    @Override
    public void addChange(Change change) {        
        changes.add(new SimpleChangeStoreItem(change, createChangeId()));
    }
    
    private ChangeSet createChangeSet(ChangeStoreItem item){        
        String author = System.getProperty("user.name");
        String id = null;
        if (item == null){
            id = createChangeId();
        }
        else {
            id = item.getId();
        }
        return new ChangeSet(id, author, false, false, null, null, null);
    }

    @Override
    public List<ChangeSet> getChangeSets(ChangeSetMode changeSetMode) {       
        if (changeSetMode == null){
            throw new IllegalArgumentException("Missing changeset mode!");
        }
        switch (changeSetMode){
            case SINGLE_FOR_ALL:
                return Arrays.asList(getSingleChangeSetForAllChanges());
            case SEPARATE_FOR_EACH:
                return getSeparateChangeSetsForEachChange();
        }
        return null;
    }    
    
    private ChangeSet getSingleChangeSetForAllChanges() {        
        ChangeSet changeSet = createChangeSet(null);
        for (ChangeStoreItem item : changes) {
            changeSet.addChange(item.getChange());
        }
        return changeSet;
    }

    private List<ChangeSet> getSeparateChangeSetsForEachChange() {       
        List<ChangeSet> changeSets = new ArrayList<ChangeSet>();        
        for (ChangeStoreItem item : changes) {
            ChangeSet changeSet = createChangeSet(item);
            changeSet.addChange(item.getChange());
            changeSets.add(changeSet);
        }
        return changeSets;
    }
    
    private String createChangeId(){
        return "CHANGE-" + System.currentTimeMillis();
    }

    @Override
    public void clear() {
        changes.clear();
    }
    
}
