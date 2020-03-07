package com.takamagahara.converter.envNodes.document.body;

import org.dom4j.Element;

/**
 * Created with IntelliJ IDEA.
 * Description: Interface for non-section processing
 * User: kamisama
 * Date: 2020-03-07
 * Time: 下午4:57
 */
public interface Strategy {
    String Process(Element element);
}
