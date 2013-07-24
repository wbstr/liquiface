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

import com.wcs.netbeans.liquiface.change.store.ChangeSetMode;
import com.wcs.netbeans.liquiface.facade.ModelFacade;
import com.wcs.netbeans.liquiface.util.TempChangelogFile;
import java.sql.Connection;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.resource.FileSystemResourceAccessor;
import org.netbeans.api.db.explorer.DatabaseConnection;

/**
 *
 * @author tveki
 */
public class DatabaseUpdater extends DatabaseHandler {    

    private TempChangelogFile tempChangelog = new TempChangelogFile();

    public void updateDatabase(final DatabaseConnection databaseConnection) throws Exception{        
        final Future<Object> future = Executors.newFixedThreadPool(1).submit(new Callable() {
            @Override
            public Object call() throws Exception {                
                updateDb(databaseConnection);
                return null;
            }
        });

        future.get();
    }

    private void updateDb(DatabaseConnection databaseConnection) throws Exception{        
        ModelFacade modelFacade = ModelFacade.getInstance();
        modelFacade.writeChangeSetsToFile(tempChangelog.get(), ChangeSetMode.SEPARATE_FOR_EACH);
        Connection conn = null;
        try {
            conn = createConnection(databaseConnection);
            Database database = createDatabase(conn);
            Liquibase liquibase = new Liquibase(tempChangelog.get().getCanonicalPath(), new FileSystemResourceAccessor(), database);            
            liquibase.update(null);
            tempChangelog.invalidate();
        } finally {
            disconnect(databaseConnection);
        }
    }
    
}
