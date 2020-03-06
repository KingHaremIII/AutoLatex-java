package com.takamagahara.converter.envNodes;

import com.takamagahara.converter.envNodesUtils.Convertible;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-04
 * Time: 下午10:19
 */
public class Packages extends EnvNode implements Convertible {
    private List<String> names;
    private final String SinglePrefix = "\\usepackage{";
    private final String SinglePostfix = "}\n";

    public Packages(Element usePackages) {
        List<Element> packages = usePackages.elements("usepackage");
        if (packages.size() < usePackages.elements().size()) {
            System.out.println("Warning: <usepackages></usepackages> label has illegal labels. ");
        }
        names = new ArrayList<>();
        for (Element e : packages) {
            names.add(e.attributeValue("name"));
        }
    }

    @Override
    public String getPrefix() {
        return "\n";
    }

    @Override
    public String getText() {
        String packageString = "";
        for (String s : names) {
            packageString += SinglePrefix+s+SinglePostfix;
        }
        return packageString;
    }

    @Override
    public String getPostfix() {
        return "\n";
    }
}
