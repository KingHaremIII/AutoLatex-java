package com.takamagahara;

import com.takamagahara.converter.envNodes.EnvNode;
import com.takamagahara.converter.envNodes.Packages;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;

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
        switch ("ab".substring(0, 1)) {
            case "a":
                System.out.println("aa");
                break;
            default:

        }
    }
}