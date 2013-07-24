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

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.db.explorer.ConnectionManager;
import org.netbeans.api.db.explorer.DatabaseConnection;
import org.netbeans.api.db.explorer.DatabaseException;

/**
 *
 * @author tveki
 */
public class DatabaseConnectionFactory {

    private static final Logger logger = Logger.getLogger(DatabaseChangeLoader.class.getName());
    
    private static DatabaseConnectionFactory instance;
    
    private DatabaseConnectionFactory(){        
    }
    
    public static DatabaseConnectionFactory getInstance(){
        if (instance == null){
            instance = new DatabaseConnectionFactory();
        }
        return instance;
    }

    public Connection createConnection(DatabaseConnection databaseConnection) { 
        if (databaseConnection == null){            
            throw new IllegalArgumentException("The databaseConnection must be provided");
        }
        try {
            ConnectionManager.getDefault().connect(databaseConnection);
            logger.log(Level.INFO, "connected: {0}", databaseConnection.getName());
            return databaseConnection.getJDBCConnection();
        } catch (DatabaseException ex) {
            logger.log(Level.SEVERE, "Unable to connect: ", ex);
        }
        return null;
    }

    public DatabaseConnection[] getNetbeansConnections(){
        ConnectionManager m = ConnectionManager.getDefault();
        return m.getConnections();
    }
    
    public void disconnect(DatabaseConnection databaseConnection){
        if (databaseConnection != null){
            ConnectionManager.getDefault().disconnect(databaseConnection);
            logger.log(Level.INFO, "disconnected: {0}", databaseConnection.getName());
        }
    }
    
}
