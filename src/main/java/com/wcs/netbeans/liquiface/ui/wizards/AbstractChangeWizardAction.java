/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.ui.wizards;

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

import com.wcs.netbeans.liquiface.eventbus.LiquifaceEventBus;
import com.wcs.netbeans.liquiface.eventbus.event.LiquibaseChangeEvent;
import com.wcs.netbeans.liquiface.eventbus.event.RedrawSceneEvent;
import liquibase.change.Change;
import org.openide.WizardDescriptor;

/**
 *
 * @author tveki
 */
public abstract class AbstractChangeWizardAction extends AbstractWizardAction{

    protected abstract Change createChange(WizardDescriptor wiz);
    protected abstract RedrawSceneEvent getRedrawSceneEvent();

    @Override
    protected void onFinishWizard(WizardDescriptor wiz) {
        Change change = createChange(wiz);
        LiquifaceEventBus.getInstance().post(new LiquibaseChangeEvent(change));
        LiquifaceEventBus.getInstance().post(getRedrawSceneEvent());
    }



}
