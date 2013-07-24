/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.ui.widget;

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

import com.wcs.netbeans.liquiface.facade.SceneFacade;
import com.wcs.netbeans.liquiface.model.Column;
import com.wcs.netbeans.liquiface.model.ForeignKeyConstraint;
import com.wcs.netbeans.liquiface.model.Table;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.graph.GraphPinScene;
import org.netbeans.api.visual.graph.layout.GridGraphLayout;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.layout.SceneLayout;
import org.netbeans.api.visual.router.Router;
import org.netbeans.api.visual.router.RouterFactory;
import org.netbeans.api.visual.vmd.VMDColorScheme;
import org.netbeans.api.visual.vmd.VMDConnectionWidget;
import org.netbeans.api.visual.vmd.VMDFactory;
import org.netbeans.api.visual.vmd.VMDNodeWidget;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.EventProcessingType;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.ImageUtilities;

/**
 *
 * @author athalay
 */
public class LiquifaceGraphScene extends GraphPinScene<Integer, String, Integer>{
    public static final String PIN_ID_DEFAULT_SUFFIX = "#default"; // NOI18N

    private final Image PRIMARY_KEY_ICON = ImageUtilities.loadImage("icons/column/primary.gif");
    private final Image FOREIGN_KEY_ICON = ImageUtilities.loadImage("icons/column/foreign.png");
    private final Image NOT_NULL_ICON = ImageUtilities.loadImage("icons/column/star.png");

    private LayerWidget backgroundLayer = new LayerWidget (this);
    private LayerWidget mainLayer = new LayerWidget (this);
    private LayerWidget connectionLayer = new LayerWidget (this);
    private LayerWidget upperLayer = new LayerWidget (this);

    private Router router;
    private Router directRouter;
    private Router orthogonalRouter;

    private WidgetAction moveControlPointAction = ActionFactory.createOrthogonalMoveControlPointAction ();
    private WidgetAction moveAction = ActionFactory.createMoveAction ();
    
    private SceneLayout sceneLayout;
    private VMDColorScheme scheme;

    private Map<Integer, TableWidget> widgets = new HashMap<Integer, TableWidget>();

    public LiquifaceGraphScene() {
        
        this (VMDFactory.getOriginalScheme());
    }

    /**
     * Creates a VMD graph scene with a specific color scheme.
     * @param scheme the color scheme
     */
    public LiquifaceGraphScene (VMDColorScheme scheme) {
        this.scheme = scheme;
        setKeyEventProcessingType (EventProcessingType.FOCUSED_WIDGET_AND_ITS_PARENTS);

        addChild (backgroundLayer);
        addChild (mainLayer);
        addChild (connectionLayer);
        addChild (upperLayer);
        orthogonalRouter = RouterFactory.createOrthogonalSearchRouter (mainLayer, connectionLayer);
        directRouter = RouterFactory.createDirectRouter ();

        router = orthogonalRouter;

        getActions ().addAction (ActionFactory.createZoomAction ());
        getActions ().addAction (ActionFactory.createPanAction ());
        getActions ().addAction (ActionFactory.createWheelPanAction ());
        getActions ().addAction (ActionFactory.createRectangularSelectAction (this, backgroundLayer));

        sceneLayout = LayoutFactory.createSceneGraphLayout (this, new GridGraphLayout<Integer, String> ().setChecker (true));
    }

    public boolean containsTable(Integer tableId){
        return widgets.containsKey(tableId);
    }

    public void refreshTables(List<Table> tables){
        removeNonExistentTableNodes(tables);
        for (Table table : tables) {
            if(widgets.containsKey(table.getId())){
                refreshTable(table);
            } else {
                addTable(table, new Point(50, 50));
            }
        }
    }

    private void removeNonExistentTableNodes(List<Table> tables){
        List<Integer> removedWidgets = new ArrayList();
        for (Integer tableId : widgets.keySet()) {
            if(isTableMissingFromList(tables, tableId)){
                TableWidget tableToRemove = widgets.get(tableId);
                mainLayer.removeChild(tableToRemove);
                removedWidgets.add(tableId);
            }
        }
        for (Integer tableId : removedWidgets) {
            widgets.remove(tableId);
            removeNode(tableId);
        }
    }

    public boolean isTableMissingFromList(List<Table> tables, Integer tableId){
        for (Table table : tables) {
            if(table.getId() == tableId.intValue()){
                return false;
            }
        }
        return true;
    }

    public void refreshTable(Table table){
        TableWidget tableWidget = widgets.get(table.getId());
        tableWidget.setNodeName(table.getName());

        List<Integer> columnWidgetIds = tableWidget.getColumnWidgetsIds();

        for (Column column : table.getColumns()) {
            if(columnWidgetIds.contains(column.getId())){
                ColumnWidget columnWidget = (ColumnWidget)findWidget(column.getId());
                columnWidget.setProperties(PIN_ID_DEFAULT_SUFFIX, null);
                columnWidget.refresh(column, getGlyps(column, table));
            } else {
                addColumnWidget(column, table);
            }
            validate();
        }

        for (Integer columnWidgetId : columnWidgetIds) {
            if(table.getColumnById(columnWidgetId) == null){
                removePinWithEdges(columnWidgetId);
            }
        }
        validate();
    }

    public void addTable(Table table, Point position){
        TableWidget tableWidget = (TableWidget) addNode(table.getId());
        tableWidget.setTableId(table.getId());
        tableWidget.setPreferredLocation (position);
        tableWidget.setNodeProperties (null, table.getName(), null, null);

        for (Column column : table.getColumns()) {
            addColumnWidget(column, table);
        }

        WidgetAction popupAction = ActionFactory.createPopupMenuAction(SceneFacade.getInstance());
        tableWidget.getActions().addAction(popupAction);
        validate();
    }

    private void addColumnWidget(Column column, Table table){
        ColumnWidget columnWidget = (ColumnWidget)addPin (table.getId(), column.getId());
        WidgetAction popupAction = ActionFactory.createPopupMenuAction(SceneFacade.getInstance());
        columnWidget.getActions().addAction(popupAction);
        columnWidget.refresh(column, getGlyps(column, table));
        validate();
    }

    private List<Image> getGlyps(Column column, Table table){
        List<Image> glyps = new ArrayList<Image>();
        if(column.isPrimaryKey()){
            glyps.add(PRIMARY_KEY_ICON);
        }
        if(table.isForeignKey(column)){
            glyps.add(FOREIGN_KEY_ICON);
        }
        if(!column.isNullable()){
            glyps.add(NOT_NULL_ICON);
        }
        return glyps;
    }

    public void refreshColumn(Column column, Table table){
        ColumnWidget columnWidget = (ColumnWidget)findWidget(column.getId());
        columnWidget.refresh(column, getGlyps(column, table));
        validate();
    }

    public void refreshForeignKeys(List<Table> tables){
        clearForeignKeys();

        for (Table table : tables) {
            for (ForeignKeyConstraint foreignKeyConstraint : table.getForeignKeyConstraints()) {
                if(tables.contains(foreignKeyConstraint.getReferencedTable())){
                    createEdge(foreignKeyConstraint);
                }
                validate();
            }
        }
    }

    public void clearForeignKeys(){
        List<String> originalEdges = new ArrayList<String>(getEdges());
        List<String> edges =  new ArrayList<String>(originalEdges);
        Collections.copy(edges, originalEdges);

        for (String edge : edges) {
           removeEdge(edge);
        }
    }

    private void createEdge(ForeignKeyConstraint foreignKeyConstraint){
        String edgeID = foreignKeyConstraint.getName();
        Integer refPinId = foreignKeyConstraint.getReferencedColumns().get(0).getId();
        Integer pinId = foreignKeyConstraint.getBaseColumns().get(0).getId();

        addEdge(edgeID);
        setEdgeSource (edgeID, pinId);
        setEdgeTarget (edgeID, refPinId);
    }

    /**
     * Implements attaching a widget to a node. The widget is VMDNodeWidget and has object-hover, select, popup-menu and move actions.
     * @param node the node
     * @return the widget attached to the node
     */
    @Override
    protected Widget attachNodeWidget (Integer node) {
        TableWidget widget = new TableWidget(this, scheme);
        mainLayer.addChild (widget);

        widget.getHeader ().getActions ().addAction (createObjectHoverAction ());
        widget.getActions ().addAction (createSelectAction ());
        widget.getActions ().addAction (moveAction);

        widgets.put(node, widget);

        return widget;
    }

    public void removeNodeWidget(Integer node){
        mainLayer.removeChild(widgets.get(node));
    }

    /**
     * Implements attaching a widget to a pin. The widget is VMDPinWidget and has object-hover and select action.
     * The the node id ends with "#default" then the pin is the default pin of a node and therefore it is non-visual.
     * @param node the node
     * @param pin the pin
     * @return the widget attached to the pin, null, if it is a default pin
     */
    @Override
    protected Widget attachPinWidget (Integer node, Integer pin) {

        ColumnWidget widget = new ColumnWidget (this, scheme, pin);
        ((TableWidget) findWidget (node)).attachPinWidget (widget);
        widget.getActions ().addAction (createObjectHoverAction ());
        widget.getActions ().addAction (createSelectAction ());

        return widget;
    }

    /**
     * Implements attaching a widget to an edge. the widget is ConnectionWidget and has object-hover, select and move-control-point actions.
     * @param edge the edge
     * @return the widget attached to the edge
     */
    @Override
    protected Widget attachEdgeWidget (String edge) {
        VMDConnectionWidget connectionWidget = new VMDConnectionWidget (this, scheme);
        connectionWidget.setRouter (router);
        connectionLayer.addChild (connectionWidget);

        connectionWidget.getActions ().addAction (createObjectHoverAction ());
        connectionWidget.getActions ().addAction (createSelectAction ());
        connectionWidget.getActions ().addAction (moveControlPointAction);

        return connectionWidget;
    }

    /**
     * Attaches an anchor of a source pin an edge.
     * The anchor is a ProxyAnchor that switches between the anchor attached to the pin widget directly and
     * the anchor attached to the pin node widget based on the minimize-state of the node.
     * @param edge the edge
     * @param oldSourcePin the old source pin
     * @param sourcePin the new source pin
     */
    @Override
    protected void attachEdgeSourceAnchor (String edge, Integer oldSourcePin, Integer sourcePin) {
        ((ConnectionWidget) findWidget (edge)).setSourceAnchor (getPinAnchor (sourcePin));
    }

    /**
     * Attaches an anchor of a target pin an edge.
     * The anchor is a ProxyAnchor that switches between the anchor attached to the pin widget directly and
     * the anchor attached to the pin node widget based on the minimize-state of the node.
     * @param edge the edge
     * @param oldTargetPin the old target pin
     * @param targetPin the new target pin
     */
    @Override
    protected void attachEdgeTargetAnchor (String edge, Integer oldTargetPin, Integer targetPin) {
        ((ConnectionWidget) findWidget (edge)).setTargetAnchor (getPinAnchor (targetPin));
    }

    private Anchor getPinAnchor (Integer pin) {
        if (pin == null)
            return null;
        VMDNodeWidget nodeWidget = (VMDNodeWidget) findWidget (getPinNode (pin));
        Widget pinMainWidget = findWidget (pin);
        Anchor anchor;
        if (pinMainWidget != null) {
            anchor = AnchorFactory.createDirectionalAnchor (pinMainWidget, AnchorFactory.DirectionalAnchorKind.HORIZONTAL, 8);
            anchor = nodeWidget.createAnchorPin (anchor);
        } else
            anchor = nodeWidget.getNodeAnchor ();
        return anchor;
    }

    /**
     * Invokes layout of the scene.
     */
    public void layoutScene () {
        sceneLayout.invokeLayout ();
    }

    public void setDirectRouter() {
        this.router = directRouter;
    }

    public void setOrthogonalRouter() {
        this.router = orthogonalRouter;
    }

    public boolean isDirectRouterUsed() {
        return router.equals(directRouter);
    }

}
