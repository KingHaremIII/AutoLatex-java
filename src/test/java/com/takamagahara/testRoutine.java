package com.takamagahara;

import com.takamagahara.converter.envNodes.EnvNode;
import com.takamagahara.converter.envNodes.Packages;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
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

    @Test
    public void argsTest() {
        String[] a = {"a", "b"};
        System.out.println();
    }

    @Test
    public void renameTest() {
        File file = new File("/home/kamisama/tmp");
        file.renameTo(new File("/home/kamisama/fuck"));
    }

    @Test
    public void InterfaceTest() {
        InterfaceTest_use(new A());
    }

    public void InterfaceTest_use(BaseTest bt) {
        InterfaceTest it = (InterfaceTest) bt;
        System.out.println(it.show());
        bt.display();
    }

    @Test
    public void StringTest() {
        String a = "a";
        a = addB(a);
        System.out.println(a);
    }

    public String addB(String a) {
        return a+"b";
    }

    @Test
    public void FileReadTest() {
        String s = readToString("/home/kamisama/Paper.tex");
        System.out.println(s);
    }

    private String readToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long fileLength = file.length();
        FileInputStream in;
        byte[] fileContent = new byte[fileLength.intValue()];
        try {
            in = new FileInputStream(file);
            in.read(fileContent);
            in.close();
            return new String(fileContent, encoding);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void isClassTest() {
        if (Packages.class.isAssignableFrom(EnvNode.class)) {
            System.out.println("P is E");
        }
        if (EnvNode.class.isAssignableFrom(Packages.class)) {
            System.out.println("E is P");
        }
    }
}
