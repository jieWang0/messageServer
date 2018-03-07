package io.transwarp.tdc.gn.common.utils;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.Set;

/**
 * 18-2-24 created by zado
 */
public class ClazzScanner {

    /**
     * find all classes in package
     */
    public static Set<Class<?>> findClasses(String packageName) {
        return findClasses(packageName, Object.class);
    }

    /**
     * find classes in package
     * only return the class which extends superClass
     */
    public static <T> Set<Class<? extends T>> findClasses(String packageName, Class<T> superClass) {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                                          .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                                          .setUrls(ClasspathHelper.forPackage(packageName))
                                          .filterInputsBy(new FilterBuilder().includePackage(packageName)));

        return reflections.getSubTypesOf(superClass);
    }
}
