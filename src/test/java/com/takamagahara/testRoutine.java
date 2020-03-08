package com.takamagahara;

import com.takamagahara.converter.envNodes.EnvNode;
import com.takamagahara.converter.envNodes.Packages;
import com.takamagahara.xmler.OperatorStore;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-01
 * Time: 下午10:51
 */
public class testRoutine {
    @Test
    public void Test() {
        String projectPath = "/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest";
        List<String> paths = new ArrayList<>();
        String[] ignoreList = OperatorStore.getInstance().readToString(projectPath+"/.reflectignore").split("\n");
        for (String s : ignoreList) {
            paths.add(projectPath + "/Documents/"+s);
        }
        for (String s : paths) {
            System.out.println(s);
        }
    }

    @Test
    public void Test1() {
        String projectPath = "/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest";
        List<String> paths = new ArrayList<>();
        String ignoreList = OperatorStore.getInstance().readToString(projectPath+"/.reflectignore");

    }
}