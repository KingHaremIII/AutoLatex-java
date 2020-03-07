package com.takamagahara.converter.envNodes;

import com.takamagahara.converter.envNodesUtils.Convertible;
import org.dom4j.Element;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-07
 * Time: 上午8:26
 */
public class RenewCommands extends EnvNode implements Convertible {
    private List<Element> commands;
    private final String SinglePrefix = "\\renewcommand{";
    private final String SinglePostfix = "\n";

    public RenewCommands(Element newCommmandNode) {
        List<Element> packages = newCommmandNode.elements("renewcommand");
        if (packages.size() < newCommmandNode.elements().size()) {
            System.out.println("Warning: <renewcommands></renewcommands> label has illegal labels. ");
        }
        commands = packages;
    }

    @Override
    public String getPrefix() {
        return "\n%!renewcommands\n";
    }

    @Override
    public String getText() {
        String renewCommandString = "";
        for (Element e : commands) {
            renewCommandString += SinglePrefix+e.attributeValue("name")+"}"+e.getText()+SinglePostfix;
        }

        return renewCommandString;
    }

    @Override
    public String getPostfix() {
        return "\n";
    }
}
