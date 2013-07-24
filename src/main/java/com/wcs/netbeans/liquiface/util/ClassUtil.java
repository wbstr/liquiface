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
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tveki
 */
public class ClassUtil {

    public static final String CLASS_EXTENSION = ".class";

    public static Iterable<Class> getAllClassesRecursivelyFromPackageOf(Class clazz) throws Exception {
        List<Class> classes = new ArrayList<Class>();
        String packageName = clazz.getPackage().getName();
        String path = toPath(packageName);
        Enumeration<URL> resources = clazz.getClassLoader().getResources(path);
        URL resource = null;
        while (resources.hasMoreElements()) {
            resource = resources.nextElement();
            classes.addAll(findClasses(resource, packageName));
        }

        if (resource == null) {
            resource = asResource(clazz);
            classes.addAll(findClasses(resource, packageName));
        }

        return classes;
    }

    private static URL asResource(Class clazz) {
        return clazz.getClassLoader().getResource(toPath(clazz));
    }

    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();

        if (!directory.exists()) {
            return classes;
        }

        logger().log(Level.FINE, "Searching for classes in directory {0} of package or subpackages of {1}",
                new Object[]{directory.getAbsolutePath(), packageName});

        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (isClass(file)) {
                classes.add(Class.forName(className(packageName, file)));
            }
        }

        return classes;
    }

    private static List<Class> findClasses(URL resource, String packageName) throws URISyntaxException, IOException, ClassNotFoundException {
        if ("file".equals(resource.getProtocol())) {
            File dir = new File(resource.getFile());
            return findClasses(dir, packageName);
        } else if ("jar".equals(resource.getProtocol())) {
            String jarPath = jarPath(resource);
            JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
            return findClasses(jar, packageName);
        } else {
            logger().log(Level.WARNING, "Unhandled protocol: {0}", resource.getProtocol());
        }
        return new ArrayList<Class>();
    }

    private static List<Class> findClasses(JarFile jar, String packageName) throws URISyntaxException, IOException, ClassNotFoundException {
        logger().log(Level.FINE, "Searching for classes in jar {0} of package or subpackages of {1}",
                new Object[]{jar.getName(), packageName});
        Enumeration<JarEntry> entries = jar.entries();
        List<Class> classes = new ArrayList<Class>();
        String path = toPath(packageName) + "/";
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            if (isClass(jarEntry) && jarEntry.getName().startsWith(path)) {
                classes.add(Class.forName(className(jarEntry)));
            }
        }
        return classes;
    }

    private static boolean isClass(File file) {
        return isClass(file.getName());
    }

    private static boolean isClass(JarEntry entry) {
        return isClass(entry.getName());
    }

    private static boolean isClass(String fileName) {
        return fileName.endsWith(CLASS_EXTENSION);
    }

    private static String className(String packageName, File file) {
        if (!isClass(file)) {
            throw new IllegalArgumentException("It's not a *.class file!");
        }
        int extensionStart = file.getName().length() - CLASS_EXTENSION.length();
        String className = file.getName().substring(0, extensionStart);
        return packageName + '.' + className;
    }

    private static String className(JarEntry entry) {
        if (!isClass(entry)) {
            throw new IllegalArgumentException("It's not a *.class entry!");
        }
        String entryName = entry.getName();
        int classNameStart = entryName.lastIndexOf('/') + 1;
        int extensionStart = entryName.length() - CLASS_EXTENSION.length();
        String packageName = toPackage(entryName.substring(0, classNameStart - 1));
        String className = entryName.substring(classNameStart, extensionStart);
        return packageName + '.' + className;
    }

    private static String jarPath(URL jarResource) {
        if (!"jar".equals(jarResource.getProtocol())) {
            throw new IllegalArgumentException("Not a jar");
        }
        int jarEnd = jarResource.getPath().indexOf("!");
        return jarResource.getPath().substring("file:".length(), jarEnd);
    }

    private static String toPath(String packageName) {
        return packageName.replace('.', '/');
    }

    private static String toPackage(String path) {
        return path.replace('/', '.');
    }

    private static String toPath(Package pack) {
        return toPath(pack.getName());
    }

    private static String toPath(Class klazz) {
        return toPath(klazz.getName()) + CLASS_EXTENSION;
    }

    private static Logger logger() {
        return Logger.getLogger(ClassUtil.class.getName());
    }
}
