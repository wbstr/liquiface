/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.eventbus;

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
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.openide.util.Exceptions;

/**
 *
 * @author botond
 */
public class LiquifaceEventBusTest {

    private List<String> eventList = new ArrayList<String>();

    @Test
    public void testThreadSafe() throws InterruptedException {
        LiquifaceEventBus.getInstance().register(new Listener("listener1"));
        LiquifaceEventBus.getInstance().register(new Listener("listener2"));
        new Thread(new Thread1()).run();
        new Thread(new Thread2()).run();
        Thread.sleep(5000);
        Assert.assertEquals("thread1 listener1 start", eventList.get(0));
        Assert.assertEquals("thread1 listener1 stop", eventList.get(1));
        Assert.assertEquals("thread1 listener2 start", eventList.get(2));
        Assert.assertEquals("thread1 listener2 stop", eventList.get(3));
        Assert.assertEquals("thread2 listener1 start", eventList.get(4));
        Assert.assertEquals("thread2 listener1 stop", eventList.get(5));
        Assert.assertEquals("thread2 listener2 start", eventList.get(6));
        Assert.assertEquals("thread2 listener2 stop", eventList.get(7));
    }

    class Thread1 implements Runnable {

        @Override
        public void run() {
            LiquifaceEventBus.getInstance().post("thread1");
        }

    }

    class Thread2 implements Runnable {

        @Override
        public void run() {
            LiquifaceEventBus.getInstance().post("thread2");
        }

    }

    class Listener {

        private String listenerName;

        public Listener(String listenerName) {
            this.listenerName = listenerName;
        }

        @Subscribe
        public void listener(String string) {
            try {
                eventList.add(string + " " + listenerName + " start");
                Thread.sleep(1000);
                eventList.add(string + " " + listenerName +  " stop");
            } catch (InterruptedException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

}
