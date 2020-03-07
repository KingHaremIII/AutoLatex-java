package com.takamagahara.converter.envNodes.document;

import com.takamagahara.converter.envNodes.EnvNode;
import org.dom4j.Element;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-07
 * Time: 上午9:21
 */
public class BibConfig extends EnvNode {
    private Element style;

    public BibConfig(Element element) {
        style = element.element("bibliographystyle");
    }

    @Override
    public String getPrefix() {
        return "\n%!bibliConfig\n";
    }

    @Override
    public String getText() {
        String text = "";
        if (style != null) {
            text += "\\bibliographystyle{"+style.attributeValue("name")+"}\n";
        }
        text += "\\bibliography{ref}\n";
        return text;
    }

    @Override
    public String getPostfix() {
        return "\n";
    }
}
