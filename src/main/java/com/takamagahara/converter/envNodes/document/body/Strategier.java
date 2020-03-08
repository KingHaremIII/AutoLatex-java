package com.takamagahara.converter.envNodes.document.body;

import org.dom4j.Element;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: Strategy selector for non-section element in \<body>\<sections>\</sections>\</body>
 * User: kamisama
 * Date: 2020-03-07
 * Time: 下午4:56
 */
public class Strategier {
    private Strategy strategy;
    private String pathProject;

    public void setStrategy(String nameStrategy, String pathProject) {
        this.pathProject = pathProject;
        try {
            Class<?> cls = Class.forName(nameStrategy);
            strategy = (Strategy) cls.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public String Process(Element element) {
        return strategy.Process(element, pathProject);
    }
}
