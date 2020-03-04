package com.takamagahara.converger.envNodes;

import com.takamagahara.converger.envNodesUtils.Convertible;
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
public class Packages implements Convertible {
    private List<String> names;
    private String command = "\\usepackage{";
    private String postfix = "}\n";

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
    public String getText() {
        String packageString = "";
        for (String s : names) {
            packageString += command+s+postfix;
        }
        return packageString;
    }

    @Override
    public String toString() {
        return getText();
    }
}
