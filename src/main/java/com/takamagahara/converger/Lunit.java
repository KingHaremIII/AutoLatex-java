package com.takamagahara.converger;

import org.dom4j.DocumentException;

import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-16
 * Time: 上午9:48
 */
public class Lunit {
    public static void main(String[] args) throws DocumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String projectPath = args[0];
        Converger converger = new Converger(projectPath);
        converger.ConvergeLunit();
    }
}
