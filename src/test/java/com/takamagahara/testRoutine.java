package com.takamagahara;

import org.junit.Test;

import java.io.File;
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
        String a = "/home/kamisama/文档";
        System.out.println(Paths.get(a).getFileName().toString());
    }

    @Test
    public void ArgTest() {
        argTest(0);
    }

    public void argTest(int i, String... strings) {
        System.out.println("start"+i);
    }

    @Test
    public void fileTest() {
        String a = "/home/kamisama/tmp";
        File file = new File(a);
        if (file.exists()) {
            file.renameTo(new File("/home/kamisama/fuck"));
            System.out.println(file.getName());
        } else {
            System.out.println("not");
        }
    }
}
