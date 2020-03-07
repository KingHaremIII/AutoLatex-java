package com.takamagahara.converter.envNodes;

import com.takamagahara.converter.envNodesUtils.Convertible;
import org.dom4j.Element;


/**
 * Created with IntelliJ IDEA.
 * Description: provides the prototype of simple node, whose tex text is just in a simple model: prefix+text+postfix
 *     without any elements in this kind of label.
 * User: kamisama
 * Date: 2020-03-05
 * Time: 上午11:25
 */
public class SimpleNodePrototype extends EnvNode implements Convertible {
    protected String prefix = "\n";
    protected String text;
    protected String postfix = "\n";

    /**
     * classes extends this class should define their own constructor and rewrite prefix and postfix.
     * @param element
     */
    public SimpleNodePrototype(Element element) {
        text = element.getText();
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getPostfix() {
        return postfix;
    }
}
