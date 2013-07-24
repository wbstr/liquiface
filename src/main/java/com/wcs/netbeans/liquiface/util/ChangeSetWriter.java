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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import liquibase.changelog.ChangeSet;
import liquibase.serializer.core.xml.XMLChangeLogSerializer;
import org.openide.util.Exceptions;

/**
 *
 * @author tveki
 */
public class ChangeSetWriter {
    
    public static void writeToStream(List<ChangeSet> changeSets, OutputStream out) {
        XMLChangeLogSerializer serializer = new XMLChangeLogSerializer();
        try {
            serializer.write(changeSets, out);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public static void writeToFile(List<ChangeSet> changeSets, File changelogFile) {
        try {
            writeToStream(changeSets, new FileOutputStream(changelogFile));
        } catch (FileNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public static String writeToString(List<ChangeSet> changeSets) {
        OutputStream out = new ByteArrayOutputStream();
        writeToStream(changeSets, out);
        return out.toString();
    }    
    
}
