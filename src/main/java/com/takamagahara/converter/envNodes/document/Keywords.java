package com.takamagahara.converter.envNodes.document;

import com.takamagahara.converter.envNodes.EnvNode;
import com.takamagahara.converter.envNodesUtils.Convertible;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-05
 * Time: 上午11:25
 */
public class Keywords extends EnvNode implements Convertible {
    private String keywords;
    private String name;

    public Keywords(Element keywords) {
        name = keywords.attributeValue("name");
        this.keywords = keywords.getText();
    }

    @Override
    public String getPrefix() {
        return "\\begin{"+name+"}\n";
    }

    @Override
    public String getText() {
        return keywords+"\n";
    }

    @Override
    public String getPostfix() {
        return "\\end{"+name+"}\n";
    }
}
