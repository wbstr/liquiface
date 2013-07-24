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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.stream.XMLStreamException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tveki
 */
public class XmlMergerTest {
    
    private static final int MAX_SIZE_DIFF = 10;
    
    public XmlMergerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of merge method, of class XmlMerger.
     */
    @Test
    public void testMergeOneChangelog() throws Exception {        
        testMerge("changelog1.xml");
    }
    
    @Test
    public void testMergeTwoChangelogs() throws Exception {        
        testMerge("changelog1.xml", "changelog2.xml");
    }
    
    @Test
    public void testMergeTwoChangelogsReversed() throws Exception {        
        testMerge("changelog2.xml", "changelog1.xml");
    }
    
    @Test
    public void testMergeThreeChangelogs() throws Exception {        
        testMerge("changelog1.xml", "changelog2.xml", "changelog3.xml");
    }    
    
    @Test(expected = XMLStreamException.class)
    public void testMergeInvalidChangelogXml() throws Exception {        
        testMerge("invalid_changelog.xml", "changelog1.xml");
    }    
    
    private void testMerge(String ... changelogs) throws Exception {          
        printChangelogsToMerge(changelogs);
        XmlMerger merger = new XmlMerger();
        
        File merged = createTempFile();        
        OutputStream out = new FileOutputStream(merged);
        InputStream[] inputs = getResources(changelogs);         
        merger.merge(out, inputs);
        out.close();
        
        InputStream mergedChangelog = new FileInputStream(merged);        
        inputs = getResources(changelogs);     
        
        int size = changelogs.length;
        
        for (InputStream input : inputs){
            if (size > 1){
                assertMergedHasMoreBytes(mergedChangelog, input);
            }
            else {
                assertMergedHasApproximatelySameSize(mergedChangelog, input);
            }
        }
        
        merger.merge(System.out, getResources(changelogs));
        
        mergedChangelog.close();
        close(inputs);
        merged.delete();
        System.out.println();
    }    
    
    private void printChangelogsToMerge(String ... changelogs){
        StringBuilder sb = new StringBuilder("\nMerging ");
        for (int i=0; i<changelogs.length; i++){
            if (i > 0){
                sb.append(", ");
            }
            sb.append(changelogs[i]);            
        }
        System.out.println(sb.toString());
    }
    
    private InputStream[] getResources(String ... res){
        InputStream[] resources = new InputStream[res.length];
        for (int i=0; i<res.length; i++){
            resources[i] = getResource(res[i]);
        }
        return resources;
    }
    
    private InputStream getResource(String resource){
        return getClass().getClassLoader().getResourceAsStream(resource);
    }
    
    private void assertMergedHasMoreBytes(InputStream merged, InputStream source) throws Exception{
        assertTrue(merged.available() > source.available());
    }
    
    private void assertMergedHasApproximatelySameSize(InputStream merged, InputStream source) throws Exception{        
        assertTrue(Math.abs(merged.available() - source.available()) < MAX_SIZE_DIFF);
    }    
    
    private void close(InputStream ... resources) throws IOException{
        for (InputStream res : resources){
            res.close();
        }
    }
    
    private File createTempFile() throws IOException{
        File file = File.createTempFile("temp", "changelog");
        file.deleteOnExit();
        return file;
    }
}
