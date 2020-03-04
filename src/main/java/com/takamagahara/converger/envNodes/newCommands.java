package com.takamagahara.converger.envNodes;

import com.takamagahara.converger.envNodesUtils.Convertible;
import org.dom4j.Element;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-04
 * Time: 下午10:30
 */
public class newCommands implements Convertible {
    private List<Element> commands;
    private String command = "\\newcommand{";
    private String postfix = "}\n";

    public newCommands(Element newCommmandNode) {
        List<org.dom4j.Element> packages = newCommmandNode.elements("newcommand");
        if (packages.size() < newCommmandNode.elements().size()) {
            System.out.println("Warning: <newcommands></newcommands> label has illegal labels. ");
        }
        commands = packages;
    }

    @Override
    public String getText() {
        // TODO complete getText
        String newCommandString = "";
        for (Element e : commands) {
            newCommandString += command+e.attributeValue("name");
        }

        return newCommandString;
    }

    @Override
    public String toString() {
        return getText();
    }
}
