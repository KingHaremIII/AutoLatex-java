package com.takamagahara.converter.envNodes.document;

import com.takamagahara.converter.envNodes.SimpleNodePrototype;
import org.dom4j.Element;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-07
 * Time: 上午8:59
 */
public class IEEEpeerreviewmaketitle extends SimpleNodePrototype {

    public IEEEpeerreviewmaketitle(Element element) {
        super(element);
        prefix = "%simpleNode\n\\IEEEpeerreviewmaketitle\n";
    }
}
