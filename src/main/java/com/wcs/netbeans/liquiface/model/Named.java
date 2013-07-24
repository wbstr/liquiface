/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.model;

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

/**
 *
 * @author tveki
 */
public class Named {
    
    private String name;

    public Named(String name) {
        this.name = name;
    }

    public Named() {        
    }    
    
    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        
        if (!(obj instanceof Named)){
            return false;
        }
        
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        
        Named named = (Named) obj;
        
        if (getName() == null){
            return named.getName() == null;
        }
        
        return getName().equals(named.getName());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (getName() != null ? getName().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "name=" + getName() + '}';
    }        
    
}
