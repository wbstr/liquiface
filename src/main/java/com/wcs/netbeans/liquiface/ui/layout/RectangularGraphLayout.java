/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.ui.layout;

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

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collection;
import org.netbeans.api.visual.graph.layout.GraphLayout;
import org.netbeans.api.visual.graph.layout.UniversalGraph;
import org.netbeans.api.visual.model.ObjectScene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author athalay
 */
public class RectangularGraphLayout<N,E> extends GraphLayout<N, E> {
    
    private final static int SCENE_WIDTH = 1200;
    
    int gapX,gapY;

    public RectangularGraphLayout(int gapX, int gapY) {
        super();
        this.gapX = gapX;
        this.gapY = gapY;
    }

    @Override
    protected void performGraphLayout(UniversalGraph<N, E> graph) {
        Collection<N> allNodes = graph.getNodes();
        
        ObjectScene scene = graph.getScene ();
        int maxWidth = 0, maxHeight = 0;
        
        for (N node : allNodes) {
            Widget nodeWidget = scene.findWidget(node);
            Rectangle bounds = nodeWidget.getBounds(); 
            if(maxWidth < bounds.width){
                maxWidth = bounds.width;
            }
            if(maxHeight < bounds.height){
                maxHeight = bounds.height;
            }
        }
        
        int numberOfNodesHorizontal;
        
        numberOfNodesHorizontal = (SCENE_WIDTH - gapX) / (maxWidth + gapX);
        
        int centerDistanceHorizontal = gapX; 
        int centerDistanceVertical = gapY; 
        int horizontalCounter = 0;
        int verticalCounter = 0;
        
        for (N node : allNodes) {
            Widget nodeWidget = scene.findWidget(node);
            Rectangle bounds = nodeWidget.getBounds(); 
            
            if (bounds == null){
                continue;
            }
            Point newPosition = new Point(centerDistanceHorizontal, centerDistanceVertical);
            nodeWidget.setPreferredLocation (newPosition);
            
            setResolvedNodeLocation(graph, node, newPosition);
            
            horizontalCounter++;
            if(horizontalCounter >= numberOfNodesHorizontal){
                horizontalCounter = 0;
                verticalCounter++;
            }
            
            centerDistanceHorizontal = gapX + horizontalCounter * (maxWidth + gapX);
            centerDistanceVertical = gapY + verticalCounter * (maxHeight + gapY);
        }
    }

    @Override
    protected void performNodesLayout(UniversalGraph<N, E> graph, Collection<N> nodes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
