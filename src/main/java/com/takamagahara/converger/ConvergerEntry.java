package com.takamagahara.converger;

import org.dom4j.DocumentException;

import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-01
 * Time: 下午11:01
 */
public class ConvergerEntry {
    public static void main(String[] args) throws DocumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (args.length == 1) {
            // full convergence
            String projectPath = args[0];
            Converger converger = new Converger(projectPath);
            converger.Converge();
        } else {
            // unit convergence
            if (args.length == 2) {
                String projectPath = args[0];
                String unitPath = args[1];
                Converger converger = new Converger(projectPath);
                converger.Converge(unitPath);
            } else {
                // error parameters
                String list = "";
                for (String s : args) {
                    list = list + " " + s;
                }
                System.out.println("Illegal parameter number: "+list);
            }
        }
    }
}
