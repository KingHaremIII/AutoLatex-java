package com.takamagahara.converter.envNodes;

import com.takamagahara.converter.envNodesUtils.Containable;
import com.takamagahara.converter.envNodesUtils.Convertible;
import org.dom4j.Element;

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
    private String contentForFast;

    public Documents(Element documentsNode) {
        options = documentsNode.attributeValue("options");
        documentClass = documentsNode.attributeValue("documentcalss");
        if (documentsNode.attributeValue("fast") == null) {
            contentForFast = "";
        } else {
            contentForFast = documentsNode.getText();
        }
    }

    @Override
    public String getPrefix() {
        String returnString = "% Created from AutoLatex by Ziyao, Kou (email: Kouzuyao@outlook.com)\n";
        if (options == null) {
            returnString += "\\documentclass{"+documentClass+"}\n";
        } else {
            returnString += "\\documentclass["+options+"]{"+documentClass+"}\n";
        }
        return returnString+contentForFast;
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
