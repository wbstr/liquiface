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
import com.wcs.netbeans.liquiface.eventbus.LiquifaceEventBus;
import com.wcs.netbeans.liquiface.eventbus.event.RedrawColumnSceneEvent;
import com.wcs.netbeans.liquiface.eventbus.event.RedrawGlobalSceneEvent;
import com.wcs.netbeans.liquiface.eventbus.event.RedrawTableSceneEvent;
import com.wcs.netbeans.liquiface.model.Column;
import com.wcs.netbeans.liquiface.model.Table;
import com.wcs.netbeans.liquiface.ui.layout.RectangularGraphLayout;
import com.wcs.netbeans.liquiface.ui.swing.popup.CanvasPopupMenu;
import com.wcs.netbeans.liquiface.ui.swing.popup.ColumnPopupMenu;
import com.wcs.netbeans.liquiface.ui.swing.popup.TablePopupMenu;
import com.wcs.netbeans.liquiface.ui.widget.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JPopupMenu;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.graph.layout.GraphLayout;
import org.netbeans.api.visual.graph.layout.GridGraphLayout;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.layout.SceneLayout;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author athalay
 */
public class SceneFacade implements PopupMenuProvider {

    //<editor-fold defaultstate="collapsed" desc="singleton stuff">
    private static SceneFacade instance;

    public static SceneFacade getInstance() {
        if (instance == null) {
            instance = new SceneFacade();
            LiquifaceEventBus.getInstance().register(instance);
        }
        return instance;
    }

    private SceneFacade() {
    }
    //</editor-fold>

    private static final int GAP_X = 50;
    private static final int GAP_Y = 50;

    private LiquifaceGraphScene scene;
    private SceneLayout layout;
    GraphLayout<Integer, String> graphLayout;

    public void init(LiquifaceGraphScene scene) {
        this.scene = scene;
        WidgetAction popupAction = ActionFactory.createPopupMenuAction(SceneFacade.getInstance());
        scene.getActions().addAction(popupAction);

        graphLayout = new RectangularGraphLayout<Integer, String>(GAP_X, GAP_Y);
        graphLayout.setAnimated(false);
        layout = LayoutFactory.createSceneGraphLayout(scene, graphLayout);
        reDraw();
    }

    public boolean isAnimated() {
        return graphLayout.isAnimated();
    }

    public void setAnimated(boolean on) {
        graphLayout.setAnimated(on);
    }

    public void setRectangularLayout(boolean rectangular) {
        boolean animated = graphLayout.isAnimated();
        if (rectangular) {
            if (graphLayout instanceof RectangularGraphLayout) {
                return;
            } else {
                graphLayout = new RectangularGraphLayout<Integer, String>(GAP_X, GAP_Y);
                graphLayout.setAnimated(animated);
                layout = LayoutFactory.createSceneGraphLayout(scene, graphLayout);
            }
        } else {
            if (graphLayout instanceof GridGraphLayout) {
                return;
            } else {
                GridGraphLayout<Integer, String> gridGraphLayout = new GridGraphLayout<Integer, String>();
                gridGraphLayout.setGaps(GAP_X, GAP_Y);
                graphLayout = gridGraphLayout;
                graphLayout.setAnimated(animated);
                layout = LayoutFactory.createSceneGraphLayout(scene, graphLayout);
            }
        }

        reDraw();
    }

    @Subscribe
    public void reDraw(RedrawGlobalSceneEvent event){
        reDraw();
    }

    @Subscribe
    public void reDraw(RedrawTableSceneEvent event){
        Table table = ModelFacade.getInstance().getTableById(event.getTableId());
        scene.refreshTable(table);
    }

    @Subscribe
    public void reDraw(RedrawColumnSceneEvent event){
        Table table = ModelFacade.getInstance().getTableById(event.getTableId());
        Column column = table.getColumnById(event.getColumnId());
        scene.refreshColumn(column, table);
    }

    private void reDraw(){
        reDrawTables();
        reDrawForeignKeys();
        scene.validate();
        scene.repaint();
        layout.invokeLayout();
        scene.validate();
    }

    private void reDrawTables() {
        scene.refreshTables(getTableList());
    }

    private void reDrawForeignKeys() {
        if(FilterFacade.getInstance().isForeignKeysVisible()){
            scene.refreshForeignKeys(getTableList());
        } else {
            scene.clearForeignKeys();
        }
    }

    private List<Table> getTableList() {
        List<Table> originalTables = FilterFacade.getInstance().getFilteredTables();
        if (originalTables.isEmpty()) {
            originalTables = ModelFacade.getInstance().getTables();
        }
        List<Table> tables = new ArrayList<Table>(originalTables);
        Collections.copy(tables, originalTables);
        return tables;
    }

    @Override
    public JPopupMenu getPopupMenu(Widget widget, Point localLocation) {
        if(widget instanceof ColumnWidget){
            ColumnWidget columnWidget = (ColumnWidget) widget;
            TableWidget tableWidget = (TableWidget)columnWidget.getParentWidget();
            return new ColumnPopupMenu(tableWidget.getTableId(), columnWidget.getColumnId());
        }
        if (widget instanceof LiquifaceGraphScene) {
            return new CanvasPopupMenu();
        }
        if (widget instanceof TableWidget) {
            return  new TablePopupMenu(((TableWidget)widget).getTableId());
        }
        return null;
    }

    public void setDirectRouter() {
        scene.setDirectRouter();
    }

    public void setOrthogonalRouter() {
        scene.setOrthogonalRouter();
    }

    public boolean isDirectRouterUsed() {
        return scene.isDirectRouterUsed();
    }
}
