package com.takamagahara.converter.envNodes;

import com.takamagahara.converter.envNodesUtils.Convertible;
import org.dom4j.Element;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-04
 * Time: 下午10:30
 */
public class NewCommands implements Convertible {
    private List<Element> commands;
    private final String SinglePrefix = "\\newcommand{";
    private final String SinglePostfix = "\n";

    public NewCommands(Element newCommmandNode) {
        List<Element> packages = newCommmandNode.elements("newcommand");
        if (packages.size() < newCommmandNode.elements().size()) {
            System.out.println("Warning: <newcommands></newcommands> label has illegal labels. ");
        }
        commands = packages;
    }

    @Override
    public String getPrefix() {
        return "\n";
    }

    @Override
    public String getText() {
        String newCommandString = "";
        for (Element e : commands) {
            newCommandString += SinglePrefix+e.attributeValue("name")+e.getText()+SinglePostfix;
        }

        return newCommandString;
    }

    @Override
    public String getPostfix() {
        return "\n";
    }
}
