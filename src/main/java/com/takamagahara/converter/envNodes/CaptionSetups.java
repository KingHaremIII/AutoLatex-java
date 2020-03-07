package com.takamagahara.converter.envNodes;

import com.takamagahara.converter.envNodesUtils.Convertible;
import org.dom4j.Element;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-07
 * Time: 上午8:38
 */
public class CaptionSetups extends EnvNode implements Convertible {
    private List<Element> commands;
    private final String SinglePrefix = "\\captionsetups";
    private final String SinglePostfix = "\n";

    public CaptionSetups(Element newCommmandNode) {
        List<Element> packages = newCommmandNode.elements("captionsetup");
        if (packages.size() < newCommmandNode.elements().size()) {
            System.out.println("Warning: <captionsetups></captionsetups> label has illegal labels. ");
        }
        commands = packages;
    }

    @Override
    public String getPrefix() {
        return "\n%!captionsetups\n";
    }

    @Override
    public String getText() {
        String captionString = "";
        for (Element e : commands) {
            captionString += SinglePrefix+"["+e.attributeValue("type")+"]"+"{"+
                    "name="+e.attributeValue("name")+", "+
                    "labelsep="+e.attributeValue("labelsep")+"}"+SinglePostfix;
        }

        return captionString;
    }

    @Override
    public String getPostfix() {
        return "\n";
    }
}
