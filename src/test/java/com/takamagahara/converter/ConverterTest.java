package com.takamagahara.converter;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-03
 * Time: 下午4:37
 */
public class ConverterTest {
    private static String pathProject = "/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest";

    @Test
    public void Test() {
        Converter converter = new Converter(
                "/home/kamisama/IdeaProjects/AutoLatex/src/main/resources/IEEEtranTest");
        converter.Convert();
    }
}
