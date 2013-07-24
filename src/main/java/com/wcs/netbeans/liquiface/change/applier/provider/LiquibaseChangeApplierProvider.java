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

import com.wcs.netbeans.liquiface.change.applier.ApplyChange;
import com.wcs.netbeans.liquiface.change.applier.ChangeApplier;
import com.wcs.netbeans.liquiface.util.ClassUtil;
import java.util.HashMap;
import java.util.Map;
import liquibase.change.Change;

/**
 *
 * @author tveki
 */
public class LiquibaseChangeApplierProvider implements ChangeApplierProvider {
    
    private Map<Class<? extends Change>, ChangeApplier> applierMap;
    
    public LiquibaseChangeApplierProvider() {
        applierMap = new HashMap<Class<? extends Change>, ChangeApplier>();    
    }    
    
    @Override
    public ChangeApplier getApplier(Change change) throws Exception {
        return getApplier(change.getClass());
    }    
    
    ChangeApplier getApplier(Class<? extends Change> changeClass) throws Exception{
        ChangeApplier applier = applierMap.get(changeClass);
        if (applier == null){
            applier = lookupApplier(changeClass);
            if (applier != null){
                applierMap.put(changeClass, applier);
            }
        }
        return applier;
    }    

    private ChangeApplier lookupApplier(Class<? extends Change> changeClass) throws Exception{
        Class<? extends ChangeApplier> applierClass = null;
        for (Class klass : ClassUtil.getAllClassesRecursivelyFromPackageOf(ChangeApplier.class)){            
            if (isApplierMatchForChange(klass, changeClass)){
                applierClass = klass;
                break;
            }
        }
        
        if (applierClass == null){
            return null;
        }
        
        return applierClass.newInstance();
    }    
    
    private boolean isApplierMatchForChange(Class klass, Class<? extends Change> changeClass){
        if (!ChangeApplier.class.isAssignableFrom(klass)){
            return false;
        }
        
        if (!klass.isAnnotationPresent(ApplyChange.class)){
            return false;
        }
        
        ApplyChange annotation = (ApplyChange) klass.getAnnotation(ApplyChange.class);   
        
        if (annotation.value() == null){
            return false;
        }
        
        return changeClass.equals(annotation.value());
    }

}
