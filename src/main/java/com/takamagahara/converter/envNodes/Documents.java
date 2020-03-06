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
    private String options;
    private String documentClass;

    public Documents(Element documentsNode) {
        options = documentsNode.attributeValue("options");
        documentClass = documentsNode.attributeValue("documentcalss");
    }

    @Override
    public String getPrefix() {
        String returnString;
        if (options == null) {
            returnString = "\\documentclass{"+documentClass+"}\n";
        } else {
            returnString = "\\documentclass["+options+"]{"+documentClass+"}\n";
        }
        return returnString+"% Created from AutoLatex by Ziyao, Kou (email: Kouzuyao@outlook.com)\n";
    }

    @Override
    public String getText() {
        return "";
    }

    @Override
    public String getPostfix() {
        return "\n";
    }
}
