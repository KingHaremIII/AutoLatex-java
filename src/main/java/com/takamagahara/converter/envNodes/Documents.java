package com.takamagahara.converter.envNodes;

import com.takamagahara.converter.envNodesUtils.Containable;
import com.takamagahara.converter.envNodesUtils.Convertible;
import org.dom4j.Attribute;
import org.dom4j.Element;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-05
 * Time: 上午9:59
 */
public class Documents extends EnvNode implements Convertible, Containable {
    private List<Element> commands;
    private String options;
    private String documentClass;

    public Documents(Element documentsNode) {
        commands = documentsNode.elements();
        options = documentsNode.attributeValue("options");
        documentClass = documentsNode.attributeValue("documentcalss");
    }

    @Override
    public String getPrefix() {
        if (options == null) {
            return "\\documentclass{"+documentClass+"}\n";
        } else {
            return "\\documentclass["+options+"]{"+documentClass+"}\n";
        }
    }

    @Override
    public String getText() {
        return "% Created from AutoLatex by Ziyao, Kou (email: Kouzuyao@outlook.com)";
    }

    @Override
    public String getPostfix() {
        return "\n";
    }

    @Override
    public List<Element> getChilds() {
        return commands;
    }
}
