/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.util;

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

import java.io.File;

/**
 *
 * @author tveki
 */
public class TempChangelogFile {
    
    private File tempFile;

    public File get() throws Exception {
        if (tempFile == null) {
            tempFile = File.createTempFile("changelog", ".xml");
            tempFile.deleteOnExit();
        }
        return tempFile;
    }
    
    public void invalidate(){
        if (tempFile == null){
            return;
        }
        if (tempFile.exists()){
            tempFile.delete();
        }
        tempFile = null;
    }
    
}
