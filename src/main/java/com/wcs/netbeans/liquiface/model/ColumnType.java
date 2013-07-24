/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wcs.netbeans.liquiface.model;

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

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
 *
 * @author tveki
 */
public class ColumnType {
    
    public static final String VARCHAR_SIZE = "varchar.size";
    public static final String VARCHAR_UNIT = "varchar.unit";
    
    public static final String NUMERIC_PRECISION = "numeric.precision";
    public static final String NUMERIC_SCALE = "numeric.scale";
    
    private static Map<SQLType, String> typeMap;
    
    /*
     * ez majd vmi kulso config fajlbol is johet
     */
    private static void init(){
        typeMap = new EnumMap<SQLType, String>(SQLType.class);
        typeMap.put(SQLType.CHAR, "CHAR (" + propertyExpression(VARCHAR_SIZE) + ")");
        typeMap.put(SQLType.VARCHAR, "VARCHAR (" + propertyExpression(VARCHAR_SIZE) + ")");
        typeMap.put(SQLType.INTEGER, "INT");
        typeMap.put(SQLType.NUMERIC, "NUMBER (" + propertyExpression(NUMERIC_PRECISION) + "," + propertyExpression(NUMERIC_SCALE) + ")");
        typeMap.put(SQLType.DATE, "DATE");
        typeMap.put(SQLType.CLOB, "CLOB");
        typeMap.put(SQLType.BLOB, "BLOB");
    }
    
    public static String getTypeTemplate(SQLType sqlType){
        if (typeMap == null){
            init();
        }
        return typeMap.get(sqlType);
    }
    
    private SQLType sqlType;
    private Map<String, String> properties;
    
    //ide egy az egyben letesszuk a Change-bol jovo type-ot
    private String type;
    
    private ColumnType(){
        this.properties = new HashMap<String, String>();
        initProperties();
    }
    
    public ColumnType(String type){        
        this();
        this.type = type;        
    }
    
    public ColumnType(SQLType sqlType){
        this();
        this.sqlType = sqlType;
    }
    
    private void initProperties(){
        if (sqlType == null) {
            return;
        }
        switch (sqlType){
            case CHAR:
            case VARCHAR :                
                addProperty(VARCHAR_SIZE, "20");
                addProperty(VARCHAR_UNIT, "");
                break;
            case NUMERIC:                
                addProperty(NUMERIC_PRECISION, "16");
                addProperty(NUMERIC_SCALE, "0");
                break;
        }
    }
    
    public void addProperty(String key, String value){
        properties.put(key, value);
    }
    
    public String getProperty(String key){
        return properties.get(key);
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public SQLType getSqlType() {
        return sqlType;
    }   
    
    private String generateType(){
        String template = getTypeTemplate(sqlType);
        
        if (template == null){
            return null;
        }
        
        String type = template;
        
        String varcharUnit = getProperty(VARCHAR_UNIT);
        String numericScale = getProperty(NUMERIC_SCALE);
        
        type = replaceVariable(type, VARCHAR_SIZE, getProperty(VARCHAR_SIZE));
        type = replaceVariable(type, NUMERIC_PRECISION, getProperty(NUMERIC_PRECISION));
        
        if (varcharUnit == null){
            type = replaceVariable(type, VARCHAR_UNIT, "");
        }
        else{
            type = replaceVariable(type, VARCHAR_UNIT, varcharUnit);
        }
        
        if (numericScale == null){
            type = replaceVariable(type, NUMERIC_SCALE, "0");
        }
        else{
            type = replaceVariable(type, NUMERIC_SCALE, numericScale);
        }
        
        return type;
    }    
    
    public String getType(){
        if (type == null){
            return generateType();
        }
        else{
            return type;
        }
    }
    
    private String replaceVariable(String original, String variableName, String variableValue){
        if (original == null || variableName == null || variableValue == null){
            return original;
        }
        return original.replaceAll(Matcher.quoteReplacement(propertyExpression(variableName)),
                                   Matcher.quoteReplacement(variableValue));              
    }
    
    private static String propertyExpression(String propertyName){
        return "%" + propertyName + "%";
    }
    
    
}
