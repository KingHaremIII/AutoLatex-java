package com.takamagahara.converter.envNodes.document.body;

import org.dom4j.Element;

/**
 * Created with IntelliJ IDEA.
 * Description: Strategy selector for non-section element in \<body>\<sections>\</sections>\</body>
 * User: kamisama
 * Date: 2020-03-07
 * Time: 下午4:56
 */
public class Strategier {
    private Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public String Process(Element element) {
        return strategy.Process(element);
    }
}
