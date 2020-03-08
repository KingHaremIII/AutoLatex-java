package com.takamagahara.converter.envNodes.document;

import com.takamagahara.converter.envNodes.EnvNode;
import com.takamagahara.converter.envNodesUtils.Containable;
import com.takamagahara.converter.envNodesUtils.Convertible;
import org.dom4j.Element;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-05
 * Time: 上午10:20
 */
public class Document extends EnvNode implements Convertible, Containable {
    public Document(Element documentElement) {
        return;
    }

    @Override
    public String getPrefix() {
        return "%!Text\n\\begin{document}\n";
    }

    @Override
    public String getText() {
        return "";
    }

    @Override
    public String getPostfix() {
        return "\\end{document}\n";
    }
}
