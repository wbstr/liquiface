/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.database;

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

import com.wcs.netbeans.liquiface.change.loader.ChangeLoader;
import com.wcs.netbeans.liquiface.util.Utils;
import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.database.Database;
import liquibase.diff.Diff;
import liquibase.diff.DiffResult;
import liquibase.parser.core.xml.XMLChangeLogSAXParser;
import liquibase.resource.FileSystemResourceAccessor;
import org.netbeans.api.db.explorer.DatabaseConnection;

/**
 *
 * @author tveki
 */
public class DatabaseChangeLoader extends DatabaseHandler implements ChangeLoader {

    private static final Logger logger = Logger.getLogger(DatabaseChangeLoader.class.getName());
    
    private static final String INPUT_CHANGELOG = "inputChangelog.xml";
  
    private DatabaseChangeLog changeLog;
    private DatabaseConnection databaseConnection;

    public DatabaseChangeLoader(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    private String getChangeLogFile() {
        return Utils.getUserHome() + File.separator + INPUT_CHANGELOG;
    }

    private void generateChangeLog() {
        Connection conn = null;
        try {
            conn = createConnection(databaseConnection);
            if (conn == null) {
                return;
            }
            Database database = createDatabase(conn);
            String defaultSchema = null;
            Diff diff = new Diff(database, defaultSchema);
            diff.setDiffTypes(null);
            DiffResult diffResult = diff.compare();
            diffResult.setChangeSetAuthor("liquface");
            diffResult.printChangeLog(getChangeLogFile(), database);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            disconnect(databaseConnection);
        }
    }

    private DatabaseChangeLog getChangeLog() {
        if (changeLog == null) {
            try {
                ChangeLogParameters params = new ChangeLogParameters();
                changeLog = new XMLChangeLogSAXParser().parse(getChangeLogFile(), params, new FileSystemResourceAccessor());
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        return changeLog;
    }

    private void regenerateChangeLog() {
        File file = new File(getChangeLogFile());
        if (file.exists()) {            
            file.delete();
            logger.log(Level.INFO, "{0} is deleted", getChangeLogFile());
        }

        generateChangeLog();

        logger.log(Level.INFO, "{0} is generated", getChangeLogFile());
    }

    @Override
    public List<ChangeSet> loadChangeSets() {
        regenerateChangeLog();
        List<ChangeSet> changeSets = getChangeLog().getChangeSets();
        logger.log(Level.INFO, "{0} change sets loaded", changeSets.size());
        return changeSets;
    }

}
