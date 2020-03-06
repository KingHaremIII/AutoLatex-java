package com.takamagahara.converter.envNodesUtils;

import com.takamagahara.converter.envNodes.EnvNode;

/**
 * Created with IntelliJ IDEA.
 * Description: Factory to build the label nodes in \<documents></documents> root label node.
 * User: kamisama
 * Date: 2020-03-04
 * Time: 下午10:33
 */
public class NodeFactory {
    public EnvNode build(String className) throws ClassNotFoundException {
        EnvNode en = null;
        Class<?> cls = Class.forName(className);
        if (EnvNode.class.isAssignableFrom(cls)) {
            try {
                en = (EnvNode) cls.newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        } else {
            throw new IncorrectClassForFactory("incorrect class for factory: "+className);
        }
        return en;
    }
}
