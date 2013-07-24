/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.facade;

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

import com.google.common.eventbus.Subscribe;
import com.wcs.netbeans.liquiface.change.applier.ChangeApplier;
import com.wcs.netbeans.liquiface.change.applier.provider.ChangeApplierProvider;
import com.wcs.netbeans.liquiface.change.applier.provider.LiquibaseChangeApplierProvider;
import com.wcs.netbeans.liquiface.change.loader.ChangeLoader;
import com.wcs.netbeans.liquiface.change.loader.ChangeLogFileChangeLoader;
import com.wcs.netbeans.liquiface.change.store.ChangeSetMode;
import com.wcs.netbeans.liquiface.change.store.ChangeStore;
import com.wcs.netbeans.liquiface.change.store.SimpleChangeStore;
import com.wcs.netbeans.liquiface.database.DatabaseChangeLoader;
import com.wcs.netbeans.liquiface.eventbus.LiquifaceEventBus;
import com.wcs.netbeans.liquiface.eventbus.event.LiquibaseChangeEvent;
import com.wcs.netbeans.liquiface.eventbus.event.ReloadModelFromChangeLogFileEvent;
import com.wcs.netbeans.liquiface.eventbus.event.ReloadModelFromDatabaseEvent;
import com.wcs.netbeans.liquiface.eventbus.event.ResetModelClearChangesEvent;
import com.wcs.netbeans.liquiface.model.Column;
import com.wcs.netbeans.liquiface.model.ForeignKeyConstraint;
import com.wcs.netbeans.liquiface.model.Model;
import com.wcs.netbeans.liquiface.model.Table;
import com.wcs.netbeans.liquiface.util.ChangeSetWriter;
import java.io.File;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import liquibase.change.Change;
import liquibase.changelog.ChangeSet;

/**
 *
 * @author athalay
 */
public class ModelFacade {

    private static final Logger logger = Logger.getLogger(ModelFacade.class.getName());
    private Model model;
    private ChangeStore changeStore;
    private ChangeApplierProvider applierProvider;

    public void addTable(Table table) {
        model.addTable(table);
    }

    public Table getTableById(int id) {
        return model.getTableById(id);
    }

    public Table getTableByName(String name) {
        return model.getTableByName(name);
    }

    public List<ForeignKeyConstraint> getReferencingForeignKeys(String tableName, List<Column> columns) {
        return model.getReferencingForeignKeys(tableName, columns);
    }

    private boolean applyToModel(Change change) throws Exception{
        ChangeApplier applier = applierProvider.getApplier(change);
        if (applier != null) {
            applier.apply(model, change);
        } else {
            logger.log(Level.WARNING, "No applier found for this change: {0}", change.getClass().getName());
            return false;
        }
        return true;
    }

    @Subscribe
    public void apply(LiquibaseChangeEvent event) throws Exception{
        Change change = event.getChange();
        if (applyToModel(change)) {
            changeStore.addChange(change);
        }
    }

    public void clearChanges() {
        changeStore.clear();
    }

    @Subscribe
    public void reloadModelFromDatabase(ReloadModelFromDatabaseEvent event) throws Exception{
        resetModel();
        ChangeLoader changeLoader = new DatabaseChangeLoader(event.getDatabaseConnection());
        initModel(changeLoader);
    }

    @Subscribe
    public void reloadModelFromChangeLogFile(ReloadModelFromChangeLogFileEvent event) throws Exception{
        resetModel();
        ChangeLoader changeLoader = new ChangeLogFileChangeLoader(event.getChangeLogFile());
        initModel(changeLoader);
    }

    private void initModel(ChangeLoader changeLoader) throws Exception{
        for (ChangeSet changeSet : changeLoader.loadChangeSets()) {
            for (Change change : changeSet.getChanges()) {
                applyToModel(change);
            }
        }
    }

    private void resetModel() {
        model = new Model();
    }

    @Subscribe
    public final void resetModelAndClearChanges(ResetModelClearChangesEvent event) {
        resetModel();
        clearChanges();
    }

    public int getNumberOfTables() {
        return model.getNumberOfTables();
    }

    public List<Table> getTables() {
        return model.getTables();
    }

    public String writeChangeSetsToString(ChangeSetMode changeSetMode) {
        return ChangeSetWriter.writeToString(changeStore.getChangeSets(changeSetMode));
    }

    public void writeChangeSetsToStream(OutputStream out, ChangeSetMode changeSetMode) {
        ChangeSetWriter.writeToStream(changeStore.getChangeSets(changeSetMode), out);
    }

    public void writeChangeSetsToFile(File changelogFile, ChangeSetMode changeSetMode) {
        ChangeSetWriter.writeToFile(changeStore.getChangeSets(changeSetMode), changelogFile);
    }

    //<editor-fold defaultstate="collapsed" desc="singleton stuff">
    private static ModelFacade instance;

    public static ModelFacade getInstance() {
        if (instance == null) {
            instance = new ModelFacade();
            LiquifaceEventBus.getInstance().register(instance);
        }
        return instance;
    }

    private ModelFacade() {
        changeStore = new SimpleChangeStore();
        //applierProvider = new SimpleChangeApplierProvider();
        applierProvider = new LiquibaseChangeApplierProvider();
        resetModel();
    }
    
    //</editor-fold>
}
