package com.takamagahara.converger;

import org.dom4j.DocumentException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-01
 * Time: 下午11:01
 */
public class ConvergerEntry {
    public static void main(String[] args) throws DocumentException {
        String projectPath = args[0];
        Converger converger = new Converger(projectPath);
        converger.Converge();
    }
}
