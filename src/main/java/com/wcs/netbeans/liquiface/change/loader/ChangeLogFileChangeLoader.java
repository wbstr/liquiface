/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.change.loader;

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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.parser.core.xml.XMLChangeLogSAXParser;
import liquibase.resource.FileSystemResourceAccessor;

/**
 *
 * @author tveki
 */
public class ChangeLogFileChangeLoader implements ChangeLoader {
    
    private static final Logger logger = Logger.getLogger(ChangeLogFileChangeLoader.class.getName());

    private File changeLogFile;
    
    private DatabaseChangeLog changeLog;    

    public ChangeLogFileChangeLoader(File changeLogFile) {
        this.changeLogFile = changeLogFile;
    }
    
    @Override
    public List<ChangeSet> loadChangeSets() {
        return getChangeLog().getChangeSets();
    }
    
    private DatabaseChangeLog getChangeLog() {
        if (changeLog == null) {
            try {
                ChangeLogParameters params = new ChangeLogParameters();
                changeLog = new XMLChangeLogSAXParser().parse(getChangeLogFile().getAbsolutePath(), params, new FileSystemResourceAccessor());
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        return changeLog;
    }

    private File getChangeLogFile() {
        return changeLogFile;
    }    
    
}
