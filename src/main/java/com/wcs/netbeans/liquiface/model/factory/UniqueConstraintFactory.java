/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.model.factory;

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

import com.wcs.netbeans.liquiface.model.Column;
import com.wcs.netbeans.liquiface.model.Table;
import com.wcs.netbeans.liquiface.model.UniqueConstraint;

/**
 *
 * @author tveki
 */
public class UniqueConstraintFactory {
    
    public static UniqueConstraint createUniqueConstraint(Table table, Column column){        
        UniqueConstraint constraint = new UniqueConstraint();
        constraint.addColumn(column);
        constraint.setName("UNIQUE_" + table.getName() + "_" + column.getName());
        return constraint;
    }
    
}
