package com.takamagahara.converter.envNodes.document;

import com.takamagahara.converter.envNodes.SimpleNodePrototype;
import org.dom4j.Element;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-05
 * Time: 上午11:22
 */
public class Abstract extends SimpleNodePrototype {
    public Abstract(Element element) {
        super(element);
        prefix = "\\begin{abstract}\n";
        postfix = "\n\\end{abstract}\n";
    }
}
