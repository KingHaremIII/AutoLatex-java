package com.takamagahara.converter.envNodes.document;

import com.takamagahara.converter.envNodes.EnvNode;
import com.takamagahara.converter.envNodes.SimpleNodePrototype;
import com.takamagahara.xmler.OperatorStore;
import org.dom4j.Element;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-05
 * Time: 上午11:22
 */
public class Abstract extends EnvNode {
    private String pathProject;

    public Abstract(Element element, String pathProject) {
        this.pathProject = pathProject;
    }

    @Override
    public String getPrefix() {
        return "\\begin{abstract}\n";
    }

    @Override
    public String getText() {
        return OperatorStore.getInstance().readToString(pathProject+"/Documents/abstract.tex");
    }

    @Override
    public String getPostfix() {
        return "\n\\end{abstract}\n";
    }
}
