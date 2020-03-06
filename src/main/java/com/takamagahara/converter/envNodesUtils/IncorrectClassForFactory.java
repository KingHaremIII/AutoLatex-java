package com.takamagahara.converter.envNodesUtils;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kamisama
 * Date: 2020-03-05
 * Time: 上午8:18
 */
public class IncorrectClassForFactory extends RuntimeException {
    public IncorrectClassForFactory(String msg) {
        super(msg);
    }
}
