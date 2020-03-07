package com.takamagahara.converter.envNodes.document;

import com.takamagahara.converter.envNodes.SimpleNodePrototype;
import org.dom4j.Element;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-07
 * Time: 上午9:01
 */
public class IfCLASSOPTIONcaptionsoff extends SimpleNodePrototype {
    public IfCLASSOPTIONcaptionsoff(Element element) {
        super(element);
        prefix = "\n%!simpleNode\n\\ifCLASSOPTIONcaptionsoff\n";
        postfix = "\n\\fi\n";
    }
}
