/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.util;

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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author tveki
 */
public class XmlMerger {

    String encoding;
    XMLInputFactory inputFactory;
    XMLOutputFactory outputFactory;
    int index;
    int numberOfSources;
    int elementLevel;

    public XmlMerger(String encoding) {
        this.encoding = encoding;
        inputFactory = XMLInputFactory.newInstance();
        outputFactory = XMLOutputFactory.newInstance();
    }

    public XmlMerger() {
        this("UTF-8");
    }

    public void merge(File target, File... sources) throws Exception {
        OutputStream targetStream = new FileOutputStream(target);
        InputStream[] sourceStreams = new InputStream[sources.length];
        for (int i = 0; i < sources.length; i++) {
            sourceStreams[i] = new FileInputStream(sources[i]);
        }
        merge(targetStream, sourceStreams);
    }

    public void merge(OutputStream target, InputStream... sources) throws Exception {
        index = 0;
        numberOfSources = sources.length;

        XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(target, encoding);


        for (InputStream source : sources) {
            mergeToTarget(eventWriter, source);
            index++;
        }

        eventWriter.close();
    }

    private void mergeToTarget(XMLEventWriter eventWriter, InputStream source) throws Exception {
        elementLevel = 0;
        XMLEventReader reader = inputFactory.createXMLEventReader(source, encoding);
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();

            boolean copy = true;

            if (event.isStartDocument()) {
                copy = isFirst();
            } else if (event.isEndDocument()) {
                copy = isLast();
            } else if (event.isStartElement()) {
                copy = !isRootLevel() || isFirst();
                elementLevel++;
            } else if (event.isEndElement()) {
                elementLevel--;
                copy = !isRootLevel() || isLast();
            }

            if (copy) {
                eventWriter.add(event);
            }
        }

        reader.close();
    }

    private boolean isRootLevel() {
        return elementLevel == 0;
    }

    private boolean isFirst() {
        return index == 0;
    }

    private boolean isLast() {
        return index == numberOfSources - 1;
    }
}
