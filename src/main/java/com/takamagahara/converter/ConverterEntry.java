package com.takamagahara.converter;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-02
 * Time: 下午6:50
 */
public class ConverterEntry {
    public static void main(String[] args) {
        Converter converter = new Converter(args[0]);
        converter.Convert();
    }
}
