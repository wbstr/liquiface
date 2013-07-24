/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.change.applier.provider;

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

import com.wcs.netbeans.liquiface.change.applier.*;
import java.util.HashMap;
import java.util.Map;
import liquibase.change.Change;

/**
 *
 * @author tveki
 */
public class SimpleChangeApplierProvider implements ChangeApplierProvider {
    
    private Map<Class<? extends Change>, ChangeApplier> applierMap;

    public SimpleChangeApplierProvider() {
        applierMap = new HashMap<Class<? extends Change>, ChangeApplier>();
        initMap();
    }
    
    private void add(ChangeApplier applier){
        applierMap.put(getChangeClass(applier.getClass()), applier);
    }
    
    private void initMap(){
        add(new AddColumnChangeApplier());
        add(new AddForeignKeyConstraintChangeApplier());
        add(new AddNotNullConstraintChangeApplier());
        add(new AddPrimaryKeyChangeApplier());
        add(new AddUniqueConstraintChangeApplier());
        add(new CreateTableChangeApplier());
        add(new DropColumnChangeApplier());
        add(new DropForeignKeyConstraintChangeApplier());
        add(new DropNotNullConstraintChangeApplier());
        add(new DropPrimaryKeyChangeApplier());
        add(new DropTableChangeApplier());
        add(new DropUniqueConstraintChangeApplier());
        add(new ModifyDataTypeChangeApplier());
        add(new RenameColumnChangeApplier());
        add(new RenameTableChangeApplier());        
        //TODO ide a tobbit is 
    }    
    
    @Override
    public ChangeApplier getApplier(Change change){
        return applierMap.get(change.getClass());
    }
    
    private Class<? extends Change> getChangeClass(Class<? extends ChangeApplier> applierClass){        
        ApplyChange annotation = (ApplyChange) applierClass.getAnnotation(ApplyChange.class);       
        return annotation.value();
    }    
    
}
