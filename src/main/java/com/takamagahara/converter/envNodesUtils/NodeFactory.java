package com.takamagahara.converter.envNodesUtils;

import com.takamagahara.converter.envNodes.EnvNode;
import org.dom4j.Element;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * Description: Factory to build the label nodes in \<documents></documents> root label node.
 * User: kamisama
 * Date: 2020-03-04
 * Time: 下午10:33
 */
public class NodeFactory {
    public static EnvNode build(String className, Element element) {
        EnvNode en = null;
        try {
            Class<?> cls = Class.forName(className);
            if (EnvNode.class.isAssignableFrom(cls)) {
                try {
                    Constructor constructor;
                    constructor = cls.getConstructor(Element.class);
                    en = (EnvNode) constructor.newInstance(element);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    System.out.println("NoSuchMethod: "+cls.getName());
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                throw new IncorrectClassForFactory("incorrect class for factory: "+className);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return en;
    }

    public static EnvNode build(String className, Element element, String pathProject) {
        EnvNode en = null;
        try {
            Class<?> cls = Class.forName(className);
            if (EnvNode.class.isAssignableFrom(cls)) {
                try {
                    Constructor constructor;
                    constructor = cls.getConstructor(Element.class, String.class);
                    en = (EnvNode) constructor.newInstance(element, pathProject);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    System.out.println("NoSuchMethod: "+cls.getName());
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                throw new IncorrectClassForFactory("incorrect class for factory: "+className);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return en;
    }
}
